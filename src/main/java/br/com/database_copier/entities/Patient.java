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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.FormationStatusEnum;
import br.com.database_copier.enums.GenderEnum;
import br.com.database_copier.enums.MaritalStatusEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateConverter;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "patient", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Patient {

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

	private String imageUrl;

	private String name;

	private String socialName;

	private String taxNumber;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate birthDate;

	private Boolean identifiedByTaxNumber;

	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@Enumerated(EnumType.STRING)
	private MaritalStatusEnum maritalStatus;

	private String occupation;

	@Enumerated(EnumType.STRING)
	private FormationStatusEnum formationStatus;

	private Boolean active;

	@OneToMany(mappedBy = "patient")
	private List<PatientFamilyGroup> relatives = new ArrayList<>();

	@OneToMany(mappedBy = "patient")
	private List<PatientAddress> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "patient")
	private List<PatientPhone> phones = new ArrayList<>();

	@OneToMany(mappedBy = "patient")
	private List<PatientEmail> emails = new ArrayList<>();

	@OneToMany(mappedBy = "patient")
	private List<PatientCompany> companies = new ArrayList<>();

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String companieNames;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String clientNames;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String registrationNumbers;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String relationships;

	private Long dependentsQuantity;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String branchNames;

	@OneToOne
	private PatientImportNaturaData importNaturaData;

	@OneToOne
	private BankData bankData;

	@OneToMany(mappedBy = "patient")
	private List<PatientAttachment> attachments = new ArrayList<>();

	private Integer casesSize;

	private Integer attendancesSize;

	public void incrementCasesSize() {
		this.casesSize = this.casesSize + 1;
	}

	public void decrementCasesSize() {
		this.casesSize = this.casesSize - 1;
	}

	public void incrementAttendancesSize() {
		this.attendancesSize = this.attendancesSize + 1;
	}

	public void decrementAttendancesSize() {
		this.attendancesSize = this.attendancesSize - 1;
	}

	@Transient
	private String importNaturaDataId;

	@Transient
	private String bankDataId;

}