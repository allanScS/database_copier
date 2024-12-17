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

import br.com.database_copier.enums.RelationshipEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateConverter;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "patientCompany", schema = GenericUtils.TARGET_SCHEMA)
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientCompany {

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
	private Patient patient;

	@ManyToOne
	private Company company;

	private Boolean holder;

	@Enumerated(EnumType.STRING)
	private RelationshipEnum relationship;

	private String registrationNumber;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate dateOfAdmission;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate lastWorkLeaveDate;

	@ManyToOne
	private CompanyHealthPlan companyHealthPlan;

	@ManyToOne
	private Branch branch;

	@ManyToOne
	private Subarea subarea;

	@ManyToOne
	private Level level;

	@ManyToOne
	private CostCenter costCenter;

	private Boolean active;

	@Transient
	private String companyId;

	@Transient
	private String patientId;

	@Transient
	private String companyHealthPlanId;

	@Transient
	private String branchId;

	@Transient
	private String subareaId;

	@Transient
	private String levelId;

	@Transient
	private String costCenterId;

}