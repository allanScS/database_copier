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

/**
 * @author generated by laucher
 */
@Data
@Entity
@Table(name = "message", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Message extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String descriptionHtml;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String descriptionPlainText;

	private Boolean viwed;

	private LocalDateTime viwedAt;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String viwedByIds;

	@ManyToOne
	private Channel channel;

	@ManyToOne
	private Account sender;

	@ManyToOne
	private Account patient;

	@ManyToOne
	private MassMessage massMessage;

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
