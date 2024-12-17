package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.Account;
import br.com.database_copier.entities.Patient;
import br.com.database_copier.entities.PatientCase;
import br.com.database_copier.entities.PatientCompany;
import br.com.database_copier.entities.TroubleArea;
import br.com.database_copier.entities.TroubleSubtype;
import br.com.database_copier.entities.TroubleType;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class PatientCaseJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		final String sourceTable = "patient_case";
		final String targetTable = "patientCase";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "case_number", "concluded", "concluded_at", "concluded_by", "description", "notifier",
				"patient_availability", "priority", "problem_holder", "product", "patient_id",
				"responsible_for_the_case_id", "trouble_area_id", "trouble_subtype_id", "trouble_type_id",
				"opening_date", "patient_company_id", "call_type", "original_case_number", "origin",
				"carelink_sessions", "provider_sessions", "participate_satisfaction_survey", "reason_for_work_leave",
				"work_leave_end_date", "work_leave_start_date" };

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

			final AbstractConverter<PatientCase> converter = new AbstractConverter<PatientCase>()
					.convertjsonToEntityList(PatientCase.class, GenericUtils.objectsToJson(list, fields));

			final List<PatientCase> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
			System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final PatientCase entity = entityList.get(i);

				if (entity.getPatientId() != null) {
					entity.setPatient(new Patient());
					entity.getPatient().setId(entity.getPatientId());
				}

				if (entity.getTroubleAreaId() != null) {
					entity.setTroubleArea(new TroubleArea());
					entity.getTroubleArea().setId(entity.getTroubleAreaId());
				}

				if (entity.getTroubleTypeId() != null) {
					entity.setTroubleType(new TroubleType());
					entity.getTroubleType().setId(entity.getTroubleTypeId());
				}

				if (entity.getTroubleSubtypeId() != null) {
					entity.setTroubleSubtype(new TroubleSubtype());
					entity.getTroubleSubtype().setId(entity.getTroubleSubtypeId());
				}

				if (entity.getResponsibleForTheCaseId() != null) {
					entity.setResponsibleForTheCase(new Account());
					entity.getResponsibleForTheCase().setId(entity.getResponsibleForTheCaseId());
				}

				if (entity.getPatientCompanyId() != null) {
					entity.setPatientCompany(new PatientCompany());
					entity.getPatientCompany().setId(entity.getPatientCompanyId());
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