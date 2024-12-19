package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum EmailBody {

	RECEIVED_CODE("received-code"),
	REQUEST_DATA_DELETION("request-data-deletion"),
	CONFIRM_DATA_DELETION("confirm-data-deletion"),
	APPOINTMENT_FIRST_REMINDER("appointment-first-reminder"),
	APPOINTMENT_SECOND_REMINDER("appointment-second-reminder"),
	APPOINTMENT_CONFIRMATION("appointment-confirmation");

	private String name;

	EmailBody(final String name) {
		this.name = name;
	}

}