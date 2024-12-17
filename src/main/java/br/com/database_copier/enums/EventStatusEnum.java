package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum EventStatusEnum {

	BOOKED("BOOKED", "Previsto", 1), DONE("DONE", "Realizado", 2), CANCELED("CANCELED", "Não realizdo", 3);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private EventStatusEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
