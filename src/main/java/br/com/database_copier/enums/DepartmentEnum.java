package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum DepartmentEnum {

	ADMINISTRATIVE("ADMINISTRATIVE", "administrativo", 1), COMMERCIAL("COMMERCIAL", "comercial", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private DepartmentEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
