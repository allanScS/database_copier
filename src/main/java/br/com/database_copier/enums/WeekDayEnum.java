package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum WeekDayEnum {
	MONDAY("MONDAY", "segunda", 1), TUESDAY("TUESDAY", "terça", 2), WEDNESDAY("WEDNESDAY", "quarta", 3),
	THURSDAY("THURSDAY", "quinta", 4), FRIDAY("FRIDAY", "sexta", 5), SATURDAY("SATURDAY", "sábado", 6),
	SUNDAY("SUNDAY", "domingo", 7);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private WeekDayEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
