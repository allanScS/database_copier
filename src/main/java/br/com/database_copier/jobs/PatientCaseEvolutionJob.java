package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.PatientCaseEvolution;
import br.com.database_copier.util.GenericUtils;

public class PatientCaseEvolutionJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolLimit);

		final String sourceTable = "patient_case_evolution";
		final String targetTable = "patientCaseEvolution";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "description", "evolution_date", "evolution_type", "receptive", "service_channel_type",
				"patient_case_id", "patient_case_forwarding_attendance_id", "service_model_type", "support_type",
				"provider_id", "created_by_account_name", "attendance_hours", "no_show" };

		final BigInteger totalElements = (BigInteger) source
				.createNativeQuery(
						"SELECT COUNT(entity.id) FROM " + GenericUtils.SOURCE_SCHEMA + "." + sourceTable + " AS entity")
				.uniqueResult();

		int totalPages = (totalElements.intValue() + itensPerPage - 1) / itensPerPage;
		int page = 0;

		while (page + 1 <= totalPages) {

			final int page2 = page;

			threadPool.execute(() -> {

				try {
					GenericUtils.executePage(fields, sourceTable, targetTable, itensPerPage, page2, totalPages, source,
							PatientCaseEvolution.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});

			page++;
		}

		threadPool.shutdown();

		try {
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.out.println("ERRO :" + e.getMessage());
		}

	}

}