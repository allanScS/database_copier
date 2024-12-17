package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum AccountOwner {
	HOLDER("Titular"), THIRD("Terceiro");

	private String name;

	AccountOwner(final String name) {
		this.name = name;
	}

}
