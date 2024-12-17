package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum PriorityEnum {

	URGENCY("URGENCY", "URGENTE", 1), REGULAR("REGULAR", "NORMAL", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private PriorityEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
