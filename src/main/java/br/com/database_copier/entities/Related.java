package br.com.database_copier.entities;

import java.time.LocalDateTime;

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
@Table(name = "related", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Related extends BaseEntity<String> {

	private static final long serialVersionUID = 5471820523088322643L;

	private String id;

	private String description;

	private String imageUrl;

	@ManyToOne
	private Notification notification;

	@Transient
	private Boolean active;

	@Transient
	private LocalDateTime createdAt;

	@Transient
	private String createdBy;

	@Transient
	private LocalDateTime updatedAt;

	@Transient
	private String updatedBy;

	@Transient
	private Boolean deleted;

	@Transient
	private LocalDateTime deletedAt;

	@Transient
	private String deletedBy;

	@Transient
	private String notificationId;

	public Related(String description, String imageUrl) {
		this.description = description;
		this.imageUrl = imageUrl;
	}
}
