package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.TroubleType;
import br.com.database_copier.util.GenericUtils;

public class TroubleTypeJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolLimit);

		final String sourceTable = "trouble_Type";
		final String targetTable = "troubleType";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "active", "description", "trouble_area_id" };

		final BigInteger totalElements = (BigInteger) source
				.createSQLQuery(
						"SELECT COUNT(entity.id) FROM " + GenericUtils.SOURCE_SCHEMA + "." + sourceTable + " AS entity")
				.uniqueResult();

		int totalPages = (totalElements.intValue() + itensPerPage - 1) / itensPerPage;
		int page = 0;

		while (page + 1 <= totalPages) {

			final int page2 = page;

			threadPool.execute(() -> {

				try {
					GenericUtils.executePage(fields, sourceTable, targetTable, itensPerPage, page2, totalPages, source,
							TroubleType.class);
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