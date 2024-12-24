package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.Company;
import br.com.database_copier.util.ExecutePageUtil;
import br.com.database_copier.util.GenericUtils;

public class CompanyJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(poolLimit, poolLimit, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), runnable -> {
					Thread t = new Thread(runnable);
					t.setDaemon(false);
					t.setName("CustomPool-" + t.getId());
					return t;
				});

		final String sourceTable = "company";
		final String targetTable = "company";

		final String[] fields = { "id", "active", "brand_color", "card_back_image_url", "card_front_image_url",
				"central_phone", "created_at", "created_by", "employees_number", "logo_image_url", "name", "updated_at",
				"updated_by", "tax_number", "deleted" };

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
							totalPages, source, Company.class);

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