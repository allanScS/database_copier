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

import br.com.database_copier.enums.ScheduleTypeEnum;
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
@Table(name = "schedule", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Schedule {

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

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime startDateTime;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime endDateTime;

	private String description;

	@Enumerated(EnumType.STRING)
	private ScheduleTypeEnum scheduleType;

	@ManyToOne
	private PatientCase patientCase;

	@ManyToOne
	private Account account;

	@Transient
	private String patientCaseId;

	@Transient
	private String accountId;

}
