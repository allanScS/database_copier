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
		int itensPerPage = 3000;
		int poolLimit = 50;

		final LocalDateTime start = LocalDateTime.now();

		Session source = HibernateUtil.startSessionFactorySourceDatabase().openSession();

		final Runnable[] jobs = { () -> AccountJob.execute(itensPerPage, poolLimit, source),
//				() -> AccountCodeJob.execute(itensPerPage, poolLimit, source),
//				() -> BankDataJob.execute(itensPerPage, poolLimit, source),
//				() -> ClientJob.execute(itensPerPage, poolLimit, source),
//				() -> ClientAddressJob.execute(itensPerPage, poolLimit, source),
//				() -> ClientEmailJob.execute(itensPerPage, poolLimit, source),
//				() -> ClientPhoneJob.execute(itensPerPage, poolLimit, source),
//				() -> CategoryJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyAdditionalBenefitJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyAddressJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyAttachmentJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyElegiblePatientJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyEmailJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyHealthPlanJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyManagerJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyManagerEmailJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyManagerPhoneJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyPhoneJob.execute(itensPerPage, poolLimit, source),
//				() -> CompanyProductJob.execute(itensPerPage, poolLimit, source),
//				() -> BranchJob.execute(itensPerPage, poolLimit, source),
//				() -> CostCenterJob.execute(itensPerPage, poolLimit, source),
//				() -> OccupationJob.execute(itensPerPage, poolLimit, source),
//				() -> LevelJob.execute(itensPerPage, poolLimit, source),
				() -> PatientImportNaturaDataJob.execute(itensPerPage, poolLimit, source),
				() -> PatientJob.execute(itensPerPage, poolLimit, source),
				() -> PatientAddressJob.execute(itensPerPage, poolLimit, source),
				() -> PatientAttachmentJob.execute(itensPerPage, poolLimit, source),
				() -> PatientEmailJob.execute(itensPerPage, poolLimit, source),
				() -> PatientPhoneJob.execute(itensPerPage, poolLimit, source),
				() -> PatientFamilyGroupJob.execute(itensPerPage, poolLimit, source),
				() -> SubareaJob.execute(itensPerPage, poolLimit, source),
				() -> PatientCompanyJob.execute(itensPerPage, poolLimit, source),
				() -> TroubleAreaJob.execute(itensPerPage, poolLimit, source),
				() -> TroubleTypeJob.execute(itensPerPage, poolLimit, source),
				() -> TroubleSubtypeJob.execute(itensPerPage, poolLimit, source),
				() -> PatientCaseJob.execute(itensPerPage, poolLimit, source),
				() -> CustomerSurveyJob.execute(itensPerPage, poolLimit, source),
				() -> BenefitsApplicationFormJob.execute(itensPerPage, poolLimit, source),
				() -> BenefitsApplicationFormQuestionsAndAnswersJob.execute(itensPerPage, poolLimit, source),
				() -> PatientCaseAttachmentJob.execute(itensPerPage, poolLimit, source),
				() -> SupplierJob.execute(itensPerPage, poolLimit, source),
				() -> SupplierAddressJob.execute(itensPerPage, poolLimit, source),
				() -> SupplierPhoneJob.execute(itensPerPage, poolLimit, source),
				() -> SupplierEmailJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderAdditionalCourseJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderAddressJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderAttachmentJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderEmailJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderFormationJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderHealthPlanJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderLanguageJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderPaymentJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderPhoneJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderServiceModelJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderServiceModelAvailabilityJob.execute(itensPerPage, poolLimit, source),
				() -> QuestionsAndAnswersJob.execute(itensPerPage, poolLimit, source),
				() -> RevenueJob.execute(itensPerPage, poolLimit, source),
				() -> ScheduleJob.execute(itensPerPage, poolLimit, source),
				() -> SpreadsheetImportJob.execute(itensPerPage, poolLimit, source),
				() -> PatientCaseForwardingJob.execute(itensPerPage, poolLimit, source),
				() -> PatientCaseForwardingAttendanceJob.execute(itensPerPage, poolLimit, source),
				() -> PatientCaseEvolutionJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderPaymentItemJob.execute(itensPerPage, poolLimit, source),
				() -> SessionReviewJob.execute(itensPerPage, poolLimit, source),
				() -> EventJob.execute(itensPerPage, poolLimit, source),
				() -> ExpenseJob.execute(itensPerPage, poolLimit, source) };

		for (int i = 0; i < jobs.length; i++) {
			System.out.printf("\nIMPORTANDO JOB %d/%d\n", i + 1, jobs.length);

			jobs[i].run();

			System.out.println("\nLimpando cache do Hibernate...");

			source.clear();

			System.out.println("Executando Garbage Collector...");
			System.gc();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		final Duration duration = Duration.between(start, LocalDateTime.now());
		System.out.printf("Duração total: %d horas, %d minutos, %d segundos\n", duration.toHours(),
				duration.toMinutesPart(), duration.toSecondsPart());
	}
}
