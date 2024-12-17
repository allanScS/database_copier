package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.PatientCaseForwarding;
import br.com.database_copier.entities.PatientCaseForwardingAttendance;
import br.com.database_copier.entities.ProviderPayment;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class PatientCaseForwardingAttendanceJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		final String sourceTable = "patient_case_forwarding_attendance";
		final String targetTable = "patientCaseForwardingAttendance";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "annotation", "effectuated_at", "no_show", "sent_for_payment", "value",
				"patient_case_forwarding_id", "provider_payment_closure_id", "provider_service_model_id",
				"service_model_type", "support_type", "attendance_hours", "attendance_ended_at" };

		Boolean hasMoreElements = Boolean.TRUE;

		int page = 0;

		while (hasMoreElements) {

			System.out.println("BUSCANDO A PAGINA: " + page);

			final String query = GenericUtils.buildSql(fields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage,
					page);

			System.out.println("Iniciando seção com origem");

			Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
			session.beginTransaction();
			System.out.println("Seção iniciada com origem");

			final List<Object[]> list = session.createSQLQuery(query).list();

			if (list.isEmpty()) {
				hasMoreElements = Boolean.FALSE;
				break;
			}

			final AbstractConverter<PatientCaseForwardingAttendance> converter = new AbstractConverter<PatientCaseForwardingAttendance>()
					.convertjsonToEntityList(PatientCaseForwardingAttendance.class,
							GenericUtils.objectsToJson(list, fields));

			final List<PatientCaseForwardingAttendance> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
			System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final PatientCaseForwardingAttendance entity = entityList.get(i);

				if (entity.getPatientCaseForwardingId() != null) {
					entity.setPatientCaseForwarding(new PatientCaseForwarding());
					entity.getPatientCaseForwarding().setId(entity.getPatientCaseForwardingId());
				}

				if (entity.getProviderPaymentClosureId() != null) {
					entity.setProviderPaymentClosure(new ProviderPayment());
					entity.getProviderPaymentClosure().setId(entity.getProviderPaymentClosureId());
				}

				if (i % 3000 == 0) {
					insertSession.getTransaction().commit();
					insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
					insertSession.beginTransaction();
				}

				final String result = (String) insertSession
						.createSQLQuery(
								"SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + targetTable + " WHERE id = :id")
						.setParameter("id", entity.getId()).uniqueResult();

				if (result == null) {
					insertSession.save(entity);
					System.out.println("Inserted MAP: " + entity.getId() + " : " + targetTable);
				}
			}

			if (!insertSession.getTransaction().wasCommitted()) {
				insertSession.getTransaction().commit();
			}

			System.out.println("Transaction commited and Session closed");
			System.out.println(i + " new data inserted");
			page++;
		}
	}
}