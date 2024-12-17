package br.com.database_copier.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.enums.EventStatusEnum;
import br.com.database_copier.enums.EventTypeEnum;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateConverter;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Matheus Alexandre
 *
 */
@Data
@Entity
@Table(name = "event", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Event {

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

	private String name;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Client client;

	@Enumerated(EnumType.STRING)
	private EventTypeEnum type;

	@Convert(converter = LocalDateConverter.class)
	@Column(columnDefinition = ("DATE"))
	private LocalDate date;

	@ManyToOne
	private Provider provider;

	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	private EventStatusEnum status;

	private Boolean sentToPay;

	@Transient
	private String companyId;

	@Transient
	private String clientId;

	@Transient
	private String providerId;
}