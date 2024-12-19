package br.com.database_copier.jobs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.enums.Role;
import br.com.database_copier.target.entities.Profile;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class ProfileJob {

	@SuppressWarnings("unchecked")
	public static void execute() {

		System.out.println("Montando query");

		String table = "profile";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by",
				"description", "name", "updated_at", "updated_by", "createdat", "createdby", "deletedat", "deletedby",
				"updatedat", "updatedby" };

		final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA);

		System.out.println("Iniciando seção com origem");

		Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
		session.beginTransaction();
		System.out.println("Seção iniciada com origem");

		final AbstractConverter<Profile> converter = new AbstractConverter<Profile>().convertjsonToEntityList(
				Profile.class, GenericUtils.objectsToJson(session.createSQLQuery(query).list(), fields));

		List<Profile> entityList = converter.getEntities();

		for (final Profile profile : entityList) {
			profile.setRoles(new ArrayList<>());

			final String subQuery = "SELECT profile_id, role FROM " + GenericUtils.SOURCE_SCHEMA
					+ ".profiles_roles WHERE profile_id = '" + profile.getId() + "'";

			final List<Object[]> objList = session.createSQLQuery(subQuery).list();

			for (final Object[] obj : objList) {
				if (obj[1] != null)
					profile.getRoles().add(Role.valueOf(obj[1].toString()));

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

			final Profile entity = entityList.get(i);

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