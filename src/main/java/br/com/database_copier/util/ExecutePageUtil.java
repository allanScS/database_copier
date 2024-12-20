package br.com.database_copier.util;

import java.lang.reflect.Field;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

public class ExecutePageUtil {

	public <T> void executePage(String[] fields, String sourceTable, String targetTable, Integer itensPerPage,
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
					
					entity = null;
				}

				targetTransaction.commit();
				success = true;

			} catch (Exception e) {
				System.err.println("Erro ao processar a página: " + (page + 1));

				try {
					Thread.sleep(2000);
				} catch (InterruptedException interruptedException) {
					Thread.currentThread().interrupt();
				}
			} finally {
				if (target != null && target.isOpen()) {
					target.clear();
					target.close();
					source.clear();
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

	}

	public <T> void setDependencies(T entity, final Class<T> entityType) {
		try {

			switch (entityType.getSimpleName()) {
			case "Patient": {

				Field importNaturaDataIdField = entityType.getDeclaredField("importNaturaDataId");
				importNaturaDataIdField.setAccessible(true);
				String importNaturaDataId = (String) importNaturaDataIdField.get(entity);

				Field bankDataIdField = entityType.getDeclaredField("bankDataId");
				bankDataIdField.setAccessible(true);
				String bankDataId = (String) bankDataIdField.get(entity);

				if (importNaturaDataId != null) {
					Field field = entity.getClass().getDeclaredField("importNaturaData");
					field.setAccessible(true);

					PatientImportNaturaData importNaturaData = new PatientImportNaturaData();
					importNaturaData.setId(importNaturaDataId);
					field.set(entity, importNaturaData);

					field = null;
					importNaturaData = null;

				}

				if (bankDataId != null) {
					Field field = entity.getClass().getDeclaredField("bankData");
					field.setAccessible(true);

					BankData bankData = new BankData();
					bankData.setId(bankDataId);
					field.set(entity, bankData);

					field = null;
					bankData = null;
				}

				importNaturaDataIdField = null;
				importNaturaDataId = null;
				bankDataIdField = null;
				bankDataId = null;

			}
				break;

			case "PatientCase": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				Field troubleAreaIdField = entityType.getDeclaredField("troubleAreaId");
				troubleAreaIdField.setAccessible(true);
				String troubleAreaId = (String) troubleAreaIdField.get(entity);

				Field troubleTypeIdField = entityType.getDeclaredField("troubleTypeId");
				troubleTypeIdField.setAccessible(true);
				String troubleTypeId = (String) troubleTypeIdField.get(entity);

				Field troubleSubtypeIdField = entityType.getDeclaredField("troubleSubtypeId");
				troubleSubtypeIdField.setAccessible(true);
				String troubleSubtypeId = (String) troubleSubtypeIdField.get(entity);

				Field responsibleForTheCaseIdField = entityType.getDeclaredField("responsibleForTheCaseId");
				responsibleForTheCaseIdField.setAccessible(true);
				String responsibleForTheCaseId = (String) responsibleForTheCaseIdField.get(entity);

				Field patientCompanyIdField = entityType.getDeclaredField("patientCompanyId");
				patientCompanyIdField.setAccessible(true);
				String patientCompanyId = (String) patientCompanyIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				if (troubleAreaId != null) {
					Field field = entity.getClass().getDeclaredField("troubleArea");
					field.setAccessible(true);

					TroubleArea troubleArea = new TroubleArea();
					troubleArea.setId(troubleAreaId);
					field.set(entity, troubleArea);

					field = null;
					troubleArea = null;
				}

				if (troubleTypeId != null) {
					Field field = entity.getClass().getDeclaredField("troubleType");
					field.setAccessible(true);

					TroubleType troubleType = new TroubleType();
					troubleType.setId(troubleTypeId);
					field.set(entity, troubleType);

					field = null;
					troubleType = null;
				}

				if (troubleSubtypeId != null) {
					Field field = entity.getClass().getDeclaredField("troubleSubtype");
					field.setAccessible(true);

					TroubleSubtype troubleSubtype = new TroubleSubtype();
					troubleSubtype.setId(troubleSubtypeId);
					field.set(entity, troubleSubtype);

					field = null;
					troubleSubtype = null;
				}

				if (responsibleForTheCaseId != null) {
					Field field = entity.getClass().getDeclaredField("responsibleForTheCase");
					field.setAccessible(true);

					Account responsibleForTheCase = new Account();
					responsibleForTheCase.setId(responsibleForTheCaseId);
					field.set(entity, responsibleForTheCase);

					field = null;
					responsibleForTheCase = null;
				}

				if (patientCompanyId != null) {
					Field field = entity.getClass().getDeclaredField("patientCompany");
					field.setAccessible(true);

					PatientCompany patientCompany = new PatientCompany();
					patientCompany.setId(patientCompanyId);
					field.set(entity, patientCompany);

					field = null;
					patientCompany = null;
				}

				patientIdField = null;
				patientId = null;
				troubleAreaIdField = null;
				troubleAreaId = null;
				troubleTypeIdField = null;
				troubleTypeId = null;
				troubleSubtypeIdField = null;
				troubleSubtypeId = null;
				responsibleForTheCaseIdField = null;
				responsibleForTheCaseId = null;
				patientCompanyIdField = null;
				patientCompanyId = null;

			}
				break;

			case "PatientCompany": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				Field companyHealthPlanIdField = entityType.getDeclaredField("companyHealthPlanId");
				companyHealthPlanIdField.setAccessible(true);
				String companyHealthPlanId = (String) companyHealthPlanIdField.get(entity);

				Field branchIdField = entityType.getDeclaredField("branchId");
				branchIdField.setAccessible(true);
				String branchId = (String) branchIdField.get(entity);

				Field subareaIdField = entityType.getDeclaredField("subareaId");
				subareaIdField.setAccessible(true);
				String subareaId = (String) subareaIdField.get(entity);

				Field levelIdField = entityType.getDeclaredField("levelId");
				levelIdField.setAccessible(true);
				String levelId = (String) levelIdField.get(entity);

				Field costCenterIdField = entityType.getDeclaredField("costCenterId");
				costCenterIdField.setAccessible(true);
				String costCenterId = (String) costCenterIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				if (companyId != null) {
					Field field = entity.getClass().getDeclaredField("company");
					field.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					field.set(entity, company);

					field = null;
					company = null;
				}

				if (companyHealthPlanId != null) {
					Field field = entity.getClass().getDeclaredField("companyHealthPlan");
					field.setAccessible(true);

					CompanyHealthPlan companyHealthPlan = new CompanyHealthPlan();
					companyHealthPlan.setId(companyHealthPlanId);
					field.set(entity, companyHealthPlan);

					field = null;
					companyHealthPlan = null;
				}

				if (branchId != null) {
					Field field = entity.getClass().getDeclaredField("branch");
					field.setAccessible(true);

					Branch branch = new Branch();
					branch.setId(branchId);
					field.set(entity, branch);

					field = null;
					branch = null;
				}

				if (subareaId != null) {
					Field field = entity.getClass().getDeclaredField("subarea");
					field.setAccessible(true);

					Subarea subarea = new Subarea();
					subarea.setId(subareaId);
					field.set(entity, subarea);

					field = null;
					subarea = null;
				}

				if (levelId != null) {
					Field field = entity.getClass().getDeclaredField("level");
					field.setAccessible(true);

					Level level = new Level();
					level.setId(levelId);
					field.set(entity, level);

					field = null;
					level = null;
				}

				if (costCenterId != null) {
					Field field = entity.getClass().getDeclaredField("costCenter");
					field.setAccessible(true);

					CostCenter costCenter = new CostCenter();
					costCenter.setId(costCenterId);
					field.set(entity, costCenter);

					field = null;
					costCenter = null;
				}

				patientIdField = null;
				patientId = null;
				companyIdField = null;
				companyId = null;
				companyHealthPlanIdField = null;
				companyHealthPlanId = null;
				branchIdField = null;
				branchId = null;
				subareaIdField = null;
				subareaId = null;
				levelIdField = null;
				levelId = null;
				costCenterIdField = null;
				costCenterId = null;

			}
				break;

			case "PatientCaseEvolution": {

				Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				String patientCaseId = (String) patientCaseIdField.get(entity);

				Field patientCaseForwardingAttendanceIdField = entityType
						.getDeclaredField("patientCaseForwardingAttendanceId");
				patientCaseForwardingAttendanceIdField.setAccessible(true);
				String patientCaseForwardingAttendanceId = (String) patientCaseForwardingAttendanceIdField.get(entity);

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (patientCaseId != null) {
					Field field = entity.getClass().getDeclaredField("patientCase");
					field.setAccessible(true);

					PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					field.set(entity, patientCase);

					field = null;
					patientCase = null;
				}

				if (patientCaseForwardingAttendanceId != null) {
					Field field = entity.getClass().getDeclaredField("patientCaseForwardingAttendance");
					field.setAccessible(true);

					PatientCaseForwardingAttendance patientCaseForwardingAttendance = new PatientCaseForwardingAttendance();
					patientCaseForwardingAttendance.setId(patientCaseForwardingAttendanceId);
					field.set(entity, patientCaseForwardingAttendance);

					field = null;
					patientCaseForwardingAttendance = null;
				}

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				patientCaseIdField = null;
				patientCaseId = null;
				patientCaseForwardingAttendanceIdField = null;
				patientCaseForwardingAttendanceId = null;
				providerIdField = null;
				providerId = null;

			}
				break;

			case "PatientCaseForwarding": {

				Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				String patientCaseId = (String) patientCaseIdField.get(entity);

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (patientCaseId != null) {
					Field field = entity.getClass().getDeclaredField("patientCase");
					field.setAccessible(true);

					PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					field.set(entity, patientCase);

					field = null;
					patientCase = null;
				}

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				patientCaseIdField = null;
				patientCaseId = null;
				providerIdField = null;
				providerId = null;

			}
				break;

			case "ProviderAdditionalCourse": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;

			}
				break;

			case "ProviderAddress": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;
			}
				break;

			case "ProviderAttachment": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;
			}
				break;

			case "ProviderEmail": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;
			}
				break;

			case "ProviderFormation": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;

			}
				break;

			case "ProviderLanguage": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;
			}
				break;

			case "ProviderPhone": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;
			}
				break;

			case "ProviderServiceModel": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;

			}
				break;

			case "ProviderServiceModelAvailability": {

				Field providerServiceModelIdField = entityType.getDeclaredField("providerServiceModelId");
				providerServiceModelIdField.setAccessible(true);
				String providerServiceModelId = (String) providerServiceModelIdField.get(entity);

				if (providerServiceModelId != null) {
					Field field = entity.getClass().getDeclaredField("providerServiceModel");
					field.setAccessible(true);

					ProviderServiceModel providerServiceModel = new ProviderServiceModel();
					providerServiceModel.setId(providerServiceModelId);
					field.set(entity, providerServiceModel);

					field = null;
					providerServiceModel = null;
				}

				providerServiceModelIdField = null;
				providerServiceModelId = null;

			}
				break;

			case "QuestionsAndAnswers": {

				Field customerSurveyIdField = entityType.getDeclaredField("customerSurveyId");
				customerSurveyIdField.setAccessible(true);
				String customerSurveyId = (String) customerSurveyIdField.get(entity);

				if (customerSurveyId != null) {
					Field field = entity.getClass().getDeclaredField("customerSurvey");
					field.setAccessible(true);

					CustomerSurvey customerSurvey = new CustomerSurvey();
					customerSurvey.setId(customerSurveyId);
					field.set(entity, customerSurvey);

					field = null;
					customerSurvey = null;
				}

				customerSurveyIdField = null;
				customerSurveyId = null;

			}
				break;

			case "ProviderPaymentItem": {

				Field providerPaymentIdField = entityType.getDeclaredField("providerPaymentId");
				providerPaymentIdField.setAccessible(true);
				String providerPaymentId = (String) providerPaymentIdField.get(entity);

				Field patientCaseForwardingAttendanceIdField = entityType
						.getDeclaredField("patientCaseForwardingAttendanceId");
				patientCaseForwardingAttendanceIdField.setAccessible(true);
				String patientCaseForwardingAttendanceId = (String) patientCaseForwardingAttendanceIdField.get(entity);

				if (providerPaymentId != null) {
					Field field = entity.getClass().getDeclaredField("providerPayment");
					field.setAccessible(true);

					ProviderPayment providerPayment = new ProviderPayment();
					providerPayment.setId(providerPaymentId);
					field.set(entity, providerPayment);

					field = null;
					providerPayment = null;
				}

				if (patientCaseForwardingAttendanceId != null) {
					Field field = entity.getClass().getDeclaredField("patientCaseForwardingAttendance");
					field.setAccessible(true);

					PatientCaseForwardingAttendance patientCaseForwardingAttendance = new PatientCaseForwardingAttendance();
					patientCaseForwardingAttendance.setId(patientCaseForwardingAttendanceId);
					field.set(entity, patientCaseForwardingAttendance);

					field = null;
					patientCaseForwardingAttendance = null;
				}

				providerPaymentIdField = null;
				providerPaymentId = null;
				patientCaseForwardingAttendanceIdField = null;
				patientCaseForwardingAttendanceId = null;

			}
				break;

			case "ProviderHealthPlan": {

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				providerIdField = null;
				providerId = null;

			}
				break;

			case "PatientCaseForwardingAttendance": {

				Field patientCaseForwardingIdField = entityType.getDeclaredField("patientCaseForwardingId");
				patientCaseForwardingIdField.setAccessible(true);
				String patientCaseForwardingId = (String) patientCaseForwardingIdField.get(entity);

				Field providerPaymentClosureIdField = entityType.getDeclaredField("providerPaymentClosureId");
				providerPaymentClosureIdField.setAccessible(true);
				String providerPaymentClosureId = (String) providerPaymentClosureIdField.get(entity);

				if (patientCaseForwardingId != null) {
					Field field = entity.getClass().getDeclaredField("patientCaseForwarding");
					field.setAccessible(true);

					PatientCaseForwarding patientCaseForwarding = new PatientCaseForwarding();
					patientCaseForwarding.setId(patientCaseForwardingId);
					field.set(entity, patientCaseForwarding);

					field = null;
					patientCaseForwarding = null;
				}

				if (providerPaymentClosureId != null) {
					Field field = entity.getClass().getDeclaredField("providerPaymentClosure");
					field.setAccessible(true);

					ProviderPayment providerPaymentClosure = new ProviderPayment();
					providerPaymentClosure.setId(providerPaymentClosureId);
					field.set(entity, providerPaymentClosure);

					field = null;
					providerPaymentClosure = null;
				}

				patientCaseForwardingIdField = null;
				patientCaseForwardingId = null;
				providerPaymentClosureIdField = null;
				providerPaymentClosureId = null;

			}
				break;

			case "PatientAddress": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				patientIdField = null;
				patientId = null;

			}
				break;

			case "PatientEmail": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				patientIdField = null;
				patientId = null;

			}
				break;

			case "PatientPhone": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				patientIdField = null;
				patientId = null;

			}
				break;

			case "PatientFamilyGroup": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				Field relativeIdField = entityType.getDeclaredField("relativeId");
				relativeIdField.setAccessible(true);
				String relativeId = (String) relativeIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				if (relativeId != null) {
					Field field = entity.getClass().getDeclaredField("relative");
					field.setAccessible(true);

					Patient relative = new Patient();
					relative.setId(relativeId);
					field.set(entity, relative);

					field = null;
					relative = null;
				}

				patientIdField = null;
				patientId = null;
				relativeIdField = null;
				relativeId = null;

			}
				break;

			case "PatientAttachment": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Patient patient = new Patient();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}
				patientIdField = null;
				patientId = null;

			}
				break;

			case "AccountCode": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "SpreadsheetImport": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "Provider": {

				Field occupationIdField = entityType.getDeclaredField("occupationId");
				occupationIdField.setAccessible(true);
				String occupationId = (String) occupationIdField.get(entity);

				Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				String supplierId = (String) supplierIdField.get(entity);

				if (occupationId != null) {
					Field field = entity.getClass().getDeclaredField("occupation");
					field.setAccessible(true);

					Occupation occupation = new Occupation();
					occupation.setId(occupationId);
					field.set(entity, occupation);

					field = null;
					occupation = null;
				}

				if (supplierId != null) {
					Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);

					field = null;
					supplier = null;
				}

				occupationIdField = null;
				occupationId = null;
				supplierIdField = null;
				supplierId = null;
			}
				break;

			case "BenefitsApplicationForm": {

				Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				String patientCaseId = (String) patientCaseIdField.get(entity);

				if (patientCaseId != null) {
					Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);

					patientCaseField = null;
					patientCase = null;
				}

				patientCaseIdField = null;
				patientCaseId = null;
			}
				break;

			case "PatientCaseAttachment": {

				Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				String patientCaseId = (String) patientCaseIdField.get(entity);

				if (patientCaseId != null) {
					Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);

					patientCaseField = null;
					patientCase = null;
				}

				patientCaseIdField = null;
				patientCaseId = null;
			}
				break;

			case "CustomerSurvey": {

				Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				String patientCaseId = (String) patientCaseIdField.get(entity);

				if (patientCaseId != null) {
					Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);

					patientCaseField = null;
					patientCase = null;
				}

				patientCaseIdField = null;
				patientCaseId = null;
			}
				break;

			case "SessionReview": {

				Field patientCaseForwardingAttendanceIdField = entityType
						.getDeclaredField("patientCaseForwardingAttendanceId");
				patientCaseForwardingAttendanceIdField.setAccessible(true);
				String patientCaseForwardingAttendanceId = (String) patientCaseForwardingAttendanceIdField.get(entity);

				if (patientCaseForwardingAttendanceId != null) {
					Field Field = entity.getClass().getDeclaredField("patientCaseForwardingAttendance");
					Field.setAccessible(true);

					PatientCaseForwardingAttendance patientCaseForwardingAttendance = new PatientCaseForwardingAttendance();
					patientCaseForwardingAttendance.setId(patientCaseForwardingAttendanceId);
					Field.set(entity, patientCaseForwardingAttendance);

					Field = null;
					patientCaseForwardingAttendance = null;
				}

				patientCaseForwardingAttendanceIdField = null;
				patientCaseForwardingAttendanceId = null;
			}
				break;

			case "Schedule": {

				Field patientCaseIdField = entityType.getDeclaredField("patientCaseId");
				patientCaseIdField.setAccessible(true);
				String patientCaseId = (String) patientCaseIdField.get(entity);

				Field accountIdField = entityType.getDeclaredField("accountId");
				accountIdField.setAccessible(true);
				String accountId = (String) accountIdField.get(entity);

				if (patientCaseId != null) {
					Field patientCaseField = entity.getClass().getDeclaredField("patientCase");
					patientCaseField.setAccessible(true);

					PatientCase patientCase = new PatientCase();
					patientCase.setId(patientCaseId);
					patientCaseField.set(entity, patientCase);

					patientCaseField = null;
					patientCase = null;
				}

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				patientCaseIdField = null;
				patientCaseId = null;
				accountIdField = null;
				accountId = null;
			}
				break;

			case "BenefitsApplicationFormQuestionsAndAnswers": {

				Field benefitsApplicationFormIdField = entityType.getDeclaredField("benefitsApplicationFormId");
				benefitsApplicationFormIdField.setAccessible(true);
				String benefitsApplicationFormId = (String) benefitsApplicationFormIdField.get(entity);

				if (benefitsApplicationFormId != null) {
					Field benefitsApplicationFormField = entity.getClass().getDeclaredField("benefitsApplicationForm");
					benefitsApplicationFormField.setAccessible(true);

					BenefitsApplicationForm benefitsApplicationForm = new BenefitsApplicationForm();
					benefitsApplicationForm.setId(benefitsApplicationFormId);
					benefitsApplicationFormField.set(entity, benefitsApplicationForm);

					benefitsApplicationFormField = null;
					benefitsApplicationForm = null;
				}

				benefitsApplicationFormIdField = null;
				benefitsApplicationFormId = null;
			}
				break;

			case "Branch": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "Subarea": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "SupplierAddress": {

				Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				String supplierId = (String) supplierIdField.get(entity);

				if (supplierId != null) {
					Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);

					field = null;
					supplier = null;
				}

				supplierIdField = null;
				supplierId = null;
			}
				break;

			case "SupplierEmail": {

				Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				String supplierId = (String) supplierIdField.get(entity);

				if (supplierId != null) {
					Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);

					field = null;
					supplier = null;
				}

				supplierIdField = null;
				supplierId = null;
			}
				break;

			case "SupplierPhone": {

				Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				String supplierId = (String) supplierIdField.get(entity);

				if (supplierId != null) {
					Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);

					field = null;
					supplier = null;
				}

				supplierIdField = null;
				supplierId = null;
			}
				break;

			case "ClientAddress": {

				Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);

					clientField = null;
					client = null;
				}

				clientIdField = null;
				clientId = null;
			}
				break;

			case "ClientEmail": {

				Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);

					clientField = null;
					client = null;
				}

				clientIdField = null;
				clientId = null;
			}
				break;

			case "ClientPhone": {

				Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);

					clientField = null;
					client = null;
				}

				clientIdField = null;
				clientId = null;
			}
				break;

			case "Company": {

				Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				String clientId = (String) clientIdField.get(entity);

				if (clientId != null) {
					Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);

					clientField = null;
					client = null;
				}

				clientIdField = null;
				clientId = null;
			}
				break;

			case "Event": {

				Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				String clientId = (String) clientIdField.get(entity);

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (clientId != null) {
					Field clientField = entity.getClass().getDeclaredField("client");
					clientField.setAccessible(true);

					Client client = new Client();
					client.setId(clientId);
					clientField.set(entity, client);

					clientField = null;
					client = null;
				}

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				if (providerId != null) {
					Field providerField = entity.getClass().getDeclaredField("provider");
					providerField.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					providerField.set(entity, provider);

					providerField = null;
					provider = null;
				}

				clientIdField = null;
				clientId = null;
				companyIdField = null;
				companyId = null;
				providerIdField = null;
				providerId = null;
			}
				break;

			case "CompanyAdditionalBenefit": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyAddress": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyAttachment": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyElegiblePatient": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyEmail": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyPhone": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyProduct": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CostCenter": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "companyHealthPlan": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyManager": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "Level": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				if (companyId != null) {
					Field companyField = entity.getClass().getDeclaredField("company");
					companyField.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					companyField.set(entity, company);

					companyField = null;
					company = null;
				}

				companyIdField = null;
				companyId = null;
			}
				break;

			case "CompanyManagerEmail": {

				Field companyManagerIdField = entityType.getDeclaredField("companyManagerId");
				companyManagerIdField.setAccessible(true);
				String companyManagerId = (String) companyManagerIdField.get(entity);

				if (companyManagerId != null) {
					Field companyManagerField = entity.getClass().getDeclaredField("companyManager");
					companyManagerField.setAccessible(true);

					CompanyManager companyManager = new CompanyManager();
					companyManager.setId(companyManagerId);
					companyManagerField.set(entity, companyManager);

					companyManagerField = null;
					companyManager = null;
				}

				companyManagerIdField = null;
				companyManagerId = null;
			}
				break;

			case "CompanyManagerPhone": {

				Field companyManagerIdField = entityType.getDeclaredField("companyManagerId");
				companyManagerIdField.setAccessible(true);
				String companyManagerId = (String) companyManagerIdField.get(entity);

				if (companyManagerId != null) {
					Field companyManagerField = entity.getClass().getDeclaredField("companyManager");
					companyManagerField.setAccessible(true);

					CompanyManager companyManager = new CompanyManager();
					companyManager.setId(companyManagerId);
					companyManagerField.set(entity, companyManager);

					companyManagerField = null;
					companyManager = null;
				}

				companyManagerIdField = null;
				companyManagerId = null;
			}
				break;

			case "TroubleType": {

				Field troubleAreaIdField = entityType.getDeclaredField("troubleAreaId");
				troubleAreaIdField.setAccessible(true);
				String troubleAreaId = (String) troubleAreaIdField.get(entity);

				if (troubleAreaId != null) {
					Field field = entity.getClass().getDeclaredField("troubleArea");
					field.setAccessible(true);

					TroubleArea troubleArea = new TroubleArea();
					troubleArea.setId(troubleAreaId);
					field.set(entity, troubleArea);

					field = null;
					troubleArea = null;
				}

				troubleAreaIdField = null;
				troubleAreaId = null;
			}
				break;

			case "TroubleSubtype": {

				Field troubleTypeIdField = entityType.getDeclaredField("troubleTypeId");
				troubleTypeIdField.setAccessible(true);
				String troubleTypeId = (String) troubleTypeIdField.get(entity);

				if (troubleTypeId != null) {
					Field field = entity.getClass().getDeclaredField("troubleType");
					field.setAccessible(true);

					TroubleType troubleType = new TroubleType();
					troubleType.setId(troubleTypeId);
					field.set(entity, troubleType);

					field = null;
					troubleType = null;
				}

				troubleTypeIdField = null;
				troubleTypeId = null;
			}
				break;

			case "Expense": {

				Field supplierIdField = entityType.getDeclaredField("supplierId");
				supplierIdField.setAccessible(true);
				String supplierId = (String) supplierIdField.get(entity);

				Field categoryIdField = entityType.getDeclaredField("categoryId");
				categoryIdField.setAccessible(true);
				String categoryId = (String) categoryIdField.get(entity);

				Field providerPaymentIdField = entityType.getDeclaredField("providerPaymentId");
				providerPaymentIdField.setAccessible(true);
				String providerId = (String) providerPaymentIdField.get(entity);

				Field eventIdField = entityType.getDeclaredField("eventId");
				eventIdField.setAccessible(true);
				String eventId = (String) eventIdField.get(entity);

				if (supplierId != null) {
					Field field = entity.getClass().getDeclaredField("supplier");
					field.setAccessible(true);

					Supplier supplier = new Supplier();
					supplier.setId(supplierId);
					field.set(entity, supplier);

					field = null;
					supplier = null;
				}

				if (categoryId != null) {
					Field field = entity.getClass().getDeclaredField("category");
					field.setAccessible(true);

					Category category = new Category();
					category.setId(categoryId);
					field.set(entity, category);

					field = null;
					category = null;
				}

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("providerPayment");
					field.setAccessible(true);

					ProviderPayment providerPayment = new ProviderPayment();
					providerPayment.setId(providerId);
					field.set(entity, providerPayment);

					field = null;
					providerPayment = null;
				}

				if (eventId != null) {
					Field field = entity.getClass().getDeclaredField("event");
					field.setAccessible(true);

					Event event = new Event();
					event.setId(eventId);
					field.set(entity, event);

					field = null;
					event = null;
				}

				supplierIdField = null;
				supplierId = null;
				categoryIdField = null;
				categoryId = null;
				providerPaymentIdField = null;
				providerId = null;
				eventIdField = null;
				eventId = null;

			}
				break;

			case "Revenue": {

				Field clientIdField = entityType.getDeclaredField("clientId");
				clientIdField.setAccessible(true);
				String clientId = (String) clientIdField.get(entity);

				Field categoryIdField = entityType.getDeclaredField("categoryId");
				categoryIdField.setAccessible(true);
				String categoryId = (String) categoryIdField.get(entity);

				if (clientId != null) {
					Field field = entity.getClass().getDeclaredField("client");
					field.setAccessible(true);

					Client client = new Client();
					client.setId(clientId);
					field.set(entity, client);

					field = null;
					client = null;
				}

				if (categoryId != null) {
					Field field = entity.getClass().getDeclaredField("category");
					field.setAccessible(true);

					Category category = new Category();
					category.setId(categoryId);
					field.set(entity, category);

					field = null;
					category = null;
				}

				clientIdField = null;
				clientId = null;
				categoryIdField = null;
				categoryId = null;
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
