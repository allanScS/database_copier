package br.com.database_copier;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.Session;

import br.com.database_copier.jobs.PatientJob;
import br.com.database_copier.util.HibernateUtil;

public class DatabaseCopierApplication {

	public static void main(String[] args) {
		int itensPerPage = 3000;
		int poolLimit = 50;

		final LocalDateTime start = LocalDateTime.now();

		Session source = HibernateUtil.startSessionFactorySourceDatabase().openSession();

		System.out.printf("\nIMPORTANDO PACIENTES\n");

		PatientJob.execute(itensPerPage, poolLimit, source);

		System.out.println("\nLimpando cache do Hibernate...");

		source.clear();

		System.out.println("Executando Garbage Collector...");
		System.gc();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		final Duration duration = Duration.between(start, LocalDateTime.now());
		System.out.printf("Duração total: %d horas, %d minutos, %d segundos\n", duration.toHours(),
				duration.toMinutesPart(), duration.toSecondsPart());
	}
}
