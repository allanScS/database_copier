package br.com.database_copier.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.FamilyGroupTypeEnum;
import br.com.database_copier.util.GenericUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author weslleymatosdecarvalho
 *
 */
@Data
@Entity
@Table(name = "patientFamilyGroup", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientFamilyGroup {

	@Id
	private String id;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Patient relative;

	@Enumerated(EnumType.STRING)
	private FamilyGroupTypeEnum familyGroupType;

	@Transient
	private String patientId;

	@Transient
	private String relativeId;
}