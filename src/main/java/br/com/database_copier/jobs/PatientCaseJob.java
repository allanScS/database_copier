package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.PatientCase;
import br.com.database_copier.util.ExecutePageUtil;
import br.com.database_copier.util.GenericUtils;

public class PatientCaseJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolLimit);

		final String sourceTable = "patient_case";
		final String targetTable = "patientCase";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "case_number", "concluded", "concluded_at", "concluded_by", "description", "notifier",
				"patient_availability", "priority", "problem_holder", "product", "patient_id",
				"responsible_for_the_case_id", "trouble_area_id", "trouble_subtype_id", "trouble_type_id",
				"opening_date", "patient_company_id", "call_type", "original_case_number", "origin",
				"carelink_sessions", "provider_sessions", "participate_satisfaction_survey", "reason_for_work_leave",
				"work_leave_end_date", "work_leave_start_date" };
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

					ExecutePageUtil executePageUtil = new ExecutePageUtil();

					executePageUtil.executePage(fields, sourceTable, targetTable, itensPerPage, page2, totalPages, source,
							PatientCase.class);

					executePageUtil = null;

					System.gc();

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