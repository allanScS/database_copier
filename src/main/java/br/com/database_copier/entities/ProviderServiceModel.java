package br.com.database_copier.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.ServiceModelTypeEnum;
import br.com.database_copier.enums.SupportTypeEnum;
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
@Table(name = "providerServiceModel", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class ProviderServiceModel {

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

	@Enumerated(EnumType.STRING)
	private ServiceModelTypeEnum serviceModelType;

	@Enumerated(EnumType.STRING)
	private SupportTypeEnum supportType;

	private BigDecimal value;

	@ManyToOne
	private Provider provider;

	@OneToMany(mappedBy = "providerServiceModel")
	private List<ProviderServiceModelAvailability> availabilities = new ArrayList<>();

	@Transient
	private String providerId;
}