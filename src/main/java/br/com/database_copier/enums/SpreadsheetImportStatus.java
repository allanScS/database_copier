package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum SpreadsheetImportStatus {
	PROCESSING("Processando"), FAILURE("Falha"), COMPLETED("Finalizado");

	private String name;

	/**
	 * 
	 * @param level
	 */
	private SpreadsheetImportStatus(String name) {
		this.name = name;
	}

}
