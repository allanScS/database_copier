package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.DataIntegrationReferenceType;
import br.com.database_copier.enums.DataIntegrationStatus;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author allan scherrer
 */
@Data
@Entity
@Table(name = "dataIntegration", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class DataIntegration extends BaseEntity<String> {

	private static final long serialVersionUID = 6101910776879001187L;

	private String id;

	@Enumerated(EnumType.STRING)
	private DataIntegrationStatus status;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String message;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String responseMessage;

	@Enumerated(EnumType.STRING)
	private DataIntegrationReferenceType dataReference;

	private String dataReferenceId;

	private String technicalGuidanceId;

	private String beneficiaryId;

	private String dataCreatedId;

	private Integer attempts;

	@ManyToOne
	private Account account;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;
	
	@Transient
	private String accountId;

}
