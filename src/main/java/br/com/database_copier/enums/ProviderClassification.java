package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ProviderClassification {
	EXTERNAL("Externo"), INTERNAL("Interno");

	private String name;

	ProviderClassification(final String name) {
		this.name = name;
	}

}
