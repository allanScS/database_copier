package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum CoverageEnum {

	MUNICIPAL("MUNICIPAL", "municipal", 1), REGIONAL("REGIONAL", "regional", 2), NATIONAL("NATIONAL", "nacional", 3);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private CoverageEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
