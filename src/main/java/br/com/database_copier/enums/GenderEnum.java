/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {

	MALE("MALE", "MASCULINO", 1), FEMALE("FEMALE", "FEMININO", 2), TRANS("TRANS", "TRANS", 3);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private GenderEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
