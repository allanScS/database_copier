package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum RankingType {
	CONSULTANTS("CONSULTANTS", "consultores", 1), PROVIDERS("PROVIDERS", "provedores", 2),
	COMPANIES("COMPANIES", "empresas", 3);

	private final String id;
	private final String name;
	private final Integer hierarchy;

	RankingType(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
