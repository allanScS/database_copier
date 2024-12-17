package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.BankData;
import br.com.database_copier.entities.Patient;
import br.com.database_copier.entities.PatientImportNaturaData;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class PatientJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		String table = "patient";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "active", "birth_date", "formation_status", "gender", "image_url", "marital_status",
				"name", "social_name", "tax_number", "occupation", "branch_names", "client_names", "companie_names",
				"dependents_quantity", "identified_by_tax_number", "registration_numbers", "relationships",
				"import_natura_data_id", "attendances_size", "cases_size", "bank_data_id" };

		Boolean hasMoreElements = Boolean.TRUE;

		int page = 0;

		while (hasMoreElements) {

			System.out.println("BUSCANDO A PAGINA: " + page);

			final String query = GenericUtils.buildSql(fields, table, GenericUtils.SOURCE_SCHEMA, itensPerPage, page);

			System.out.println("Iniciando seção com origem");

			Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
			session.beginTransaction();
			System.out.println("Seção iniciada com origem");

			final List<Object[]> list = session.createSQLQuery(query).list();

			if (list.isEmpty()) {
				hasMoreElements = Boolean.FALSE;
				break;
			}

			final AbstractConverter<Patient> converter = new AbstractConverter<Patient>()
					.convertjsonToEntityList(Patient.class, GenericUtils.objectsToJson(list, fields));

			final List<Patient> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final Patient entity = entityList.get(i);

				if (entity.getImportNaturaDataId() != null) {
					entity.setImportNaturaData(new PatientImportNaturaData());
					entity.getImportNaturaData().setId(entity.getImportNaturaDataId());
				}

				if (entity.getBankDataId() != null) {
					entity.setBankData(new BankData());
					entity.getBankData().setId(entity.getBankDataId());
				}

				if (i % 3000 == 0) {
					insertSession.getTransaction().commit();
					insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
					insertSession.beginTransaction();
				}

				final String result = (String) insertSession
						.createSQLQuery(
								"SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + table + " WHERE id = :id")
						.setParameter("id", entity.getId()).uniqueResult();

				if (result == null) {
					insertSession.save(entity);
					System.out.println("Inserted MAP: " + entity.getId() + " : " + table);
				}
			}

			if (!insertSession.getTransaction().wasCommitted()) {
				insertSession.getTransaction().commit();
			}

			System.out.println("Transaction commited and Session closed");
			System.out.println(i + " new data inserted");
			page++;
		}
	}
}