package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.DataIntegration;
import br.com.database_copier.util.ExecutePageUtil;
import br.com.database_copier.util.GenericUtils;

public class DataIntegrationJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(poolLimit, poolLimit, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), runnable -> {
					Thread t = new Thread(runnable);
					t.setDaemon(false);
					t.setName("CustomPool-" + t.getId());
					return t;
				});

		final String sourceTable = "data_integration";
		final String targetTable = "dataIntegration";

		final String[] fields = { "id", "attempts", "beneficiary_id", "created_at", "created_by", "data_created_id",
				"data_reference", "data_reference_id", "deleted", "deleted_at", "deleted_by", "message",
				"response_message", "status", "technical_guidance_id", "updated_at", "updated_by", "account_id" };

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

					executePageUtil.executePage(fields.clone(), sourceTable, targetTable, itensPerPage, page2,
							totalPages, source, DataIntegration.class);

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