package br.com.database_copier;

import org.quartz.SchedulerException;

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

public class CarelinkEapIntegrationApplication {

	public static void main(String[] args) throws SchedulerException {

		int itensPerPage = 1000;

		System.out.println("IMPORTANDO ACCOUNT 1/68");
		AccountJob.execute(itensPerPage);

		System.out.println("IMPORTANDO ACCOUNT_CODE 2/68");
		AccountCodeJob.execute(itensPerPage);

		System.out.println("IMPORTANDO BANK_DATA 3/68");
		BankDataJob.execute(itensPerPage);

		System.out.println("IMPORTANDO CLIENT 4/68");
		ClientJob.execute(itensPerPage);

		System.out.println("IMPORTANDO CLIENT_ADDRESS 5/68");
		ClientAddressJob.execute(itensPerPage);

		System.out.println("IMPORTANDO CLIENT_EMAIL 6/68");
		ClientEmailJob.execute(itensPerPage);

		System.out.println("IMPORTANDO CLIENT_PHONE 7/68");
		ClientPhoneJob.execute(itensPerPage);

		System.out.println("IMPORTANDO CATEGORY 8/68");
		CategoryJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY 9/68");
		CompanyJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_ADDITIONAL_BENEFIT 10/68");
		CompanyAdditionalBenefitJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_ADDRESS 11/68");
		CompanyAddressJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_ATTACHMENT 12/68");
		CompanyAttachmentJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_ELIGIBLE_PATIENT 13/68");
		CompanyElegiblePatientJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_EMAIL 14/68");
		CompanyEmailJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_HEALTH_PLAN 15/68");
		CompanyHealthPlanJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_MANAGER 16/68");
		CompanyManagerJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_MANAGER_EMAIL 17/68");
		CompanyManagerEmailJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_MANAGER_PHONE 18/68");
		CompanyManagerPhoneJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_PHONE 19/68");
		CompanyPhoneJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COMPANY_PRODUCT 20/68");
		CompanyProductJob.execute(itensPerPage);

		System.out.println("IMPORTANDO BRANCH 21/68");
		BranchJob.execute(itensPerPage);

		System.out.println("IMPORTANDO COST_CENTER 22/68");
		CostCenterJob.execute(itensPerPage);

		System.out.println("IMPORTANDO OCCUPATION 23/68");
		OccupationJob.execute(itensPerPage);

		System.out.println("IMPORTANDO LEVEL 24/68");
		LevelJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_IMPORT_NATURA_DATA 25/68");
		PatientImportNaturaDataJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT 26/68");
		PatientJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_ADDRESS 27/68");
		PatientAddressJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_ATTACHMENT 28/68");
		PatientAttachmentJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_EMAIL 29/68");
		PatientEmailJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_PHONE 30/68");
		PatientPhoneJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_FAMILY_GROUP 31/68");
		PatientFamilyGroupJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SUBAREA 32/68");
		SubareaJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_COMPANY 33/68");
		PatientCompanyJob.execute(itensPerPage);

		System.out.println("IMPORTANDO TROUBLE_AREA 34/68");
		TroubleAreaJob.execute(itensPerPage);

		System.out.println("IMPORTANDO TROUBLE_TYPE 35/68");
		TroubleTypeJob.execute(itensPerPage);

		System.out.println("IMPORTANDO TROUBLE_SUBTYPE 36/68");
		TroubleSubtypeJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_CASE 37/68");
		PatientCaseJob.execute(itensPerPage);

		System.out.println("IMPORTANDO CUSTOMER_SURVEY 38/68");
		CustomerSurveyJob.execute(itensPerPage);

		System.out.println("IMPORTANDO BENEFITS_APPLICATION_FORM 39/68");
		BenefitsApplicationFormJob.execute(itensPerPage);

		System.out.println("IMPORTANDO BENEFITS_APPLICATION_FORM_QUESTIONS_AND_ANSWERS 40/68");
		BenefitsApplicationFormQuestionsAndAnswersJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_CASE_ATTACHMENT 41/68");
		PatientCaseAttachmentJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SUPPLIER 42/68");
		SupplierJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SUPPLIER_ADDRESS 43/68");
		SupplierAddressJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SUPPLIER_PHONE 44/68");
		SupplierPhoneJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SUPPLIER_EMAIL 45/68");
		SupplierEmailJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER 46/68");
		ProviderJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_ADDITIONAL_COURSE 47/68");
		ProviderAdditionalCourseJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_ADDRESS 48/68");
		ProviderAddressJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_ATTACHMENT 49/68");
		ProviderAttachmentJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_EMAIL 50/68");
		ProviderEmailJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_FORMATION 51/68");
		ProviderFormationJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_HEALTH_PLAN 52/68");
		ProviderHealthPlanJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_LANGUAGE 53/68");
		ProviderLanguageJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_PAYMENT 54/68");
		ProviderPaymentJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_PHONE 55/68");
		ProviderPhoneJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_SERVICE_MODEL 56/68");
		ProviderServiceModelJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_SERVICE_MODEL_AVAILABILITY 57/68");
		ProviderServiceModelAvailabilityJob.execute(itensPerPage);

		System.out.println("IMPORTANDO QUESTIONS_AND_ANSWERS 58/68");
		QuestionsAndAnswersJob.execute(itensPerPage);

		System.out.println("IMPORTANDO REVENUE 59/68");
		RevenueJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SCHEDULE 60/68");
		ScheduleJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SPREADSHEET_IMPORT 61/68");
		SpreadsheetImportJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_CASE_FORWARDING 62/68");
		PatientCaseForwardingJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_CASE_FORWARDING_ATTENDANCE 63/68");
		PatientCaseForwardingAttendanceJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PATIENT_CASE_EVOLUTION 64/68");
		PatientCaseEvolutionJob.execute(itensPerPage);

		System.out.println("IMPORTANDO PROVIDER_PAYMENT_ITEM 65/68");
		ProviderPaymentItemJob.execute(itensPerPage);

		System.out.println("IMPORTANDO SESSION_REVIEW 66/68");
		SessionReviewJob.execute(itensPerPage);

		System.out.println("IMPORTANDO EVENT 67/68");
		EventJob.execute(itensPerPage);

		System.out.println("IMPORTANDO EXPENSE 68/68");
		ExpenseJob.execute(itensPerPage);

		System.exit(200);

	}
}