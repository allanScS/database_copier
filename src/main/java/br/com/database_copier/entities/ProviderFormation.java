package br.com.database_copier.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.FormationStatusEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateConverter;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author weslleymatosdecarvalho
 *
 */
@Data
@Entity
@Table(name = "providerFormation", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class ProviderFormation {

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

	private String college;

	private String name;

	@Enumerated(EnumType.STRING)
	private FormationStatusEnum formationStatus;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate initialDate;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate finalDate;

	@ManyToOne
	private Provider provider;

	private Boolean favourite;

	@Transient
	private String providerId;

}
