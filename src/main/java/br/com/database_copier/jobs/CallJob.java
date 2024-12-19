package br.com.database_copier.jobs;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.target.entities.Account;
import br.com.database_copier.target.entities.Call;
import br.com.database_copier.target.entities.Channel;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class CallJob {

	@SuppressWarnings("unchecked")
	public static void execute() {

		System.out.println("Montando query");

		String table = "call";

		String[] fields = { "id", "active", "call_status", "created_at", "created_by", "ended_at", "started_at",
				"updated_at", "updated_by", "patient_id", "receiver_id", "direct_receiver_id", "call_to_patient",
				"channel_id", "rating", "review", "callstatus", "calltopatient", "createdat", "createdby", "endedat",
				"startedat", "updatedat", "updatedby", "directreceiver_id" };

		Boolean hasMoreElements = Boolean.TRUE;

		int page = 0;

		while (hasMoreElements) {

			final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA, 500, page);

			System.out.println("Iniciando seção com origem");

			Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
			session.beginTransaction();
			System.out.println("Seção iniciada com origem");

			final List<Object[]> list = session.createSQLQuery(query).list();

			if (list.isEmpty()) {
				hasMoreElements = Boolean.FALSE;
				break;
			}

			final AbstractConverter<Call> converter = new AbstractConverter<Call>().convertjsonToEntityList(Call.class,
					GenericUtils.objectsToJson(list, fields));

			List<Call> entityList = converter.getEntities();

			for (final Call entity : entityList) {
				entity.setParticipants(new ArrayList<>());

				final String subQuery = "SELECT call_id, participants_id FROM " + GenericUtils.SOURCE_SCHEMA
						+ ".call_participants WHERE call_id = '" + entity.getId() + "'";

				final List<Object[]> objList = session.createSQLQuery(subQuery).list();

				for (final Object[] obj : objList) {
					if (obj[1] != null) {
						Account account = new Account();
						account.setId(obj[1].toString());

						entity.getParticipants().add(account);
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

				final Call entity = entityList.get(i);

				if (entity.getChannelId() != null) {
					entity.setChannel(new Channel());
					entity.getChannel().setId(entity.getChannelId());
				}

				if (entity.getPatientId() != null) {
					entity.setPatient(new Account());
					entity.getPatient().setId(entity.getPatientId());
				}

				if (entity.getReceiverId() != null) {
					entity.setReceiver(new Account());
					entity.getReceiver().setId(entity.getReceiverId());
				}

				if (entity.getDirectReceiverId() != null) {
					entity.setDirectReceiver(new Account());
					entity.getDirectReceiver().setId(entity.getDirectReceiverId());
				}

				if (i % 3000 == 0) {
					insertSession.getTransaction().commit();
					insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
					insertSession.beginTransaction();
				}

				String result = (String) insertSession
						.createSQLQuery(
								"SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + table + " WHERE id = :id")
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
		page++;
	}
}