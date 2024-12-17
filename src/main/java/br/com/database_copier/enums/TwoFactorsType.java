package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum TwoFactorsType {
	EMAIL("E-mail"), SMS("sms");

	private String name;

	TwoFactorsType(final String name) {
		this.name = name;
	}

}