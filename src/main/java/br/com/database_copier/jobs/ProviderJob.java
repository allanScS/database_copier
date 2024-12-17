package br.com.database_copier.jobs;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;

import br.com.database_copier.entities.Provider;
import br.com.database_copier.util.GenericUtils;

public class ProviderJob {

	public static void execute(final Integer itensPerPage, final Integer poolLimit, final Session source) {

		final ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolLimit);

		final String sourceTable = "provider";
		final String targetTable = "provider";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "account_digit", "account_number", "accreditation_date", "active", "agency", "bank",
				"bank_code", "birth_date", "certificate_number", "corporate_name", "corporate_tax_number", "gender",
				"image_url", "marital_status", "name", "pix_key", "provider_status", "registration", "social_name",
				"supplier", "tags", "tax_number", "occupation_id", "ethnicity_enum", "account_id", "is_corporate",
				"supplier_id", "classification" };

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
							Provider.class);
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