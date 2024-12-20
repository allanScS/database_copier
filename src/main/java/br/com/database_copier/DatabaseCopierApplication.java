package br.com.database_copier;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.Session;

import br.com.database_copier.jobs.AccountCodeJob;
import br.com.database_copier.jobs.AccountJob;
import br.com.database_copier.jobs.AddressJob;
import br.com.database_copier.jobs.ProfileJob;
import br.com.database_copier.util.HibernateUtil;

public class DatabaseCopierApplication {

	public static void main(String[] args) {
		int itensPerPage = 3000;
		int poolLimit = 50;

		final LocalDateTime start = LocalDateTime.now();

		Session source = HibernateUtil.startSessionFactorySourceDatabase().openSession();

		final Runnable[] jobs = { () -> ProfileJob.execute(itensPerPage, poolLimit, source),
				() -> AddressJob.execute(itensPerPage, poolLimit, source),
				() -> AccountJob.execute(itensPerPage, poolLimit, source),
				() -> AccountCodeJob.execute(itensPerPage, poolLimit, source),
//				() -> AccountCodeJob.execute(itensPerPage, poolLimit, source),
//				() -> BankDataJob.execute(itensPerPage, poolLimit, source),
//				() -> ClientJob.execute(itensPerPage, poolLimit, source),
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
