package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {

	COMPLETE_EAP("COMPLETE_EAP", "EAP COMPLETO", 1), EMOTIONAL("EMOTIONAL", "EMOCIONAL", 2),
	SOCIAL("SOCIAL", "SOCIAL", 3), PENSION("PENSION", "PREVIDENCIÁRIO", 4), LEGAL("LEGAL", "JURÍDICO", 5),
	EVENT("EVENT", "EVENTOS IN COMPANY", 6);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private ProductEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}