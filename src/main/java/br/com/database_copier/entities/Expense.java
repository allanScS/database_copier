package br.com.database_copier.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "expense", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Expense {

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

	private String description;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String observation;

	@Column(scale = 2)
	private BigDecimal value;

	@Column(scale = 2)
	private BigDecimal additions;

	@Column(scale = 2)
	private BigDecimal discounts;

	@Column(scale = 2)
	private BigDecimal finalValue;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime dueDate;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime billDate;

	private Boolean effectuated;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime effectuatedAt;

	private String billNumber;

	private Boolean installment;

	private Integer installmentsNumber;

	@Column(scale = 2)
	private BigDecimal installmentValue;

	@ManyToOne
	private Supplier supplier;

	@ManyToOne
	private Category category;

	@ManyToOne
	private ProviderPayment providerPayment;

	@ManyToOne
	private Event event;

	@Transient
	private String supplierId;

	@Transient
	private String categoryId;

	@Transient
	private String providerPaymentId;

	@Transient
	private String eventId;
}