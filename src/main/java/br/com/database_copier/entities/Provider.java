package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "provider", schema = GenericUtils.SOURCE_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Provider extends BaseEntity<String> {

	private static final long serialVersionUID = 521418863705706930L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String imageUrl;

	private String tags;

	@ManyToMany
	@JoinTable(name = "provider_insurance", schema = GenericUtils.SOURCE_SCHEMA)
	private List<Insurance> insurances;

	private String phone;

	@Column(columnDefinition = "TEXT")
	private String about;

	@OneToOne(optional = false)
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
}
