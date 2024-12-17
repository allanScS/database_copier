package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum SupportTypeEnum {

	VISIT("VISIT", "visita", 1), INDIVIDUAL_SESSION("INDIVIDUAL_SESSION", "sessão individual", 2),
	COUPLE_SESSION("COUPLE_SESSION", "sessão casal", 3), LECTURE("LECTURE", "palestra", 4), CISD("CISD", "cisd", 5),
	LEGAL_CONSULTANCY("LEGAL_CONSULTANCY", "consultoria jurídica", 6),
	FINANCIAL_CONSULTANCY("FINANCIAL_CONSULTANCY", "consultoria financeira", 7);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private SupportTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
