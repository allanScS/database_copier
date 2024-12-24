package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.CallStatus;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "call", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Call extends BaseEntity<String> {

	private static final long serialVersionUID = 7375095246961189795L;

	private String id;

	@ManyToOne
	private Channel channel;

	@ManyToOne
	private Account patient;

	@ManyToOne
	private Account receiver;

	@ManyToOne
	private Account directReceiver;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String review;

	private Double rating;

	@ManyToMany
	@JoinTable(name = "callParticipants", schema = GenericUtils.TARGET_SCHEMA)
	private List<Account> participants = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private CallStatus callStatus;

	private LocalDateTime startedAt;

	private LocalDateTime endedAt;

	private Boolean active;

	private Boolean callToPatient;

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

	@Transient
	private String channelId;
	
	@Transient
	private String patientId;
	
	@Transient
	private String receiverId;
	
	@Transient
	private String directReceiverId;
	
}