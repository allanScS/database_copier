/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ServiceChannelEnum {

	EMAIL("EMAIL", "EMAIL", 1), PHONE("PHONE", "TELEFONE", 2), WHATSAPP("WHATSAPP", "WHATSAPP", 3),
	CHATBOT("CHATBOT", "CHATBOT", 4), FREE("0800", "0800", 5);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private ServiceChannelEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
