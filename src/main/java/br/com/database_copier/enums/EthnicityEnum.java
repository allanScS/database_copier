/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum EthnicityEnum {

	WHITE("WHITE", "branco", 1), BLACK("BLACK", "preto", 2), MIXED("MIXED", "pardo", 3), ASIAN("ASIAN", "amarelo", 4),
	INDIGENOUS("INDIGENOUS", "indígena", 5);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private EthnicityEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}
}
