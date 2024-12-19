package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.com.database_copier.enums.NewsStatus;
import br.com.database_copier.util.GenericUtils;
import br.com.neoapp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "news", schema = GenericUtils.SOURCE_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class News extends BaseEntity<String> {

	private static final long serialVersionUID = 3827167848863356970L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@Column(columnDefinition = "TEXT")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String descriptionInHtml;

	private String thumbnail;

	private String contentImageUrl;

	private String videoUrl;

	@ManyToMany
	@JoinTable(name = "news_segments", schema = GenericUtils.SOURCE_SCHEMA)
	private List<Company> segments;

	@Enumerated(EnumType.STRING)
	private NewsStatus status;

	private LocalDateTime postageDate;

	private LocalDateTime expirationDate;

	private Integer viewers;

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

	private Integer ordination;
}
