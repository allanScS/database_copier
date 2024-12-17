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

import br.com.database_copier.enums.EvolutionTypeEnum;
import br.com.database_copier.enums.ServiceChannelEnum;
import br.com.database_copier.enums.ServiceModelTypeEnum;
import br.com.database_copier.enums.SupportTypeEnum;
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
@Table(name = "patientCaseEvolution", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientCaseEvolution {

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

	@ManyToOne
	private PatientCase patientCase;

	@ManyToOne
	private PatientCaseForwardingAttendance patientCaseForwardingAttendance;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate evolutionDate;

	private Double attendanceHours;

	private Boolean noShow;

	@Enumerated(EnumType.STRING)
	private EvolutionTypeEnum evolutionType;

	@Enumerated(EnumType.STRING)
	private ServiceChannelEnum serviceChannelType;

	@Enumerated(EnumType.STRING)
	private ServiceModelTypeEnum serviceModelType;

	@Enumerated(EnumType.STRING)
	private SupportTypeEnum supportType;

	private Boolean receptive;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String description;

	@ManyToOne
	private Provider provider;

	private String createdByAccountName;

	@Transient
	private String patientCaseId;

	@Transient
	private String patientCaseForwardingAttendanceId;

	@Transient
	private String providerId;
}