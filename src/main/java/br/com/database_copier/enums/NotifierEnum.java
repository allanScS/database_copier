package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum NotifierEnum {

	MEDICAL_AREA("MEDICAL_AREA", "ÁREA MÉDICA", 1), AUTO_FOWARD("AUTO_FOWARD", "AUTO", 2),
	COWORKER("COWORKER", "COLEGA", 3), RELATIVE("RELATIVE", "FAMILIAR", 4), MANAGER("MANAGER", "GESTOR", 5),
	HR("HR", "RH", 6);

	private String id;

	private String name;

	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private NotifierEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
