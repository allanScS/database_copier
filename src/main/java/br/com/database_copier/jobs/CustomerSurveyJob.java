package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.CustomerSurvey;
import br.com.database_copier.entities.PatientCase;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class CustomerSurveyJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		final String sourceTable = "customer_survey";
		final String targetTable = "customerSurvey";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "is_answered", "patient_case_id" };

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

			final AbstractConverter<CustomerSurvey> converter = new AbstractConverter<CustomerSurvey>()
					.convertjsonToEntityList(CustomerSurvey.class, GenericUtils.objectsToJson(list, fields));

			final List<CustomerSurvey> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
			System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final CustomerSurvey entity = entityList.get(i);

				if (entity.getPatientCaseId() != null) {
					entity.setPatientCase(new PatientCase());
					entity.getPatientCase().setId(entity.getPatientCaseId());
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