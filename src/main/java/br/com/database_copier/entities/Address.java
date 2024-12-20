package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "address", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Address extends BaseEntity<String> {

	private static final long serialVersionUID = 1366942716900437107L;

	private String id;

	private String street;

	private String number;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String complement;

	private String district;

	private String city;

	private String state;

	private String country;

	private String postalCode;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String referencePoint;

	private Double latitude;

	private Double longitude;

	private Boolean active;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	@Transient
	private Boolean deleted;

	@Transient
	private LocalDateTime deletedAt;

	@Transient
	private String deletedBy;
}
