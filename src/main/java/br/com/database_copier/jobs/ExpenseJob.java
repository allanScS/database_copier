package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.Category;
import br.com.database_copier.entities.Event;
import br.com.database_copier.entities.Expense;
import br.com.database_copier.entities.ProviderPayment;
import br.com.database_copier.entities.Supplier;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class ExpenseJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		String table = "expense";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "observation", "description", "value", "additions", "discounts", "final_value",
				"due_date", "bill_date", "effectuated", "effectuated_at", "bill_number", "installment",
				"installments_number", "installment_value", "supplier_id", "category_id", "provider_payment_id",
				"event_id" };

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

			final AbstractConverter<Expense> converter = new AbstractConverter<Expense>()
					.convertjsonToEntityList(Expense.class, GenericUtils.objectsToJson(list, fields));

			final List<Expense> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
			System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final Expense entity = entityList.get(i);

				if (entity.getSupplierId() != null) {
					entity.setSupplier(new Supplier());
					entity.getSupplier().setId(entity.getSupplierId());
				}

				if (entity.getCategoryId() != null) {
					entity.setCategory(new Category());
					entity.getCategory().setId(entity.getCategoryId());
				}

				if (entity.getProviderPaymentId() != null) {
					entity.setProviderPayment(new ProviderPayment());
					entity.getProviderPayment().setId(entity.getProviderPaymentId());
				}

				if (entity.getEventId() != null) {
					entity.setEvent(new Event());
					entity.getEvent().setId(entity.getEventId());
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