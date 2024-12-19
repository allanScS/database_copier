package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.PatientCaseForwardingAttendance;
import br.com.database_copier.util.GenericUtils;

public class PatientCaseForwardingAttendanceJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolLimit);

		final String sourceTable = "patient_case_forwarding_attendance";
		final String targetTable = "patientCaseForwardingAttendance";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "annotation", "effectuated_at", "no_show", "sent_for_payment", "value",
				"patient_case_forwarding_id", "provider_payment_closure_id", "provider_service_model_id",
				"service_model_type", "support_type", "attendance_hours", "attendance_ended_at" };

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
							PatientCaseForwardingAttendance.class);
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