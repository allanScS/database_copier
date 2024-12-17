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

import br.com.database_copier.enums.DepartmentEnum;
import br.com.database_copier.util.GenericUtils;
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
@Table(name = "companyManager", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class CompanyManager {

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
	private Company company;

	private String name;

	private String role;

	@Enumerated(EnumType.STRING)
	private DepartmentEnum department;

	@OneToMany(mappedBy = "companyManager")
	private List<CompanyManagerPhone> phones = new ArrayList<>();

	@OneToMany(mappedBy = "companyManager")
	private List<CompanyManagerEmail> emails = new ArrayList<>();

	@Transient
	private String companyId;

}
