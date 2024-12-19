package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "medical_appointment", schema = GenericUtils.SOURCE_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class MedicalAppointment extends BaseEntity<String> {

	private static final long serialVersionUID = -1274718802906899522L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@ManyToOne
	private Account patient;

	@ManyToOne
	private Call call;

	@Column(columnDefinition = "TEXT")
	private String review;

	@Column(columnDefinition = "TEXT")
	private String comments;

	private Boolean isHospitalization;

	private Double rating;

	private LocalDateTime date;

	private String doctorName;

	@ManyToOne
	private Provider provider;

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
