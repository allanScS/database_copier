package br.com.database_copier.entities;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.com.database_copier.enums.DeviceNotificationStatus;
import br.com.database_copier.enums.DeviceNotificationType;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author allan
 */
@Data
@Entity
@Table(name = "device_notification", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class DeviceNotification extends BaseEntity<String> {

	private static final long serialVersionUID = -7333248746162363823L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	private String reference;

	private String referenceName;

	private String title;

	@ManyToOne
	private Account account;

	private String body;

	@Enumerated(EnumType.STRING)
	private DeviceNotificationType type;

	private LocalDateTime sendedAt;

	private LocalDateTime viewedAt;

	@Enumerated(EnumType.STRING)
	private DeviceNotificationStatus status;

	private String sound;

	private String categoryId;

	private Integer contentAvailable;

	private String categoryIdentifier;

	private String channelId;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;

	private Long expiration;

}
