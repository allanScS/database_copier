/**
 * 
 */
package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

	ROLE_MASTER("ROLE_MASTER", "super-usuário", 1), ROLE_ADMIN("ROLE_ADMIN", "administrador", 2),
	ROLE_SALES("ROLE_SALES", "vendas", 3), ROLE_FINANCES("ROLE_FINANCES", "finanças", 4),
	ROLE_CONSULTANT("ROLE_CONSULTANT", "consultor", 5), ROLE_PROVIDER("ROLE_PROVIDER", "provedor", 6),
	ROLE_DEVELOPER("ROLE_DEVELOPER", "desenvolvedor", 7);

	private final String id;
	private final String name;
	private final Integer hierarchy;

	RoleEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}
}
