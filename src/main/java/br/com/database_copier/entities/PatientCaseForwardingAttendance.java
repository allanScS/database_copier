package br.com.database_copier.entities;

import java.math.BigDecimal;
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

import br.com.database_copier.enums.ServiceModelTypeEnum;
import br.com.database_copier.enums.SupportTypeEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "patientCaseForwardingAttendance", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientCaseForwardingAttendance {

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
	private PatientCaseForwarding patientCaseForwarding;

	private Boolean noShow;

	private Boolean sentForPayment;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime effectuatedAt;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime attendanceEndedAt;

	private BigDecimal value;

	private Double attendanceHours;

	@Enumerated(EnumType.STRING)
	private ServiceModelTypeEnum serviceModelType;

	@Enumerated(EnumType.STRING)
	private SupportTypeEnum supportType;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String annotation;

	@ManyToOne
	private ProviderPayment providerPaymentClosure;

	@Transient
	private String patientCaseForwardingId;

	@Transient
	private String providerPaymentClosureId;
}
