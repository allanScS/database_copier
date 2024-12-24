package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "schedule", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Schedule extends BaseEntity<String> {

	private static final long serialVersionUID = -1274718802906899522L;

	private String id;

	private String title;

	private String description;

	private LocalDateTime startedAt;

	private LocalDateTime endedAt;

	@ManyToOne
	private Account professional;

	@ManyToOne
	private Account patient;

	private Boolean active;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;

	private String meetingLink;

	private String patientEmail;

	private String patientName;

	private Boolean firstReminderSent;

	private Boolean secondReminderSent;

	private Boolean professionalJoinedTheCall;

	@Transient
	private String professionalId;

	@Transient
	private String patientId;
}