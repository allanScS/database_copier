package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.GenderAffected;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "healthCondition", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class HealthCondition extends BaseEntity<String> {

	private static final long serialVersionUID = -1274718802906899522L;

	private String id;

	private String name;

	private String tagCode;

	@Enumerated(EnumType.STRING)
	private GenderAffected genderAffected;

	@Transient
	private LocalDateTime createdAt;

	@Transient
	private String createdBy;

	@Transient
	private LocalDateTime updatedAt;

	@Transient
	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;

}
