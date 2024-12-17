package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum BranchOfActivityEnum {

	RETAIL("RETAIL", "varejo", 1), SERVICES("SERVICES", "prestação de serviços", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private BranchOfActivityEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
