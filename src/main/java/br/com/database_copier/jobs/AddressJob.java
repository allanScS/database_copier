package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.target.entities.Address;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class AddressJob {

	@SuppressWarnings("unchecked")
	public static void execute() {

		System.out.println("Montando query");

		String table = "address";

		final String[] fields = { "id", "active", "city", "complement", "country", "created_at", "created_by",
				"district", "latitude", "longitude", "number", "postal_code", "reference_point", "state", "street",
				"updated_at", "updated_by", "createdat", "createdby", "postalcode", "referencepoint", "updatedat",
				"updatedby" };

		final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA);

		System.out.println("Iniciando seção com origem");

		Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
		session.beginTransaction();
		System.out.println("Seção iniciada com origem");

		final AbstractConverter<Address> converter = new AbstractConverter<Address>().convertjsonToEntityList(
				Address.class, GenericUtils.objectsToJson(session.createSQLQuery(query).list(), fields));

		List<Address> entityList = converter.getEntities();

		System.out.println("Lista de dados criada");

		HibernateUtil.getSessionFactory().close();
		System.out.println("Transaction commited and Session closed");

		Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
		insertSession.beginTransaction();
		System.out.println("First database transaction started");
		int i = 0;

		for (i = 0; i < entityList.size(); i++) {

			final Address entity = entityList.get(i);

			if (i % 3000 == 0) {
				insertSession.getTransaction().commit();
				insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
				insertSession.beginTransaction();
			}

			String result = (String) insertSession
					.createSQLQuery("SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + table + " WHERE id = :id")
					.setParameter("id", entity.getId()).uniqueResult();

			if (result == null) {
				insertSession.save(entity);
				System.out.println("Inserted MAP: " + entity.getId());
			}
		}

		if (!insertSession.getTransaction().wasCommitted()) {
			insertSession.getTransaction().commit();
		}

		HibernateUtil.getSessionFactory().close();
		System.out.println("Transaction commited and Session closed");
		System.out.println(i + " new data inserted");
	}
}