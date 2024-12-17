package br.com.database_copier.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.neoapp.base.AbstractConverter;

public class GenericUtils {

	public static final String SOURCE_SCHEMA = "carelink_eap";

	public static final String TARGET_SCHEMA = "dbo";

	public static String buildSql(final String[] fields, final String table, final String schema,
			final Integer itensPerPage, final Integer page) {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT");

		int i = 0;
		for (String field : fields) {

			if (i == 0)
				sb.append(" entity.");
			else
				sb.append(", entity.");

			sb.append(field);

			i++;
		}

		sb.append(" FROM ");
		sb.append(schema);
		sb.append(".");
		sb.append(table);
		sb.append(" AS entity");
		sb.append(" LIMIT ");
		sb.append(itensPerPage);
		sb.append(" OFFSET ");
		sb.append(itensPerPage * page);
		sb.append(";");

		return sb.toString();

	}

	public static JSONArray objectsToJson(final List<Object[]> objList, final String[] fields) {
		final JSONArray jsonArray = new JSONArray();

		for (Object[] row : objList) {
			final JSONObject jsonObject = new JSONObject();

			for (int i = 0; i < row.length; i++) {
				jsonObject.put(convertToCamelCase(fields[i]), row[i]);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private static String convertToCamelCase(String fieldName) {
		final String[] parts = fieldName.split("_");
		final StringBuilder camelCaseName = new StringBuilder(parts[0].toLowerCase());

		for (int i = 1; i < parts.length; i++) {
			camelCaseName.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
		}

		return camelCaseName.toString();
	}

	public static LocalDateTime adjustLocalDateTime(final LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		else
			return localDateTime;
//			return localDateTime.minusHours(3);
	}

	public static LocalTime adjustLocalTime(final LocalTime localTime) {
		if (localTime == null)
			return null;
		else
			return localTime;
//			return localTime.minusHours(3);
	}

	@SuppressWarnings("unchecked")
	public static <T> void executePage(final String[] fields, final String sourceTable, final String targetTable,
			final Integer itensPerPage, final Integer page, final Integer totalPages, final Session source,
			final Class<T> entityType)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		System.out.printf("BUSCANDO A PAGINA: %d/%d%n", page + 1, totalPages);

		final Session target = HibernateUtil.startSessionFactoryTargetDatabase().openSession();

		final Transaction targetTransaction = target.beginTransaction();

		final String query = GenericUtils.buildSql(fields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage, page);

		final List<Object[]> list = source.createSQLQuery(query).list();

		final AbstractConverter<T> converter = new AbstractConverter<T>().convertjsonToEntityList(entityType,
				GenericUtils.objectsToJson(list, fields));

		final List<T> entityList = converter.getEntities();

		for (T entity : entityList) {

			final Field idField = entityType.getDeclaredField("id");
			idField.setAccessible(true);

			final Object idValue = idField.get(entity);

			final String result = (String) target
					.createSQLQuery(
							"SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + targetTable + " WHERE id = :id")
					.setParameter("id", idValue).uniqueResult();

			if (result == null) {
				synchronized (target) {
					target.save(entity);
				}
			}
		}

		if (!targetTransaction.wasCommitted()) {
			targetTransaction.commit();
		}
	}

}
