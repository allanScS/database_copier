/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum CallTypeEnum {

	ACTIVE("ACTIVE", "ATIVO", 1), RECEPTIVE("RECEPTIVE", "RECEPTIVO", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private CallTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}