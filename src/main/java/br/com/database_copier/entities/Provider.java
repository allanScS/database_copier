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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.EthnicityEnum;
import br.com.database_copier.enums.GenderEnum;
import br.com.database_copier.enums.MaritalStatusEnum;
import br.com.database_copier.enums.ProviderClassification;
import br.com.database_copier.enums.ProviderStatus;
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
@Table(name = "provider", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Provider {

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

	private String accountId;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate accreditationDate;

	private String registration;

	private String name;

	private String socialName;

	private Boolean isCorporate;

	private String taxNumber;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@Enumerated(EnumType.STRING)
	private MaritalStatusEnum maritalStatus;

	@Enumerated(EnumType.STRING)
	private ProviderStatus providerStatus;

	@Enumerated(EnumType.STRING)
	private EthnicityEnum ethnicityEnum;

	@Enumerated(EnumType.STRING)
	@Column(name = "\"classification\"")
	private ProviderClassification classification;

	@ManyToOne
	private Occupation occupation;

	private String certificateNumber;

	private String corporateName;

	private String corporateTaxNumber;

	private String bank;

	private String bankCode;

	private String agency;

	private String accountNumber;

	private String accountDigit;

	private String pixKey;

	private String imageUrl;

	@OneToOne
	private Supplier supplier;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String tags;

	private Boolean active;

	@OneToMany(mappedBy = "provider")
	private List<ProviderFormation> formations = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderAddress> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderPhone> phones = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderEmail> emails = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderLanguage> languages = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderHealthPlan> plans = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderAdditionalCourse> courses = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderServiceModel> services = new ArrayList<>();

	@OneToMany(mappedBy = "provider")
	private List<ProviderAttachment> attachments = new ArrayList<>();

	@Transient
	private String occupationId;

	@Transient
	private String supplierId;

}