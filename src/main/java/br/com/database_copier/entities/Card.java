package br.com.database_copier.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import br.com.database_copier.enums.Kinship;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "card", schema = GenericUtils.SOURCE_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Card extends BaseEntity<String> {

	private static final long serialVersionUID = 3756971185296154365L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@ManyToOne
	private Account account;

	private String exhibitionName;

	private LocalDate exhibitionBirthdate;

	private String registration;

	@Enumerated(EnumType.STRING)
	private Kinship kinship;

	private String insurance;

	@ManyToOne
	private Company company;

	private String healthPlan;

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
