package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum SystemType {

	ANDROID("android"), IOS("ios");

	private String name;

	SystemType(final String name) {
		this.name = name;
	}

}
