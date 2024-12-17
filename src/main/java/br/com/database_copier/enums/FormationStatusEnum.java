/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum FormationStatusEnum {

	COMPLETE_PRIMARY_EDUCATION("COMPLETE_PRIMARY_EDUCATION", "Ensino Fundamental Completo", 1),
	INCOMPLETE_PRIMARY_EDUCATION("INCOMPLETE_PRIMARY_EDUCATION", "Ensio Fundamental Incompleto", 2),
	COMPLETE_HIGH_SCHOOL("COMPLETE_HIGH_SCHOOL", "Ensio Médio Completo", 3),
	INCOMPLETE_HIGH_SCHOOL("INCOMPLETE_HIGH_SCHOOL", "Ensio Médio Incompleto", 4),
	COMPLETE_GRADUATION("COMPLETE_GRADUATION", "Graduação Completa", 5),
	INCOMPLETE_GRADUATION("INCOMPLETE_GRADUATION", "Graduação Incompleta", 6),
	COMPLETE_MASTERS_DEGREE("COMPLETE_MASTERS_DEGREE", "Mestrado Completo", 7),
	INCOMPLETE_MASTERS_DEGREE("INCOMPLETE_MASTERS_DEGREE", "Mestrado Incompleto", 7);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private FormationStatusEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
