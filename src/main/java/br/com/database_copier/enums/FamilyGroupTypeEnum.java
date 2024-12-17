/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum FamilyGroupTypeEnum {

	SPOUSE("SPOUSE", "CONJUGE", 1), CHILDREN("CHILDREN", "FILHO", 2), PARENTS("PARENTS", "PAIS", 3),
	SIBLING("SIBLING", "IRMAO", 4), IN_LAW("PARENTS IN LAW", "SOGRO", 5), OTHERS("OTHERS", "OUTROS", 6);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private FamilyGroupTypeEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}
