package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import br.com.database_copier.enums.Role;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author generated by laucher
 */
@Data
@Entity
@Table(name = "profile", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Profile extends BaseEntity<String> {

	private static final long serialVersionUID = -5173693959225504245L;
	
	private String id;

	private String name;

	private String description;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	@JoinTable(name = "profilesRoles", joinColumns = 
	@JoinColumn(name = "profile_id"), schema = GenericUtils.TARGET_SCHEMA)
	@Column(name = "role")
	private List<Role> roles = new ArrayList<>();

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;

}
