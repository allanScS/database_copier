package br.com.database_copier.util;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;

public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		return (attribute == null) ? null : Date.valueOf(attribute);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		return (dbData == null) ? null : dbData.toLocalDate();
	}
}
