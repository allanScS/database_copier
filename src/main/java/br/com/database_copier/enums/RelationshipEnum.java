/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum RelationshipEnum {

	HOLDER("HOLDER", "TITULAR", 1), SPOUSE("SPOUSE", "CONJUGE", 2), CHILDREN("CHILDREN", "FILHO", 3),
	PARENTS("PARENTS", "PAIS", 4), SIBLING("SIBLING", "IRMAO", 5), PARENTS_IN_LAW("PARENTS IN LAW", "SOGRO", 6),
	OTHERS("OTHERS", "OUTROS", 7);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private RelationshipEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}

}