package br.com.database_copier.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.com.database_copier.enums.AccountStatus;
import br.com.database_copier.enums.Pronoun;
import br.com.database_copier.enums.Speciality;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "account", schema = GenericUtils.SOURCE_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Account extends BaseEntity<String> {

	private static final long serialVersionUID = -1274718802906899522L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	private String name;

	private String email;

	private String password;

	private String phone;

	private LocalDate birthDate;

	private String taxNumber;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	private LocalDateTime lastStatusUpdate;

	@Enumerated(EnumType.STRING)
	private Pronoun pronoun;

	@ManyToOne
	private Profile profile;

	@Enumerated(EnumType.STRING)
	private Speciality speciality;

	@OneToOne
	private Address address;

	private Boolean active;

	@OneToMany(mappedBy = "account")
	private List<Device> devices = new ArrayList<>();

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;

	private String dataDeletionCode;

	private Boolean confirmDataDeletion;

}