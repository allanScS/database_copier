package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.ProviderServiceModel;
import br.com.database_copier.entities.ProviderServiceModelAvailability;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class ProviderServiceModelAvailabilityJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		final String sourceTable = "provider_service_model_availability";
		final String targetTable = "providerServiceModelAvailability";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "end_time", "start_time", "weekday", "provider_service_model_id" };

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

			final AbstractConverter<ProviderServiceModelAvailability> converter = new AbstractConverter<ProviderServiceModelAvailability>()
					.convertjsonToEntityList(ProviderServiceModelAvailability.class,
							GenericUtils.objectsToJson(list, fields));

			final List<ProviderServiceModelAvailability> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
			System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final ProviderServiceModelAvailability entity = entityList.get(i);

				if (entity.getProviderServiceModelId() != null) {
					entity.setProviderServiceModel(new ProviderServiceModel());
					entity.getProviderServiceModel().setId(entity.getProviderServiceModelId());
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