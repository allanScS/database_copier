package br.com.database_copier.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.database_copier.enums.BodyType;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.LocalDateTimeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author weslleymatosdecarvalho
 *
 */
@Data
@Entity
@Table(name = "client", schema = GenericUtils.TARGET_SCHEMA)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class Client {

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

	private String imageUrl;

	private String name;

	private String corporateName;

	private String taxNumber;

	private Boolean active;

	private Integer companiesSize;

	private Integer patientsSize;

	@Enumerated(EnumType.STRING)
	private BodyType emailHtmlBody;

	@OneToMany(mappedBy = "client")
	private List<ClientAddress> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "client")
	private List<ClientPhone> phones = new ArrayList<>();

	@OneToMany(mappedBy = "client")
	private List<ClientEmail> emails = new ArrayList<>();

	@OneToMany(mappedBy = "client")
	private List<Company> companies = new ArrayList<>();

	private Boolean shouldUpdatePatients;

	private Boolean hasWorkLeaveInformation;
}