package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.WeekDayEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import br.com.database_copier.util.LocalTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author allan scherrer
 *
 */
@Data
@Entity
@Table(name = "providerServiceModelAvailability", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class ProviderServiceModelAvailability {

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
	private WeekDayEnum weekday;

	@Convert(converter = LocalTimeConverter.class)
	@Column(columnDefinition = ("TIME"))
	private LocalTime startTime;

	@Convert(converter = LocalTimeConverter.class)
	@Column(columnDefinition = ("TIME"))
	private LocalTime endTime;

	@ManyToOne
	private ProviderServiceModel providerServiceModel;

	@Transient
	private String providerServiceModelId;

}
