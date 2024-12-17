package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum BodyType {
	CASE_REFUSE("case-refuse"), CASE_CLOSURE("case-closure"), CASE_ACCEPT("case-accept"),
	NEW_FORWARDING("new-forwarding"), RECEIVED_CODE("received-code"), WELCOME("welcome"),

	// SESSIONS REVIEW
	SESSION_REVIEW("session-review-carelink-eap"), SESSION_REVIEW_LOGUM("session-review-Logum"),
	SESSION_REVIEW_FM("session-review-FM"), SESSION_REVIEW_MXM("session-review-MXM"),
	SESSION_REVIEW_OCEAN_PACT("session-review-OceanPact"), SESSION_REVIEW_SCHOTT("session-review-SCHOTT"),
	SESSION_REVIEW_AVON("session-review-AVON"), SESSION_REVIEW_BAYER("session-review-BAYER"),
	SESSION_REVIEW_CARELINK("session-review-CARELINK"),
	SESSION_REVIEW_DOR_CONSULTORIA("session-review-DOR-Consultoria"), SESSION_REVIEW_GB("session-review-GB"),
	SESSION_REVIEW_LOURES("session-review-Loures"), SESSION_REVIEW_NATURA("session-review-NATURA"),
	SESSION_REVIEW_PEAC("session-review-Peac"), SESSION_REVIEW_STEFANINI("session-review-STEFANINI"),
	SESSION_REVIEW_DOCK_CARE("session-review-DockCare"), SESSION_REVIEW_CAMORIM("session-review-CAMORIM"),
	SESSION_REVIEW_UNILEVER("session-review-UNILEVER"), SESSION_REVIEW_THYSSENKRUPP("session-review-THYSSENKRUPP"),
	SESSION_REVIEW_B2("session-review-B2");

	private String name;

	BodyType(final String name) {
		this.name = name;
	}

}