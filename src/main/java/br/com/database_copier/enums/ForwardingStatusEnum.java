package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum ForwardingStatusEnum {

	ACCEPTED("ACCEPTED", "aceito", 1), PENDING("PENDING", "pendente", 2), REFUSED("REFUSED", "recusado", 3),
	FINALIZED("FINALIZED", "finalizado", 4), PAID("PAID", "pago", 5), CANCELED("CANCELED", "cancelado", 6);

	private String id;
	private String name;
	private Integer hierarchy;

	/**
	 * 
	 * @param level
	 */
	private ForwardingStatusEnum(String id, String name, final Integer hierarchy) {
		this.id = id;
		this.name = name;
		this.hierarchy = hierarchy;
	}
}
