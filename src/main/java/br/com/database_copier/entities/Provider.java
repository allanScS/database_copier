package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "provider", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Provider extends BaseEntity<String> {

	private static final long serialVersionUID = 521418863705706930L;

	private String id;

	private String name;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String imageUrl;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String tags;

	@ManyToMany
	@JoinTable(name = "providerInsurance", schema = GenericUtils.TARGET_SCHEMA)
	private List<Insurance> insurances;

	private String phone;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String about;

	@OneToOne
	private Address address;

	private Double rating;

	private Integer reviewsNumber;

	@Transient
	private Boolean active;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	@Transient
	private LocalDateTime updatedAt;

	@Transient
	private String updatedBy;

	@Transient
	private Boolean deleted;

	@Transient
	private LocalDateTime deletedAt;

	@Transient
	private String deletedBy;
	
	@Transient
	private String addressId;
}
