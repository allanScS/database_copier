package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author allan scherrer
 *
 */
@Data
@Entity
@Table(name = "benefitsApplicationForm", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class BenefitsApplicationForm {

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

	@OneToOne
	private PatientCase patientCase;

	@OneToMany(mappedBy = "benefitsApplicationForm")
	private List<BenefitsApplicationFormQuestionsAndAnswers> questionsAndAnswers = new ArrayList<>();

	@Transient
	private String patientCaseId;
}
