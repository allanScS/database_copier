/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum OriginEnum {

	EMAIL("EMAIL", "EMAIL", 1), FREE("0800", "0800", 2), CHATBOT("CHATBOT", "CHATBOT", 3);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private OriginEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
