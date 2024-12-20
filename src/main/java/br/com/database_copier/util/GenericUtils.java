package br.com.database_copier.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.json.JSONObject;

public class GenericUtils {

	public static final String SOURCE_SCHEMA = "central_saude_24h";

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

	public static JSONObject objectToJson(final Object[] obj, final String[] fields) {

		final JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < obj.length; i++) {
			jsonObject.put(convertToCamelCase(fields[i]), obj[i]);
		}

		return jsonObject;
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

}
