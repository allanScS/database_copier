package br.com.database_copier;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.Session;

import br.com.database_copier.jobs.AccountCodeJob;
import br.com.database_copier.jobs.AccountJob;
import br.com.database_copier.jobs.BankDataJob;
import br.com.database_copier.jobs.BenefitsApplicationFormJob;
import br.com.database_copier.jobs.BenefitsApplicationFormQuestionsAndAnswersJob;
import br.com.database_copier.jobs.BranchJob;
import br.com.database_copier.jobs.CategoryJob;
import br.com.database_copier.jobs.ClientAddressJob;
import br.com.database_copier.jobs.ClientEmailJob;
import br.com.database_copier.jobs.ClientJob;
import br.com.database_copier.jobs.ClientPhoneJob;
import br.com.database_copier.jobs.CompanyAdditionalBenefitJob;
import br.com.database_copier.jobs.CompanyAddressJob;
import br.com.database_copier.jobs.CompanyAttachmentJob;
import br.com.database_copier.jobs.CompanyElegiblePatientJob;
import br.com.database_copier.jobs.CompanyEmailJob;
import br.com.database_copier.jobs.CompanyHealthPlanJob;
import br.com.database_copier.jobs.CompanyJob;
import br.com.database_copier.jobs.CompanyManagerEmailJob;
import br.com.database_copier.jobs.CompanyManagerJob;
import br.com.database_copier.jobs.CompanyManagerPhoneJob;
import br.com.database_copier.jobs.CompanyPhoneJob;
import br.com.database_copier.jobs.CompanyProductJob;
import br.com.database_copier.jobs.CostCenterJob;
import br.com.database_copier.jobs.CustomerSurveyJob;
import br.com.database_copier.jobs.EventJob;
import br.com.database_copier.jobs.ExpenseJob;
import br.com.database_copier.jobs.LevelJob;
import br.com.database_copier.jobs.OccupationJob;
import br.com.database_copier.jobs.PatientAddressJob;
import br.com.database_copier.jobs.PatientAttachmentJob;
import br.com.database_copier.jobs.PatientCaseAttachmentJob;
import br.com.database_copier.jobs.PatientCaseEvolutionJob;
import br.com.database_copier.jobs.PatientCaseForwardingAttendanceJob;
import br.com.database_copier.jobs.PatientCaseForwardingJob;
import br.com.database_copier.jobs.PatientCaseJob;
import br.com.database_copier.jobs.PatientCompanyJob;
import br.com.database_copier.jobs.PatientEmailJob;
import br.com.database_copier.jobs.PatientFamilyGroupJob;
import br.com.database_copier.jobs.PatientImportNaturaDataJob;
import br.com.database_copier.jobs.PatientJob;
import br.com.database_copier.jobs.PatientPhoneJob;
import br.com.database_copier.jobs.ProviderAdditionalCourseJob;
import br.com.database_copier.jobs.ProviderAddressJob;
import br.com.database_copier.jobs.ProviderAttachmentJob;
import br.com.database_copier.jobs.ProviderEmailJob;
import br.com.database_copier.jobs.ProviderFormationJob;
import br.com.database_copier.jobs.ProviderHealthPlanJob;
import br.com.database_copier.jobs.ProviderJob;
import br.com.database_copier.jobs.ProviderLanguageJob;
import br.com.database_copier.jobs.ProviderPaymentItemJob;
import br.com.database_copier.jobs.ProviderPaymentJob;
import br.com.database_copier.jobs.ProviderPhoneJob;
import br.com.database_copier.jobs.ProviderServiceModelAvailabilityJob;
import br.com.database_copier.jobs.ProviderServiceModelJob;
import br.com.database_copier.jobs.QuestionsAndAnswersJob;
import br.com.database_copier.jobs.RevenueJob;
import br.com.database_copier.jobs.ScheduleJob;
import br.com.database_copier.jobs.SessionReviewJob;
import br.com.database_copier.jobs.SpreadsheetImportJob;
import br.com.database_copier.jobs.SubareaJob;
import br.com.database_copier.jobs.SupplierAddressJob;
import br.com.database_copier.jobs.SupplierEmailJob;
import br.com.database_copier.jobs.SupplierJob;
import br.com.database_copier.jobs.SupplierPhoneJob;
import br.com.database_copier.jobs.TroubleAreaJob;
import br.com.database_copier.jobs.TroubleSubtypeJob;
import br.com.database_copier.jobs.TroubleTypeJob;
import br.com.database_copier.util.HibernateUtil;

public class DatabaseCopierApplication {

	public static void main(String[] args) {

		int itensPerPage = 10000;
		int poolLimit = 10;

		final LocalDateTime start = LocalDateTime.now();

		final Session source = HibernateUtil.startSessionFactorySourceDatabase().openSession();

		System.out.println("\nIMPORTANDO ACCOUNT 1/68\n");
		AccountJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO ACCOUNT_CODE 2/68\n");
		AccountCodeJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO BANK_DATA 3/68\n");
		BankDataJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO CLIENT 4/68\n");
		ClientJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO CLIENT_ADDRESS 5/68\n");
		ClientAddressJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO CLIENT_EMAIL 6/68\n");
		ClientEmailJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO CLIENT_PHONE 7/68\n");
		ClientPhoneJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO CATEGORY 8/68\n");
		CategoryJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY 9/68\n");
		CompanyJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_ADDITIONAL_BENEFIT 10/68\n");
		CompanyAdditionalBenefitJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_ADDRESS 11/68\n");
		CompanyAddressJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_ATTACHMENT 12/68\n");
		CompanyAttachmentJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_ELIGIBLE_PATIENT 13/68\n");
		CompanyElegiblePatientJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_EMAIL 14/68\n");
		CompanyEmailJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_HEALTH_PLAN 15/68\n");
		CompanyHealthPlanJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_MANAGER 16/68\n");
		CompanyManagerJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_MANAGER_EMAIL 17/68\n");
		CompanyManagerEmailJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_MANAGER_PHONE 18/68\n");
		CompanyManagerPhoneJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_PHONE 19/68\n");
		CompanyPhoneJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COMPANY_PRODUCT 20/68\n");
		CompanyProductJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO BRANCH 21/68\n");
		BranchJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO COST_CENTER 22/68\n");
		CostCenterJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO OCCUPATION 23/68\n");
		OccupationJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO LEVEL 24/68\n");
		LevelJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_IMPORT_NATURA_DATA 25/68\n");
		PatientImportNaturaDataJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT 26/68\n");
		PatientJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_ADDRESS 27/68\n");
		PatientAddressJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_ATTACHMENT 28/68\n");
		PatientAttachmentJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_EMAIL 29/68\n");
		PatientEmailJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_PHONE 30/68\n");
		PatientPhoneJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_FAMILY_GROUP 31/68\n");
		PatientFamilyGroupJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SUBAREA 32/68\n");
		SubareaJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_COMPANY 33/68\n");
		PatientCompanyJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO TROUBLE_AREA 34/68\n");
		TroubleAreaJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO TROUBLE_TYPE 35/68\n");
		TroubleTypeJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO TROUBLE_SUBTYPE 36/68\n");
		TroubleSubtypeJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_CASE 37/68\n");
		PatientCaseJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO CUSTOMER_SURVEY 38/68\n");
		CustomerSurveyJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO BENEFITS_APPLICATION_FORM 39/68\n");
		BenefitsApplicationFormJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO BENEFITS_APPLICATION_FORM_QUESTIONS_AND_ANSWERS 40/68\n");
		BenefitsApplicationFormQuestionsAndAnswersJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_CASE_ATTACHMENT 41/68\n");
		PatientCaseAttachmentJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SUPPLIER 42/68\n");
		SupplierJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SUPPLIER_ADDRESS 43/68\n");
		SupplierAddressJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SUPPLIER_PHONE 44/68\n");
		SupplierPhoneJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SUPPLIER_EMAIL 45/68\n");
		SupplierEmailJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER 46/68\n");
		ProviderJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_ADDITIONAL_COURSE 47/68\n");
		ProviderAdditionalCourseJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_ADDRESS 48/68\n");
		ProviderAddressJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_ATTACHMENT 49/68\n");
		ProviderAttachmentJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_EMAIL 50/68\n");
		ProviderEmailJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_FORMATION 51/68\n");
		ProviderFormationJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_HEALTH_PLAN 52/68\n");
		ProviderHealthPlanJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_LANGUAGE 53/68\n");
		ProviderLanguageJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_PAYMENT 54/68\n");
		ProviderPaymentJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_PHONE 55/68\n");
		ProviderPhoneJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_SERVICE_MODEL 56/68\n");
		ProviderServiceModelJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_SERVICE_MODEL_AVAILABILITY 57/68\n");
		ProviderServiceModelAvailabilityJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO QUESTIONS_AND_ANSWERS 58/68\n");
		QuestionsAndAnswersJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO REVENUE 59/68\n");
		RevenueJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SCHEDULE 60/68\n");
		ScheduleJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SPREADSHEET_IMPORT 61/68\n");
		SpreadsheetImportJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_CASE_FORWARDING 62/68\n");
		PatientCaseForwardingJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_CASE_FORWARDING_ATTENDANCE 63/68\n");
		PatientCaseForwardingAttendanceJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PATIENT_CASE_EVOLUTION 64/68\n");
		PatientCaseEvolutionJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO PROVIDER_PAYMENT_ITEM 65/68\n");
		ProviderPaymentItemJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO SESSION_REVIEW 66/68\n");
		SessionReviewJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO EVENT 67/68\n");
		EventJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nIMPORTANDO EXPENSE 68/68\n");
		ExpenseJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nPROCESSO FINALIZADO !");

		final Duration duration = Duration.between(start, LocalDateTime.now());

		System.out.println("Duracao: " + duration.toDaysPart() + " dias e " + duration.toHoursPart() + " horas "
				+ duration.toMinutesPart() + " minutos");

		System.exit(200);

	}
}