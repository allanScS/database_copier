package br.com.database_copier.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.CallTypeEnum;
import br.com.database_copier.enums.NotifierEnum;
import br.com.database_copier.enums.OriginEnum;
import br.com.database_copier.enums.PriorityEnum;
import br.com.database_copier.enums.ProblemHolderEnum;
import br.com.database_copier.enums.ProductEnum;
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
@Table(name = "patientCase", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientCase {

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

	private Long caseNumber;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime openingDate;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private TroubleArea troubleArea;

	@ManyToOne
	private TroubleType troubleType;

	@ManyToOne
	private TroubleSubtype troubleSubtype;

	private Boolean concluded;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime concludedAt;

	private String concludedBy;

	@Column(name = "\"priority\"")
	@Enumerated(EnumType.STRING)
	private PriorityEnum priority;

	@Enumerated(EnumType.STRING)
	private CallTypeEnum callType;

	@Enumerated(EnumType.STRING)
	private NotifierEnum notifier;

	@Enumerated(EnumType.STRING)
	private ProblemHolderEnum problemHolder;

	@Enumerated(EnumType.STRING)
	private ProductEnum product;

	@Enumerated(EnumType.STRING)
	private OriginEnum origin;

	@ManyToOne
	private Account responsibleForTheCase;

	private String patientAvailability;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String description;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String reasonForWorkLeave;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = "DATE")
	private LocalDate workLeaveStartDate;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = "DATE")
	private LocalDate workLeaveEndDate;

	@ManyToOne
	private PatientCompany patientCompany;

	private String originalCaseNumber;

	private Integer carelinkSessions;

	private Integer providerSessions;

	@OneToMany(mappedBy = "patientCase")
	private List<PatientCaseEvolution> evolutions = new ArrayList<>();

	@OneToMany(mappedBy = "patientCase")
	private List<PatientCaseForwarding> forwardings = new ArrayList<>();

	@OneToMany(mappedBy = "patientCase")
	private List<PatientCaseAttachment> attachments = new ArrayList<>();

	private Boolean participateSatisfactionSurvey;

	@Transient
	private String patientId;

	@Transient
	private String troubleAreaId;

	@Transient
	private String troubleTypeId;

	@Transient
	private String troubleSubtypeId;

	@Transient
	private String responsibleForTheCaseId;

	@Transient
	private String patientCompanyId;

}