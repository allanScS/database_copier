package br.com.neoapp.base;

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

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.database_copier.enums.AccountOwner;
import br.com.database_copier.enums.BodyType;
import br.com.database_copier.enums.BranchOfActivityEnum;
import br.com.database_copier.enums.CategoryTypeEnum;
import br.com.database_copier.enums.CodeStatus;
import br.com.database_copier.enums.CodeTypeEnum;
import br.com.database_copier.enums.CoverageEnum;
import br.com.database_copier.enums.DepartmentEnum;
import br.com.database_copier.enums.ElegibleEnum;
import br.com.database_copier.enums.EthnicityEnum;
import br.com.database_copier.enums.EventStatusEnum;
import br.com.database_copier.enums.EventTypeEnum;
import br.com.database_copier.enums.EvolutionTypeEnum;
import br.com.database_copier.enums.FamilyGroupTypeEnum;
import br.com.database_copier.enums.FormationStatusEnum;
import br.com.database_copier.enums.ForwardingStatusEnum;
import br.com.database_copier.enums.GenderEnum;
import br.com.database_copier.enums.LanguageEnum;
import br.com.database_copier.enums.MaritalStatusEnum;
import br.com.database_copier.enums.NotifierEnum;
import br.com.database_copier.enums.OriginEnum;
import br.com.database_copier.enums.PriorityEnum;
import br.com.database_copier.enums.ProblemHolderEnum;
import br.com.database_copier.enums.ProductEnum;
import br.com.database_copier.enums.ProviderClassification;
import br.com.database_copier.enums.ProviderStatus;
import br.com.database_copier.enums.RankingType;
import br.com.database_copier.enums.RelationshipEnum;
import br.com.database_copier.enums.RoleEnum;
import br.com.database_copier.enums.ScheduleTypeEnum;
import br.com.database_copier.enums.ServiceChannelEnum;
import br.com.database_copier.enums.ServiceModelTypeEnum;
import br.com.database_copier.enums.SpreadsheetImportOperationType;
import br.com.database_copier.enums.SpreadsheetImportStatus;
import br.com.database_copier.enums.SupportTypeEnum;
import br.com.database_copier.enums.TwoFactorsType;
import br.com.database_copier.enums.WeekDayEnum;
import br.com.database_copier.util.GenericUtils;
import lombok.Getter;

@Getter
public class AbstractConverter<E> {

	private List<E> entities = new ArrayList<>();

	private String parametersFailed;

	public AbstractConverter<E> convertjsonToEntityList(final Class<E> reference, final JSONArray dataArray) {

		for (final Object object : dataArray) {
			entities.add(convertJsonToEntity(reference, object.toString()));
		}
		return this;

	}

	private E convertJsonToEntity(final Class<E> reference, final String json) {

		try {
			final Constructor<E> constructor = reference.getDeclaredConstructor();
			constructor.setAccessible(true);

			final var entity = constructor.newInstance();

			final Map<String, Object> map = new JSONObject(json).toMap();

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					final String value = entry.getValue().toString();

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

							case "AccountOwner":
								field.set(entity, AccountOwner.valueOf(value));
								break;

							case "BodyType":
								field.set(entity, BodyType.valueOf(value));
								break;

							case "BranchOfActivityEnum":
								field.set(entity, BranchOfActivityEnum.valueOf(value));
								break;

							case "CategoryTypeEnum":
								field.set(entity, CategoryTypeEnum.valueOf(value));
								break;

							case "CodeStatus":
								field.set(entity, CodeStatus.valueOf(value));
								break;

							case "CodeTypeEnum":
								field.set(entity, CodeTypeEnum.valueOf(value));
								break;

							case "CoverageEnum":
								field.set(entity, CoverageEnum.valueOf(value));
								break;

							case "DepartmentEnum":
								field.set(entity, DepartmentEnum.valueOf(value));
								break;

							case "ElegibleEnum":
								field.set(entity, ElegibleEnum.valueOf(value));
								break;

							case "EthnicityEnum":
								field.set(entity, EthnicityEnum.valueOf(value));
								break;

							case "EventStatusEnum":
								field.set(entity, EventStatusEnum.valueOf(value));
								break;

							case "EventTypeEnum":
								field.set(entity, EventTypeEnum.valueOf(value));
								break;

							case "EvolutionTypeEnum":
								field.set(entity, EvolutionTypeEnum.valueOf(value));
								break;

							case "FamilyGroupTypeEnum":
								field.set(entity, FamilyGroupTypeEnum.valueOf(value));
								break;

							case "FormationStatusEnum":
								field.set(entity, FormationStatusEnum.valueOf(value));
								break;

							case "ForwardingStatusEnum":
								field.set(entity, ForwardingStatusEnum.valueOf(value));
								break;

							case "GenderEnum":
								field.set(entity, GenderEnum.valueOf(value));
								break;

							case "LanguageEnum":
								field.set(entity, LanguageEnum.valueOf(value));
								break;

							case "MaritalStatusEnum":
								field.set(entity, MaritalStatusEnum.valueOf(value));
								break;

							case "NotifierEnum":
								field.set(entity, NotifierEnum.valueOf(value));
								break;

							case "OriginEnum":
								field.set(entity, OriginEnum.valueOf(value));
								break;

							case "PriorityEnum":
								field.set(entity, PriorityEnum.valueOf(value));

							case "ProblemHolderEnum":
								field.set(entity, ProblemHolderEnum.valueOf(value));
								break;

							case "ProductEnum":
								field.set(entity, ProductEnum.valueOf(value));
								break;

							case "ProviderClassification":
								field.set(entity, ProviderClassification.valueOf(value));
								break;

							case "ProviderStatus":
								field.set(entity, ProviderStatus.valueOf(value));
								break;

							case "RankingType":
								field.set(entity, RankingType.valueOf(value));
								break;

							case "RelationshipEnum":
								field.set(entity, RelationshipEnum.valueOf(value));
								break;

							case "RoleEnum":
								field.set(entity, RoleEnum.valueOf(value));

							case "ScheduleTypeEnum":
								field.set(entity, ScheduleTypeEnum.valueOf(value));
								break;

							case "ServiceChannelEnum":
								field.set(entity, ServiceChannelEnum.valueOf(value));
								break;

							case "ServiceModelTypeEnum":
								field.set(entity, ServiceModelTypeEnum.valueOf(value));
								break;

							case "SpreadsheetImportOperationType":
								field.set(entity, SpreadsheetImportOperationType.valueOf(value));
								break;

							case "SpreadsheetImportStatus":
								field.set(entity, SpreadsheetImportStatus.valueOf(value));
								break;

							case "SupportTypeEnum":
								field.set(entity, SupportTypeEnum.valueOf(value));
								break;

							case "TwoFactorsType":
								field.set(entity, TwoFactorsType.valueOf(value));
								break;

							case "WeekDayEnum":
								field.set(entity, WeekDayEnum.valueOf(value));
								break;

							default:
								if (this.parametersFailed == null)
									this.parametersFailed = "Falha ao converter o parametro: " + entry.getKey()
											+ " valor: " + value + ", tipo não mapeado";
								else if (!this.parametersFailed.contains(entry.getKey()))
									this.parametersFailed = this.parametersFailed + "\nFalha ao converter o parametro: "
											+ entry.getKey() + " valor: " + value + ", tipo não mapeado";
							}

						} catch (Exception e) {

							if (entry.getKey() != null)
								if (this.parametersFailed == null)
									this.parametersFailed = "Falha ao converter o parametro: " + entry.getKey()
											+ " valor: " + value;
								else if (!this.parametersFailed.contains(entry.getKey()))
									this.parametersFailed = this.parametersFailed + "\nFalha ao converter o parametro: "
											+ entry.getKey() + " valor: " + value;
						}
				}
			}
			return entity;

		} catch (Exception e) {
			System.out.println("ERRO AO CONVERTER A ENTIDADE: " + reference.getClass().getSimpleName());
			return null;
		}

	}

	private static LocalDateTime parseLocalDateTime(String dateString) {
		final List<DateTimeFormatter> formatters = new ArrayList<>();
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
					return GenericUtils.adjustLocalDateTime(LocalDateTime.parse(dateString, formatter));
				} catch (java.time.format.DateTimeParseException ignored) {
				}
			}
			System.out.println("ERRO AO CONVERTER A DATA/HORA: " + dateString);
		}
		return null;
	}

	public static LocalDate parseLocalDate(String dateString) {
		final List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		formatters.add(DateTimeFormatter.ISO_LOCAL_DATE);

		if (dateString != null && !dateString.isEmpty() && !dateString.equals(" ")) {
			if (dateString.contains("Z"))
				dateString = dateString.replace("Z", "");
			for (DateTimeFormatter formatter : formatters) {
				try {
					return LocalDate.parse(dateString, formatter);
				} catch (java.time.format.DateTimeParseException ignored) {
				}
			}
			System.out.println("ERRO AO CONVERTER A DATA: " + dateString);
		}
		return null;
	}

	private static LocalTime parseLocalTime(String dateString) {
		final List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("HH:mm:ss"));
		formatters.add(DateTimeFormatter.ISO_LOCAL_TIME);

		if (dateString != null && !dateString.isEmpty() && !dateString.equals(" ")) {
			if (dateString.contains("Z"))
				dateString = dateString.replace("Z", "");
			for (DateTimeFormatter formatter : formatters) {
				try {
					return LocalTime.parse(dateString, formatter);
				} catch (java.time.format.DateTimeParseException ignored) {
				}
			}
			System.out.println("ERRO AO CONVERTER A HORA: " + dateString);
		}
		return null;
	}

}
