package br.com.database_copier.jobs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.target.entities.Account;
import br.com.database_copier.target.entities.Channel;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class ChannelJob {

	@SuppressWarnings("unchecked")
	public static void execute() {

		System.out.println("Montando query");

		String table = "channel";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by",
				"description", "updated_at", "updated_by", "viwed", "name", "channel_id", "sender_id", "createdat",
				"createdby", "deletedat", "deletedby", "updatedat", "updatedby" };

		final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA);

		System.out.println("Iniciando seção com origem");

		Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
		session.beginTransaction();
		System.out.println("Seção iniciada com origem");

		final AbstractConverter<Channel> converter = new AbstractConverter<Channel>().convertjsonToEntityList(
				Channel.class, GenericUtils.objectsToJson(session.createSQLQuery(query).list(), fields));

		List<Channel> entityList = converter.getEntities();

		for (final Channel entity : entityList) {
			entity.setAccounts(new ArrayList<>());

			final String subQuery = "SELECT channel_id, account_id FROM " + GenericUtils.SOURCE_SCHEMA
					+ ".channel_account WHERE channel_id = '" + entity.getId() + "'";

			final List<Object[]> objList = session.createSQLQuery(subQuery).list();

			for (final Object[] obj : objList) {
				if (obj[1] != null) {
					Account account = new Account();
					account.setId(obj[1].toString());

					entity.getAccounts().add(account);
				}

			}
		}

		System.out.println("Lista de dados criada");

		HibernateUtil.getSessionFactory().close();
		System.out.println("Transaction commited and Session closed");

		Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
		insertSession.beginTransaction();
		System.out.println("First database transaction started");
		int i = 0;

		for (i = 0; i < entityList.size(); i++) {

			final Channel entity = entityList.get(i);

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