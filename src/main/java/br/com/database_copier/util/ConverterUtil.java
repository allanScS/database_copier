package br.com.database_copier.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import br.com.database_copier.enums.GenderEnum;
import lombok.Getter;

@Getter
public class ConverterUtil<E> {

	public E convertJsonToEntity(final Class<E> reference, final JSONObject json) {

		try {
			Constructor<E> constructor = reference.getDeclaredConstructor();
			constructor.setAccessible(true);

			var entity = constructor.newInstance();

			Map<String, Object> map = json.toMap();

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					String value = entry.getValue().toString();

					if (!value.isEmpty() && !value.isBlank())
						try {
							final Field field = entity.getClass().getDeclaredField(entry.getKey());
							field.setAccessible(true);

							switch (field.getType().getSimpleName()) {
							case "String":
								field.set(entity, value);
								break;

							case "Long":
								field.set(entity, Long.parseLong(value));
								break;

							case "LocalDateTime":
								field.set(entity, parseLocalDateTime(value));
								break;

							case "LocalDate":
								field.set(entity, parseLocalDate(value));
								break;

							case "LocalTime":
								field.set(entity, parseLocalTime(value));
								break;

							case "Integer":
								field.set(entity, Integer.parseInt(value));
								break;

							case "Double":
								field.set(entity, Double.parseDouble(value));
								break;

							case "Float":
								field.set(entity, Float.parseFloat(value));
								break;

							case "Byte":
								field.set(entity, Byte.parseByte(value));
								break;

							case "BigInteger":
								field.set(entity, new BigInteger(value));
								break;

							case "BigDecimal":
								field.set(entity, new BigDecimal(value));
								break;

							case "Boolean":
								field.set(entity, Boolean.parseBoolean(value));
								break;

							case "Short":
								field.set(entity, Short.parseShort(value));
								break;

							case "GenderEnum":
								field.set(entity, GenderEnum.valueOf(value));
								break;

							default:
								break;
							}

							value = null;

						} catch (Exception e) {

							System.out
									.println("Falha ao converter o parametro: " + entry.getKey() + " valor: " + value);
						}
				}
			}

			constructor = null;
			map = null;

			return entity;

		} catch (Exception e) {
			System.out.println("ERRO AO CONVERTER A ENTIDADE: " + reference.getClass().getSimpleName());
			return null;
		}

	}

	private LocalDateTime parseLocalDateTime(String dateString) {
		List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
		formatters.add(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		formatters.add(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		if (dateString != null && !dateString.isEmpty() && !dateString.equals(" ")) {
			if (dateString.contains("Z"))
				dateString = dateString.replace("Z", "");
			for (DateTimeFormatter formatter : formatters) {
				try {
					LocalDateTime localDateTime = GenericUtils
							.adjustLocalDateTime(LocalDateTime.parse(dateString, formatter));
					formatters = null;
					dateString = null;
					return localDateTime;
				} catch (java.time.format.DateTimeParseException ignored) {
				}
			}
			System.out.println("ERRO AO CONVERTER A DATA/HORA: " + dateString);
		}
		return null;
	}

	public LocalDate parseLocalDate(String dateString) {
		List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		formatters.add(DateTimeFormatter.ISO_LOCAL_DATE);

		if (dateString != null && !dateString.isEmpty() && !dateString.equals(" ")) {
			if (dateString.contains("Z"))
				dateString = dateString.replace("Z", "");
			for (DateTimeFormatter formatter : formatters) {
				try {
					LocalDate localDate = LocalDate.parse(dateString, formatter);
					formatters = null;
					dateString = null;
					return localDate;
				} catch (java.time.format.DateTimeParseException ignored) {
				}
			}
			System.out.println("ERRO AO CONVERTER A DATA: " + dateString);
		}
		return null;
	}

	private LocalTime parseLocalTime(String dateString) {
		List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("HH:mm:ss"));
		formatters.add(DateTimeFormatter.ISO_LOCAL_TIME);

		if (dateString != null && !dateString.isEmpty() && !dateString.equals(" ")) {
			if (dateString.contains("Z"))
				dateString = dateString.replace("Z", "");
			for (DateTimeFormatter formatter : formatters) {
				try {
					LocalTime localTime = LocalTime.parse(dateString, formatter);
					formatters = null;
					dateString = null;
					return localTime;
				} catch (java.time.format.DateTimeParseException ignored) {
				}
			}
			System.out.println("ERRO AO CONVERTER A HORA: " + dateString);
		}
		return null;
	}

}
