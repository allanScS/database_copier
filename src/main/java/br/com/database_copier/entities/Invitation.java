package br.com.database_copier.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "invitation", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Invitation extends BaseEntity<String> {

	private static final long serialVersionUID = -1274718802906899522L;

	private String id;

	@OneToOne
	private Call call;

	@OneToOne
	private Account guest;

	@OneToOne
	private Account inviteSender;

	private Boolean accepted;

	private LocalDateTime reactedAt;

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

	@Transient
	private String callId;

	@Transient
	private String guestId;

	@Transient
	private String inviteSenderId;

}
