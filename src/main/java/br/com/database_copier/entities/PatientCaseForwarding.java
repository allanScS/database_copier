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

import br.com.database_copier.enums.ForwardingStatusEnum;
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
@Table(name = "patientCaseForwarding", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientCaseForwarding {

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
	private Provider provider;

	@ManyToOne
	private PatientCase patientCase;

	@Enumerated(EnumType.STRING)
	private ForwardingStatusEnum forwardingStatus;

	@Enumerated(EnumType.STRING)
	private ServiceModelTypeEnum serviceModelType;

	@Enumerated(EnumType.STRING)
	private SupportTypeEnum supportType;

	private BigDecimal value;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime reactedAt;

	@OneToMany(mappedBy = "patientCaseForwarding")
	private List<PatientCaseForwardingAttendance> attendances = new ArrayList<>();

	@Transient
	private Boolean sendEmailToProvider;

	@Transient
	private String providerId;

	@Transient
	private String patientCaseId;
}