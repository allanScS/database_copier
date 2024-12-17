package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author weslleymatosdecarvalho
 *
 */
@Data
@Entity
@Table(name = "patientAddress", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class PatientAddress {

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

	private String description;

	private String street;

	private String number;

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

	private Boolean favourite;

	@ManyToOne
	private Patient patient;

	@Transient
	private String patientId;

}
