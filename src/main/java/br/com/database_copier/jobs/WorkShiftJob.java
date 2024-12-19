package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.target.entities.WorkShift;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class WorkShiftJob {

	@SuppressWarnings("unchecked")
	public static void execute() {

		System.out.println("Montando query");

		String table = "work_shift";

		final String[] fields = { "id", "active", "created_at", "created_by", "day_of_week", "deleted", "deleted_at",
				"deleted_by", "ends_at", "starts_at", "updated_at", "updated_by", "account_id", "createdat",
				"createdby", "dayofweek", "deletedat", "deletedby", "endsat", "startsat", "updatedat", "updatedby" };

		final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA);

		System.out.println("Iniciando seção com origem");

		Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
		session.beginTransaction();
		System.out.println("Seção iniciada com origem");

		final AbstractConverter<WorkShift> converter = new AbstractConverter<WorkShift>().convertjsonToEntityList(
				WorkShift.class, GenericUtils.objectsToJson(session.createSQLQuery(query).list(), fields));

		List<WorkShift> entityList = converter.getEntities();

		System.out.println("Lista de dados criada");

		HibernateUtil.getSessionFactory().close();
		System.out.println("Transaction commited and Session closed");

		Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
		insertSession.beginTransaction();
		System.out.println("First database transaction started");
		int i = 0;

		for (i = 0; i < entityList.size(); i++) {

			final WorkShift entity = entityList.get(i);

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