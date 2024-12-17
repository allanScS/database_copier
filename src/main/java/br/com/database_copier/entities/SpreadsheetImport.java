package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.SpreadsheetImportOperationType;
import br.com.database_copier.enums.SpreadsheetImportStatus;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author allan scherrer
 *
 */
@Data
@Entity
@Table(name = "spreadsheetImport", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class SpreadsheetImport {

	@Id
	private String id;

	private Boolean deleted;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createdAt;

	private String createdBy;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime updatedAt;

	private String updatedBy;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime deletedAt;

	private String deletedBy;

	private String logFileName;

	private String logFileExtension;

	private String filename;

	private String fileExtension;

	@Enumerated(EnumType.STRING)
	private SpreadsheetImportOperationType operationType;

	@Enumerated(EnumType.STRING)
	private SpreadsheetImportStatus status;

	@ManyToOne
	private Account account;

	@Transient
	private String base64File;

	@Transient
	private String accountId;

}