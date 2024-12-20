package br.com.database_copier.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import br.com.database_copier.entities.Account;
import br.com.database_copier.entities.BankData;
import br.com.database_copier.entities.BenefitsApplicationForm;
import br.com.database_copier.entities.Branch;
import br.com.database_copier.entities.Category;
import br.com.database_copier.entities.Client;
import br.com.database_copier.entities.Company;
import br.com.database_copier.entities.CompanyHealthPlan;
import br.com.database_copier.entities.CompanyManager;
import br.com.database_copier.entities.CostCenter;
import br.com.database_copier.entities.CustomerSurvey;
import br.com.database_copier.entities.Event;
import br.com.database_copier.entities.Level;
import br.com.database_copier.entities.Occupation;
import br.com.database_copier.entities.Patient;
import br.com.database_copier.entities.PatientCase;
import br.com.database_copier.entities.PatientCaseForwarding;
import br.com.database_copier.entities.PatientCaseForwardingAttendance;
import br.com.database_copier.entities.PatientCompany;
import br.com.database_copier.entities.PatientImportNaturaData;
import br.com.database_copier.entities.Provider;
import br.com.database_copier.entities.ProviderPayment;
import br.com.database_copier.entities.ProviderServiceModel;
import br.com.database_copier.entities.Subarea;
import br.com.database_copier.entities.Supplier;
import br.com.database_copier.entities.TroubleArea;
import br.com.database_copier.entities.TroubleSubtype;
import br.com.database_copier.entities.TroubleType;
import br.com.neoapp.base.AbstractConverter;

public class GenericUtils {

	public static final String SOURCE_SCHEMA = "carelink_eap";

	public static final String TARGET_SCHEMA = "dbo";

	public static String buildSql(final String[] fields, final String table, final String schema,
			final Integer itensPerPage, final Integer page) {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT");

		int i = 0;
		for (String field : fields) {

			if (i == 0)
				sb.append(" entity.");
			else
				sb.append(", entity.");

			sb.append(field);

			i++;
		}

		sb.append(" FROM ");
		sb.append(schema);
		sb.append(".");
		sb.append(table);
		sb.append(" AS entity");
		sb.append(" LIMIT ");
		sb.append(itensPerPage);
		sb.append(" OFFSET ");
		sb.append(itensPerPage * page);
		sb.append(";");

		return sb.toString();

	}

	public static JSONObject objectToJson(final Object[] obj, final String[] fields) {

		final JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < obj.length; i++) {
			jsonObject.put(convertToCamelCase(fields[i]), obj[i]);
		}

		return jsonObject;
	}

	private static String convertToCamelCase(String fieldName) {
		final String[] parts = fieldName.split("_");
		final StringBuilder camelCaseName = new StringBuilder(parts[0].toLowerCase());

		for (int i = 1; i < parts.length; i++) {
			camelCaseName.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
		}

		return camelCaseName.toString();
	}

	public static LocalDateTime adjustLocalDateTime(final LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		else
			return localDateTime;
//			return localDateTime.minusHours(3);
	}

	public static LocalTime adjustLocalTime(final LocalTime localTime) {
		if (localTime == null)
			return null;
		else
			return localTime;
//			return localTime.minusHours(3);
	}

	public static <T> void executePage(String[] fields, String sourceTable, String targetTable, Integer itensPerPage,
			Integer page, Integer totalPages, Session source, Class<T> entityType) {

		Boolean success = false;
		Session target = null;
		Transaction targetTransaction = null;
		ScrollableResults results = null;
		T entity = null;

		String query = GenericUtils.buildSql(fields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage, page);

		while (!success) {
			try {
				System.out.printf("BUSCANDO %s PAGINA: %d/%d%n", entityType.getSimpleName(), page + 1, totalPages);

				target = HibernateUtil.startSessionFactoryTargetDatabase().openSession();
				targetTransaction = target.beginTransaction();

				results = source.createNativeQuery(query).setTimeout(600000).setFetchSize(itensPerPage)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					entity = new AbstractConverter<T>().convertJsonToEntity(entityType,
							GenericUtils.objectToJson(results.get(), fields));

					setDependencies(entity, entityType);

					target.saveOrUpdate(entity);
				}

				targetTransaction.commit();
				target.clear();
				target.close();
				success = true;

			} catch (Exception e) {
				System.err.println("Erro ao processar a página: " + (page + 1));

				try {
					Thread.sleep(2000);
				} catch (InterruptedException interruptedException) {
					Thread.currentThread().interrupt();
				}
			}
		}

		results = null;
		query = null;
		targetTransaction = null;
		target = null;
		success = true;
		fields = null;
		sourceTable = null;
		targetTable = null;
		itensPerPage = null;
		page = null;
		totalPages = null;
		source = null;
		entityType = null;
		success = null;

		System.gc();
	}

	public static <T> void setDependencies(T entity, final Class<T> entityType) {
		try {

			switch (entityType.getSimpleName()) {
			case "Patient": {

				final Field importNaturaDataIdField = entityType.getDeclaredField("importNaturaDataId");
				importNaturaDataIdField.setAccessible(true);
				final String importNaturaDataId = (String) importNaturaDataIdField.get(entity);

				final Field bankDataIdField = entityType.getDeclaredField("bankDataId");
				bankDataIdField.setAccessible(true);
				final String bankDataId = (String) bankDataIdField.get(entity);

				if (importNaturaDataId != null) {
					final Field field = entity.getClass().getDeclaredField("importNaturaData");
					field.setAccessible(true);

					final PatientImportNaturaData importNaturaData = new PatientImportNaturaData();
					importNaturaData.setId(importNaturaDataId);
					field.set(entity, importNaturaData);
				}

				if (bankDataId != null) {
					final Field field = entity.getClass().getDeclaredField("bankData");
					field.setAccessible(true);

					final BankData bankData = new BankData();
					bankData.setId(bankDataId);
					field.set(entity, bankData);
				}
			}
				break;

			case "PatientCase": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				final Field troubleAreaIdField = entityType.getDeclaredField("troubleAreaId");
				troubleAreaIdField.setAccessible(true);
				final String troubleAreaId = (String) troubleAreaIdField.get(entity);

				final Field troubleTypeIdField = entityType.getDeclaredField("troubleTypeId");
				troubleTypeIdField.setAccessible(true);
				final String troubleTypeId = (String) troubleTypeIdField.get(entity);

				final Field troubleSubtypeIdField = entityType.getDeclaredField("troubleSubtypeId");
				troubleSubtypeIdField.setAccessible(true);
				final String troubleSubtypeId = (String) troubleSubtypeIdField.get(entity);

				final Field responsibleForTheCaseIdField = entityType.getDeclaredField("responsibleForTheCaseId");
				responsibleForTheCaseIdField.setAccessible(true);
				final String responsibleForTheCaseId = (String) responsibleForTheCaseIdField.get(entity);

				final Field patientCompanyIdField = entityType.getDeclaredField("patientCompanyId");
				patientCompanyIdField.setAccessible(true);
				final String patientCompanyId = (String) patientCompanyIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

				if (troubleAreaId != null) {
					final Field field = entity.getClass().getDeclaredField("troubleArea");
					field.setAccessible(true);

					final TroubleArea troubleArea = new TroubleArea();
					troubleArea.setId(troubleAreaId);
					field.set(entity, troubleArea);
				}

				if (troubleTypeId != null) {
					final Field field = entity.getClass().getDeclaredField("troubleType");
					field.setAccessible(true);

					final TroubleType troubleType = new TroubleType();
					troubleType.setId(troubleTypeId);
					field.set(entity, troubleType);
				}

				if (troubleSubtypeId != null) {
					final Field field = entity.getClass().getDeclaredField("troubleSubtype");
					field.setAccessible(true);

					final TroubleSubtype troubleSubtype = new TroubleSubtype();
					troubleSubtype.setId(troubleSubtypeId);
					field.set(entity, troubleSubtype);
				}

				if (responsibleForTheCaseId != null) {
					final Field field = entity.getClass().getDeclaredField("responsibleForTheCase");
					field.setAccessible(true);

					final Account responsibleForTheCase = new Account();
					responsibleForTheCase.setId(responsibleForTheCaseId);
					field.set(entity, responsibleForTheCase);
				}

				if (patientCompanyId != null) {
					final Field field = entity.getClass().getDeclaredField("patientCompany");
					field.setAccessible(true);

					final PatientCompany patientCompany = new PatientCompany();
					patientCompany.setId(patientCompanyId);
					field.set(entity, patientCompany);
				}
			}
				break;

			case "PatientCompany": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				final Field companyHealthPlanIdField = entityType.getDeclaredField("companyHealthPlanId");
				companyHealthPlanIdField.setAccessible(true);
				final String companyHealthPlanId = (String) companyHealthPlanIdField.get(entity);

				final Field branchIdField = entityType.getDeclaredField("branchId");
				branchIdField.setAccessible(true);
				final String branchId = (String) branchIdField.get(entity);

				final Field subareaIdField = entityType.getDeclaredField("subareaId");
				subareaIdField.setAccessible(true);
				final String subareaId = (String) subareaIdField.get(entity);

				final Field levelIdField = entityType.getDeclaredField("levelId");
				levelIdField.setAccessible(true);
				final String levelId = (String) levelIdField.get(entity);

				final Field costCenterIdField = entityType.getDeclaredField("costCenterId");
				costCenterIdField.setAccessible(true);
				final String costCenterId = (String) costCenterIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

				if (companyId != null) {
					final Field field = entity.getClass().getDeclaredField("company");
					field.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					field.set(entity, company);
				}

				if (companyHealthPlanId != null) {
					final Field field = entity.getClass().getDeclaredField("companyHealthPlan");
					field.setAccessible(true);

					final CompanyHealthPlan companyHealthPlan = new CompanyHealthPlan();
					companyHealthPlan.setId(companyHealthPlanId);
					field.set(entity, companyHealthPlan);
				}

				if (branchId != null) {
					final Field field = entity.getClass().getDeclaredField("branch");
					field.setAccessible(true);

					final Branch branch = new Branch();
					branch.setId(branchId);
					field.set(entity, branch);
				}

				if (subareaId != null) {
					final Field field = entity.getClass().getDeclaredField("subarea");
					field.setAccessible(true);

					final Subarea subarea = new Subarea();
					subarea.setId(subareaId);
					field.set(entity, subarea);
				}

				if (levelId != null) {
					final Field field = entity.getClass().getDeclaredField("level");
					field.setAccessible(true);

					final Level level = new Level();
					level.setId(levelId);
					field.set(entity, level);
				}

				if (costCenterId != null) {
					final Field field = entity.getClass().getDeclaredField("costCenter");
					field.setAccessible(true);

					final CostCenter costCenter = new CostCenter();
					costCenter.setId(costCenterId);
					field.set(entity, costCenter);
				}
			}
				break;

			case "PatientCaseEvolution": {

				final Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				final String patientCaseId = (String) patientCaseIdField.get(entity);

				final Field patientCaseForwardingAttendanceIdField = entityType
						.getDeclaredField("patientCaseForwardingAttendanceId");
				patientCaseForwardingAttendanceIdField.setAccessible(true);
				final String patientCaseForwardingAttendanceId = (String) patientCaseForwardingAttendanceIdField
						.get(entity);

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (patientCaseId != null) {
					final Field field = entity.getClass().getDeclaredField("patientCase");
					field.setAccessible(true);

					final PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					field.set(entity, patientCase);
				}

				if (patientCaseForwardingAttendanceId != null) {
					final Field field = entity.getClass().getDeclaredField("patientCaseForwardingAttendance");
					field.setAccessible(true);

					final PatientCaseForwardingAttendance patientCaseForwardingAttendance = new PatientCaseForwardingAttendance();
					patientCaseForwardingAttendance.setId(patientCaseForwardingAttendanceId);
					field.set(entity, patientCaseForwardingAttendance);
				}

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "PatientCaseForwarding": {

				final Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				final String patientCaseId = (String) patientCaseIdField.get(entity);

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (patientCaseId != null) {
					final Field field = entity.getClass().getDeclaredField("patientCase");
					field.setAccessible(true);

					final PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					field.set(entity, patientCase);
				}

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderAdditionalCourse": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderAddress": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderAttachment": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderEmail": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderFormation": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderLanguage": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderPhone": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderServiceModel": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "ProviderServiceModelAvailability": {

				final Field providerServiceModelIdField = entityType.getDeclaredField("providerServiceModelId");
				providerServiceModelIdField.setAccessible(true);
				final String providerServiceModelId = (String) providerServiceModelIdField.get(entity);

				if (providerServiceModelId != null) {
					final Field field = entity.getClass().getDeclaredField("providerServiceModel");
					field.setAccessible(true);

					final ProviderServiceModel providerServiceModel = new ProviderServiceModel();
					providerServiceModel.setId(providerServiceModelId);
					field.set(entity, providerServiceModel);
				}

			}
				break;

			case "QuestionsAndAnswers": {

				final Field customerSurveyIdField = entityType.getDeclaredField("customerSurveyId");
				customerSurveyIdField.setAccessible(true);
				final String customerSurveyId = (String) customerSurveyIdField.get(entity);

				if (customerSurveyId != null) {
					final Field field = entity.getClass().getDeclaredField("customerSurvey");
					field.setAccessible(true);

					final CustomerSurvey customerSurvey = new CustomerSurvey();
					customerSurvey.setId(customerSurveyId);
					field.set(entity, customerSurvey);
				}

			}
				break;

			case "ProviderPaymentItem": {

				final Field providerPaymentIdField = entityType.getDeclaredField("providerPaymentId");
				providerPaymentIdField.setAccessible(true);
				final String providerPaymentId = (String) providerPaymentIdField.get(entity);

				final Field patientCaseForwardingAttendanceIdField = entityType
						.getDeclaredField("patientCaseForwardingAttendanceId");
				patientCaseForwardingAttendanceIdField.setAccessible(true);
				final String patientCaseForwardingAttendanceId = (String) patientCaseForwardingAttendanceIdField
						.get(entity);

				if (providerPaymentId != null) {
					final Field field = entity.getClass().getDeclaredField("providerPayment");
					field.setAccessible(true);

					final ProviderPayment providerPayment = new ProviderPayment();
					providerPayment.setId(providerPaymentId);
					field.set(entity, providerPayment);
				}

				if (patientCaseForwardingAttendanceId != null) {
					final Field field = entity.getClass().getDeclaredField("patientCaseForwardingAttendance");
					field.setAccessible(true);

					final PatientCaseForwardingAttendance patientCaseForwardingAttendance = new PatientCaseForwardingAttendance();
					patientCaseForwardingAttendance.setId(patientCaseForwardingAttendanceId);
					field.set(entity, patientCaseForwardingAttendance);
				}

			}
				break;

			case "ProviderHealthPlan": {

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);
				}

			}
				break;

			case "PatientCaseForwardingAttendance": {

				final Field patientCaseForwardingIdField = entityType.getDeclaredField("patientCaseForwardingId");
				patientCaseForwardingIdField.setAccessible(true);
				final String patientCaseForwardingId = (String) patientCaseForwardingIdField.get(entity);

				final Field providerPaymentClosureIdField = entityType.getDeclaredField("providerPaymentClosureId");
				providerPaymentClosureIdField.setAccessible(true);
				final String providerPaymentClosureId = (String) providerPaymentClosureIdField.get(entity);

				if (patientCaseForwardingId != null) {
					final Field field = entity.getClass().getDeclaredField("patientCaseForwarding");
					field.setAccessible(true);

					final PatientCaseForwarding patientCaseForwarding = new PatientCaseForwarding();
					patientCaseForwarding.setId(patientCaseForwardingId);
					field.set(entity, patientCaseForwarding);
				}

				if (providerPaymentClosureId != null) {
					final Field field = entity.getClass().getDeclaredField("providerPaymentClosure");
					field.setAccessible(true);

					final ProviderPayment providerPaymentClosure = new ProviderPayment();
					providerPaymentClosure.setId(providerPaymentClosureId);
					field.set(entity, providerPaymentClosure);
				}

			}
				break;

			case "PatientAddress": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

			}
				break;

			case "PatientEmail": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

			}
				break;

			case "PatientPhone": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

			}
				break;

			case "PatientFamilyGroup": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				final Field relativeIdField = entityType.getDeclaredField("relativeId");
				relativeIdField.setAccessible(true);
				final String relativeId = (String) relativeIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

				if (relativeId != null) {
					final Field field = entity.getClass().getDeclaredField("relative");
					field.setAccessible(true);

					final Patient relative = new Patient();
					relative.setId(relativeId);
					field.set(entity, relative);
				}

			}
				break;

			case "PatientAttachment": {

				final Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				final String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					final Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					final Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);
				}

			}
				break;

			case "AccountCode": {

				final Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				final String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					final Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					final Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);
				}
			}
				break;

			case "SpreadsheetImport": {

				final Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				final String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					final Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					final Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);
				}
			}
				break;

			case "Provider": {

				final Field occupationIdField = entityType.getDeclaredField("occupationId");
				occupationIdField.setAccessible(true);
				final String occupationId = (String) occupationIdField.get(entity);

				final Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				final String supplierId = (String) supplierIdField.get(entity);

				if (occupationId != null) {
					final Field field = entity.getClass().getDeclaredField("occupation");
					field.setAccessible(true);

					final Occupation occupation = new Occupation();
					occupation.setId(occupationId);
					field.set(entity, occupation);
				}

				if (supplierId != null) {
					final Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					final Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);
				}
			}
				break;

			case "BenefitsApplicationForm": {

				final Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				final String patientCaseId = (String) patientCaseIdField.get(entity);

				if (patientCaseId != null) {
					final Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					final PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);
				}
			}
				break;

			case "PatientCaseAttachment": {

				final Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				final String patientCaseId = (String) patientCaseIdField.get(entity);

				if (patientCaseId != null) {
					final Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					final PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);
				}
			}
				break;

			case "CustomerSurvey": {

				final Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				final String patientCaseId = (String) patientCaseIdField.get(entity);

				if (patientCaseId != null) {
					final Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					final PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);
				}
			}
				break;

			case "SessionReview": {

				final Field patientCaseForwardingAttendanceIdField = entityType
						.getDeclaredField("patientCaseForwardingAttendanceId");
				patientCaseForwardingAttendanceIdField.setAccessible(true);
				final String patientCaseForwardingAttendanceId = (String) patientCaseForwardingAttendanceIdField
						.get(entity);

				if (patientCaseForwardingAttendanceId != null) {
					final Field Field = entity.getClass().getDeclaredField("patientCaseForwardingAttendance");
					Field.setAccessible(true);

					final PatientCaseForwardingAttendance patientCaseForwardingAttendance = new PatientCaseForwardingAttendance();
					patientCaseForwardingAttendance.setId(patientCaseForwardingAttendanceId);
					Field.set(entity, patientCaseForwardingAttendance);
				}
			}
				break;

			case "Schedule": {

				final Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				final String patientCaseId = (String) patientCaseIdField.get(entity);

				final Field accountIdField = entityType.getDeclaredField("accountId");
				accountIdField.setAccessible(true);
				final String accountId = (String) accountIdField.get(entity);

				if (patientCaseId != null) {
					final Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					final PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);
				}

				if (accountId != null) {
					final Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					final Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);
				}
			}
				break;

			case "BenefitsApplicationFormQuestionsAndAnswers": {

				final Field benefitsApplicationFormIdField = entityType.getDeclaredField("benefitsApplicationFormId");
				benefitsApplicationFormIdField.setAccessible(true);
				final String benefitsApplicationFormId = (String) benefitsApplicationFormIdField.get(entity);

				if (benefitsApplicationFormId != null) {
					final Field benefitsApplicationFormField = entity.getClass()
							.getDeclaredField("benefitsApplicationForm");
					benefitsApplicationFormField.setAccessible(true);

					final BenefitsApplicationForm benefitsApplicationForm = new BenefitsApplicationForm();
					benefitsApplicationForm.setId(benefitsApplicationFormId);
					benefitsApplicationFormField.set(entity, benefitsApplicationForm);
				}
			}
				break;

			case "Branch": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "Subarea": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "SupplierAddress": {

				final Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				final String supplierId = (String) supplierIdField.get(entity);

				if (supplierId != null) {
					final Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					final Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);
				}
			}
				break;

			case "SupplierEmail": {

				final Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				final String supplierId = (String) supplierIdField.get(entity);

				if (supplierId != null) {
					final Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					final Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);
				}
			}
				break;

			case "SupplierPhone": {

				final Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				final String supplierId = (String) supplierIdField.get(entity);

				if (supplierId != null) {
					final Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					final Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);
				}
			}
				break;

			case "ClientAddress": {

				final Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				final String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					final Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					final Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);
				}
			}
				break;

			case "ClientEmail": {

				final Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				final String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					final Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					final Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);
				}
			}
				break;

			case "ClientPhone": {

				final Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				final String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					final Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					final Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);
				}
			}
				break;

			case "Company": {

				final Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				final String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					final Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					final Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);
				}
			}
				break;

			case "Event": {

				final Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				final String clientId = (String) clientIdField.get(entity);

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				final Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				final String providerId = (String) providerIdField.get(entity);

				if (clientId != null) {
					final Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					final Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);
				}

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}

				if (providerId != null) {
					final Field providerField = entity.getClass().getDeclaredField("provider");
					providerField.setAccessible(true);

					final Provider provider = new Provider();
					provider.setId(providerId);
					providerField.set(entity, provider);
				}
			}
				break;

			case "CompanyAdditionalBenefit": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyAddress": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyAttachment": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyElegiblePatient": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyEmail": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyPhone": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyProduct": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CostCenter": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "companyHealthPlan": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyManager": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "Level": {

				final Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				final String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					final Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					final Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);
				}
			}
				break;

			case "CompanyManagerEmail": {

				final Field companyManagerIdField = entityType.getDeclaredField("companyManagerId");
				companyManagerIdField.setAccessible(true);
				final String companyManagerId = (String) companyManagerIdField.get(entity);

				if (companyManagerId != null) {
					final Field companyManagerField = entity.getClass().getDeclaredField("companyManager");
					companyManagerField.setAccessible(true);

					final CompanyManager companyManager = new CompanyManager();
					companyManager.setId(companyManagerId);
					companyManagerField.set(entity, companyManager);
				}
			}
				break;

			case "CompanyManagerPhone": {

				final Field companyManagerIdField = entityType.getDeclaredField("companyManagerId");
				companyManagerIdField.setAccessible(true);
				final String companyManagerId = (String) companyManagerIdField.get(entity);

				if (companyManagerId != null) {
					final Field companyManagerField = entity.getClass().getDeclaredField("companyManager");
					companyManagerField.setAccessible(true);

					final CompanyManager companyManager = new CompanyManager();
					companyManager.setId(companyManagerId);
					companyManagerField.set(entity, companyManager);
				}
			}
				break;

			case "TroubleType": {

				final Field troubleAreaIdField = entityType.getDeclaredField("troubleAreaId");
				troubleAreaIdField.setAccessible(true);
				final String troubleAreaId = (String) troubleAreaIdField.get(entity);

				if (troubleAreaId != null) {
					final Field field = entity.getClass().getDeclaredField("troubleArea");
					field.setAccessible(true);

					final TroubleArea troubleArea = new TroubleArea();
					troubleArea.setId(troubleAreaId);
					field.set(entity, troubleArea);
				}
			}
				break;

			case "TroubleSubtype": {

				final Field troubleTypeIdField = entityType.getDeclaredField("troubleTypeId");
				troubleTypeIdField.setAccessible(true);
				final String troubleTypeId = (String) troubleTypeIdField.get(entity);

				if (troubleTypeId != null) {
					final Field field = entity.getClass().getDeclaredField("troubleType");
					field.setAccessible(true);

					final TroubleType troubleType = new TroubleType();
					troubleType.setId(troubleTypeId);
					field.set(entity, troubleType);
				}
			}
				break;

			case "Expense": {

				final Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				final String supplierId = (String) supplierIdField.get(entity);

				final Field categoryIdField = entityType.getDeclaredField("categoryId");
				categoryIdField.setAccessible(true);
				final String categoryId = (String) categoryIdField.get(entity);

				final Field providerPaymentIdField = entityType.getDeclaredField("providerPaymentId");
				providerPaymentIdField.setAccessible(true);
				final String providerId = (String) providerPaymentIdField.get(entity);

				final Field eventIdField = entityType.getDeclaredField("eventId");
				eventIdField.setAccessible(true);
				final String eventId = (String) eventIdField.get(entity);

				if (supplierId != null) {
					final Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					final Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);
				}

				if (categoryId != null) {
					final Field field = entity.getClass().getDeclaredField("category");
					field.setAccessible(true);

					final Category category = new Category();
					category.setId(categoryId);
					field.set(entity, category);
				}

				if (providerId != null) {
					final Field field = entity.getClass().getDeclaredField("providerPayment");
					field.setAccessible(true);

					final ProviderPayment providerPayment = new ProviderPayment();
					providerPayment.setId(providerId);
					field.set(entity, providerPayment);
				}

				if (eventId != null) {
					final Field field = entity.getClass().getDeclaredField("event");
					field.setAccessible(true);

					final Event event = new Event();
					event.setId(eventId);
					field.set(entity, event);
				}
			}
				break;

			case "Revenue": {

				final Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				final String clientId = (String) clientIdField.get(entity);

				final Field categoryIdField = entityType.getDeclaredField("categoryId");
				categoryIdField.setAccessible(true);
				final String categoryId = (String) categoryIdField.get(entity);

				if (clientId != null) {
					final Field field = entity.getClass().getDeclaredField("client");
					field.setAccessible(true);

					final Client client = new Client();
					client.setId(clientId);
					field.set(entity, client);
				}

				if (categoryId != null) {
					final Field field = entity.getClass().getDeclaredField("category");
					field.setAccessible(true);

					final Category category = new Category();
					category.setId(categoryId);
					field.set(entity, category);
				}

			}
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
