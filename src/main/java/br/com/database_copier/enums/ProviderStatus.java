package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ProviderStatus {
	ACCREDITED("ACCREDITED", "credenciado", 1), ONGOING("ONGOING", "em credenciamento", 2),
	UNACCREDITED("UNACCREDITED", "des credenciado", 2), INA("INACTIVE", "inativo", 3);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private ProviderStatus(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}