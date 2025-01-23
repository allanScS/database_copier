package br.com.database_copier.util;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExecutePageUtil {

	public <T> void executePage(String[] fields, String sourceTable, String targetTable, Integer itensPerPage,
			Integer page, Integer totalPages, Session source, Class<T> entityType) {

		Boolean success = false;
		Session target = null;
		Transaction targetTransaction = null;
		ScrollableResults results = null;
		T entity = null;

		String query = GenericUtils.buildSql(fields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage, page);

		while (!success) {
			try {
				System.out.printf("BUSCANDO %s PAGINA: %d/%d%n", entityType.getSimpleName(), page + 1, totalPages);

				target = HibernateUtil.startSessionFactoryTargetDatabase().openSession();
				targetTransaction = target.beginTransaction();

				results = source.createNativeQuery(query).setTimeout(600000).setFetchSize(itensPerPage)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					entity = new ConverterUtil<T>().convertJsonToEntity(entityType,
							GenericUtils.objectToJson(results.get(), fields));

					setDependencies(entity, entityType);

					target.saveOrUpdate(entity);

					entity = null;
				}

				targetTransaction.commit();
				success = true;

			} catch (Exception e) {
				System.err.println("Erro ao processar a página: " + (page + 1));

				try {
					Thread.sleep(2000);
				} catch (InterruptedException interruptedException) {
					Thread.currentThread().interrupt();
				}
			} finally {
				if (target != null && target.isOpen()) {
					target.clear();
					target.close();
					source.clear();
				}
			}

		}

		results = null;
		query = null;
		targetTransaction = null;
		target = null;
		success = true;
		fields = null;
		sourceTable = null;
		targetTable = null;
		itensPerPage = null;
		page = null;
		totalPages = null;
		source = null;
		entityType = null;
		success = null;

	}

	public <T> void setDependencies(T entity, final Class<T> entityType) {

		// Método utilizado para tratar relacionamentos de tabelas
	}

}
