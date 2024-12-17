package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.Occupation;
import br.com.database_copier.entities.Provider;
import br.com.database_copier.entities.Supplier;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class ProviderJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		String table = "provider";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "account_digit", "account_number", "accreditation_date", "active", "agency", "bank",
				"bank_code", "birth_date", "certificate_number", "corporate_name", "corporate_tax_number", "gender",
				"image_url", "marital_status", "name", "pix_key", "provider_status", "registration", "social_name",
				"supplier", "tags", "tax_number", "occupation_id", "ethnicity_enum", "account_id", "is_corporate",
				"supplier_id", "classification" };

		Boolean hasMoreElements = Boolean.TRUE;

		int page = 0;

		while (hasMoreElements) {

			System.out.println("BUSCANDO A PAGINA: " + page);

			final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA, itensPerPage, page);

			System.out.println("Iniciando seção com origem");

			Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
			session.beginTransaction();
			System.out.println("Seção iniciada com origem");

			final List<Object[]> list = session.createSQLQuery(query).list();

			if (list.isEmpty()) {
				hasMoreElements = Boolean.FALSE;
				break;
			}

			final AbstractConverter<Provider> converter = new AbstractConverter<Provider>()
					.convertjsonToEntityList(Provider.class, GenericUtils.objectsToJson(list, fields));

			final List<Provider> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final Provider entity = entityList.get(i);

				if (entity.getSupplierId() != null) {
					entity.setSupplier(new Supplier());
					entity.getSupplier().setId(entity.getSupplierId());
				}

				if (entity.getOccupationId() != null) {
					entity.setOccupation(new Occupation());
					entity.getOccupation().setId(entity.getOccupationId());
				}

				if (i % 3000 == 0) {
					insertSession.getTransaction().commit();
					insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
					insertSession.beginTransaction();
				}

				final String result = (String) insertSession
						.createSQLQuery(
								"SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + table + " WHERE id = :id")
						.setParameter("id", entity.getId()).uniqueResult();

				if (result == null) {
					insertSession.save(entity);
					System.out.println("Inserted MAP: " + entity.getId() + " : " + table);
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