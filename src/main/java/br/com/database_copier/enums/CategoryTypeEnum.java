package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum CategoryTypeEnum {

	REVENUE("REVENUE", "receita", 1), EXPENSE("EXPENSE", "despesa", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private CategoryTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
