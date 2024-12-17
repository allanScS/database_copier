/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum CodeTypeEnum {

	FORGOTTEN_PASSWORD("FORGOTTEN_PASSWORD", "Esqueceu a senha ou Primeiro acesso", 1),
	ACESS_CODE("ACESS_CODE", "Codigo de acesso de dupla atenticação", 2);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private CodeTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}