package br.com.database_copier.entities;

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

import br.com.database_copier.enums.BranchOfActivityEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author weslleymatosdecarvalho
 *
 */
@Data
@Entity
@Table(name = "company", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Company {

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

	private String corporateName;

	private String taxNumber;

	@Enumerated(EnumType.STRING)
	private BranchOfActivityEnum branchOfActivity;

	private String website;

	private Integer maxQuantityService;

	private String programName;

	@ManyToOne
	private Client client;

	private Boolean active;

	private Boolean shouldUpdatePatients;

	private Integer productsSize;

	private Integer benefitsSize;

	private Integer patientsSize;

	@OneToMany(mappedBy = "company")
	private List<CompanyAddress> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyPhone> phones = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyEmail> emails = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyManager> managers = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyProduct> products = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyElegiblePatient> elegiblePatients = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyAdditionalBenefit> benefits = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyHealthPlan> plans = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CompanyAttachment> attachments = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<Branch> branches = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<Subarea> subareas = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<Level> levels = new ArrayList<>();

	@OneToMany(mappedBy = "company")
	private List<CostCenter> costCenters = new ArrayList<>();

	@Transient
	private String clientId;
}