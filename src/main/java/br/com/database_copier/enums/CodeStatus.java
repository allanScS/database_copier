package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum CodeStatus {
	PENDING("PENDING", "pendente", 1), VERIFIED("VERIFIED", "verificado", 2), EXPIRED("EXPIRED", "expirado", 3);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private CodeStatus(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
