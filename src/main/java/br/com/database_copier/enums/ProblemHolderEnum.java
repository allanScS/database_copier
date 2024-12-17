package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ProblemHolderEnum {

	EMPLOYEE("EMPLOYEE", "COLABORADOR", 1), SPOUSE("SPOUSE", "CÔNJUGE", 2), UNPLACED("UNPLACED", "DESLIGADO", 3),
	CHILDREN("CHILDREN", "FILHOS", 4), MANAGER("MANAGER", "GESTOR", 5), PARENTS("PARENTS", "PAIS", 6),
	HR("HR", "RH", 7);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private ProblemHolderEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}