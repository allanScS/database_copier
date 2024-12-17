package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ServiceModelTypeEnum {

	ONLINE("ONLINE", "online", 1), PRESENTIAL("PRESENTIAL", "presencial", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private ServiceModelTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
