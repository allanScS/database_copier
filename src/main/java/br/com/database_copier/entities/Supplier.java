package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author allan scherrero
 *
 */
@Data
@Entity
@Table(name = "supplier", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Supplier {

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

	private Boolean isCorporate;

	private String corporateName;

	private String taxNumber;

	private Boolean active;

	@OneToMany(mappedBy = "supplier")
	private List<SupplierAddress> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "supplier")
	private List<SupplierPhone> phones = new ArrayList<>();

	@OneToMany(mappedBy = "supplier")
	private List<SupplierEmail> emails = new ArrayList<>();
}