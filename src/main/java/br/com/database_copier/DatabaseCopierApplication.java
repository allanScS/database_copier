package br.com.database_copier;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.Session;

import br.com.database_copier.jobs.AccountCodeJob;
import br.com.database_copier.jobs.AccountJob;
import br.com.database_copier.jobs.AddressJob;
import br.com.database_copier.jobs.CallJob;
import br.com.database_copier.jobs.CardJob;
import br.com.database_copier.jobs.ChannelJob;
import br.com.database_copier.jobs.CompanyJob;
import br.com.database_copier.jobs.DataIntegrationJob;
import br.com.database_copier.jobs.DeviceJob;
import br.com.database_copier.jobs.DeviceNotificationJob;
import br.com.database_copier.jobs.EvaluationJob;
import br.com.database_copier.jobs.HealthConditionJob;
import br.com.database_copier.jobs.HospitalizationJob;
import br.com.database_copier.jobs.InsuranceJob;
import br.com.database_copier.jobs.InvitationJob;
import br.com.database_copier.jobs.MassMessageJob;
import br.com.database_copier.jobs.MedicalAppointmentJob;
import br.com.database_copier.jobs.MessageJob;
import br.com.database_copier.jobs.MyHealthJob;
import br.com.database_copier.jobs.NewsJob;
import br.com.database_copier.jobs.NotificationJob;
import br.com.database_copier.jobs.NotificationRelatedJob;
import br.com.database_copier.jobs.PrescriptionJob;
import br.com.database_copier.jobs.ProfileJob;
import br.com.database_copier.jobs.ProviderJob;
import br.com.database_copier.jobs.RefreshTokenJob;
import br.com.database_copier.jobs.RelatedJob;
import br.com.database_copier.jobs.ReviewJob;
import br.com.database_copier.jobs.ScheduleJob;
import br.com.database_copier.jobs.WorkShiftJob;
import br.com.database_copier.util.HibernateUtil;

public class DatabaseCopierApplication {

	public static void main(String[] args) {
		int itensPerPage = 3000;
		int poolLimit = 50;

		final LocalDateTime start = LocalDateTime.now();

		Session source = HibernateUtil.startSessionFactorySourceDatabase().openSession();

		final Runnable[] jobs = { 
				() -> ProfileJob.execute(itensPerPage, poolLimit, source),
				() -> AddressJob.execute(itensPerPage, poolLimit, source),
				() -> InsuranceJob.execute(itensPerPage, poolLimit, source),
				() -> ProviderJob.execute(itensPerPage, poolLimit, source),
				() -> AccountJob.execute(itensPerPage, poolLimit, source),
				() -> AccountCodeJob.execute(itensPerPage, poolLimit, source),
				() -> CompanyJob.execute(itensPerPage, poolLimit, source),
				() -> CardJob.execute(itensPerPage, poolLimit, source),
				() -> ChannelJob.execute(itensPerPage, poolLimit, source),
				() -> CallJob.execute(itensPerPage, poolLimit, source),
				() -> DataIntegrationJob.execute(itensPerPage, poolLimit, source),
				() -> DeviceJob.execute(itensPerPage, poolLimit, source),
				() -> DeviceNotificationJob.execute(itensPerPage, poolLimit, source),
				() -> EvaluationJob.execute(itensPerPage, poolLimit, source),
				() -> HealthConditionJob.execute(itensPerPage, poolLimit, source),
				() -> HospitalizationJob.execute(itensPerPage, poolLimit, source),
				() -> InvitationJob.execute(itensPerPage, poolLimit, source),
				() -> MassMessageJob.execute(itensPerPage, poolLimit, source),
				() -> MedicalAppointmentJob.execute(itensPerPage, poolLimit, source),
				() -> MessageJob.execute(itensPerPage, poolLimit, source),
				() -> MyHealthJob.execute(itensPerPage, poolLimit, source),
				() -> NewsJob.execute(itensPerPage, poolLimit, source),
				() -> PrescriptionJob.execute(itensPerPage, poolLimit, source),
				() -> NotificationJob.execute(itensPerPage, poolLimit, source),
				() -> RelatedJob.execute(itensPerPage, poolLimit, source),
				() -> RefreshTokenJob.execute(itensPerPage, poolLimit, source),
				() -> ReviewJob.execute(itensPerPage, poolLimit, source),
				() -> ScheduleJob.execute(itensPerPage, poolLimit, source),
				() -> WorkShiftJob.execute(itensPerPage, poolLimit, source),
				() -> NotificationRelatedJob.execute(itensPerPage, poolLimit, source),
		};

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
