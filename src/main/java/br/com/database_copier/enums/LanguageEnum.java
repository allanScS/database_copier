package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum LanguageEnum {

	PORTUGUESE("PORTUGUESE", "português", 1), ENGLISH("ENGLISH", "inglês", 2), CHINESE("CHINESE", "chinês", 3),
	HINDI("HINDI", "indiano", 4), SPANISH("SPANISH", "espanhol", 5), ARABIC("ARABIC", "árabe", 6),
	BENGALI("BENGALI", "bengali", 7), FRENCH("FRENCH", "francês", 8), RUSSIAN("RUSSIAN", "russo", 9),
	URDU("URDU", "urdu", 10), INDONESIAN("INDONESIAN", "indonésio", 11), GERMAN("GERMAN", "alemão", 12),
	JAPANESE("JAPANESE", "japonês", 13), MARATI("MARATI", "marati", 14), TELUG("TELUG", "telugu", 15),
	TURKISH("TURKISH", "turco", 16), TAMIL("TAMIL", "tâmil", 17), KOREAN("KOREAN", "coreano", 18),
	ITALIAN("ITALIAN", "italiano", 19), SIGN_LANGUAGE("SIGN_LANGUAGE", "libras", 20);

	private String id;

	private String name;

	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private LanguageEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
