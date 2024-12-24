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

/**
 * @author allan
 */
@Data
@Entity
@Table(name = "refreshToken", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class RefreshToken extends BaseEntity<String> {

	private static final long serialVersionUID = 7080662298149036308L;

	private String id;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String refreshToken;

	private Boolean expired;
	
	@OneToOne
	private Account account;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column(updatable = false)
	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean deleted;

	private LocalDateTime deletedAt;

	private String deletedBy;
	
	@Transient
	private String accountId;
}
