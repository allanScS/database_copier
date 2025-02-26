package br.com.database_copier.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.database_copier.util.GenericUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Table(name = "providerInsurance", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProviderInsurance {

	@ManyToOne
	private Provider provider;

	@ManyToOne
	private Insurance insurance;

	@Transient
	private String providerId;

	@Transient
	private String insuranceId;
}
