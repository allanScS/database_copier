package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.Schedule;
import br.com.database_copier.util.ExecutePageUtil;
import br.com.database_copier.util.GenericUtils;

public class ScheduleJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(poolLimit, poolLimit, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), runnable -> {
					Thread t = new Thread(runnable);
					t.setDaemon(false);
					t.setName("CustomPool-" + t.getId());
					return t;
				});

		final String sourceTable = "schedule";
		final String targetTable = "schedule";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "description", "end_date_time", "schedule_type", "start_date_time", "account_id",
				"patient_case_id" };

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
							Schedule.class);

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