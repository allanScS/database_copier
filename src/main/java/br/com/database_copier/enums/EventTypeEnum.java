package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum EventTypeEnum {

	LECTURE("LECTURE", "Palestra", 1), CONVERSATION_CIRCLE("CONVERSATION_CIRCLE", "Roda de conversa", 2),
	ONSITE_DUTY("ONSITE_DUTY", "Plantão Onsite", 3), COURSES("COURSES", "Cursos", 4), WEBNAR("WEBNAR", "Webnar", 5),
	CISD("CISD", "CISD", 6);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private EventTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
