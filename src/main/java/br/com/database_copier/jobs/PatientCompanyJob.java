package br.com.database_copier.jobs;

import java.util.List;

import org.hibernate.Session;

import br.com.database_copier.entities.Branch;
import br.com.database_copier.entities.Company;
import br.com.database_copier.entities.CompanyHealthPlan;
import br.com.database_copier.entities.CostCenter;
import br.com.database_copier.entities.Level;
import br.com.database_copier.entities.Patient;
import br.com.database_copier.entities.PatientCompany;
import br.com.database_copier.entities.Subarea;
import br.com.database_copier.util.GenericUtils;
import br.com.database_copier.util.HibernateUtil;
import br.com.neoapp.base.AbstractConverter;

public class PatientCompanyJob {

	@SuppressWarnings("unchecked")
	public static void execute(Integer itensPerPage) {

		System.out.println("Montando query");

		final String sourceTable = "patient_company";
		final String targetTable = "patientCompany";

		final String[] fields = { "id", "created_at", "created_by", "deleted", "deleted_at", "deleted_by", "updated_at",
				"updated_by", "holder", "registration_number", "company_id", "company_health_plan_id", "patient_id",
				"date_of_admission", "branch_id", "subarea_id", "active", "relationship", "import_code",
				"cost_center_id", "level_id", "last_work_leave_date" };

		Boolean hasMoreElements = Boolean.TRUE;

		int page = 0;

		while (hasMoreElements) {

			System.out.println("BUSCANDO A PAGINA: " + page);

			final String query = GenericUtils.buildSql(fields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage,
					page);

			System.out.println("Iniciando seção com origem");

			Session session = HibernateUtil.startSessionFactorySourceDatabase().getCurrentSession();
			session.beginTransaction();
			System.out.println("Seção iniciada com origem");

			final List<Object[]> list = session.createSQLQuery(query).list();

			if (list.isEmpty()) {
				hasMoreElements = Boolean.FALSE;
				break;
			}

			final AbstractConverter<PatientCompany> converter = new AbstractConverter<PatientCompany>()
					.convertjsonToEntityList(PatientCompany.class, GenericUtils.objectsToJson(list, fields));

			final List<PatientCompany> entityList = converter.getEntities();

			System.out.println("Lista de dados criada");

			session.close();
			System.out.println("Transaction commited and Session closed");

			Session insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
			insertSession.beginTransaction();
			System.out.println("First database transaction started");
			int i = 0;

			for (i = 0; i < entityList.size(); i++) {

				final PatientCompany entity = entityList.get(i);

				if (entity.getCompanyId() != null) {
					entity.setCompany(new Company());
					entity.getCompany().setId(entity.getCompanyId());
				}

				if (entity.getPatientId() != null) {
					entity.setPatient(new Patient());
					entity.getPatient().setId(entity.getPatientId());
				}

				if (entity.getCompanyHealthPlanId() != null) {
					entity.setCompanyHealthPlan(new CompanyHealthPlan());
					entity.getCompanyHealthPlan().setId(entity.getCompanyHealthPlanId());
				}

				if (entity.getBranchId() != null) {
					entity.setBranch(new Branch());
					entity.getBranch().setId(entity.getBranchId());
				}

				if (entity.getSubareaId() != null) {
					entity.setSubarea(new Subarea());
					entity.getSubarea().setId(entity.getSubareaId());
				}

				if (entity.getLevelId() != null) {
					entity.setLevel(new Level());
					entity.getLevel().setId(entity.getLevelId());
				}

				if (entity.getCostCenterId() != null) {
					entity.setCostCenter(new CostCenter());
					entity.getCostCenter().setId(entity.getCostCenterId());
				}

				if (i % 3000 == 0) {
					insertSession.getTransaction().commit();
					insertSession = HibernateUtil.startSessionFactoryTargetDatabase().getCurrentSession();
					insertSession.beginTransaction();
				}

				final String result = (String) insertSession
						.createSQLQuery(
								"SELECT id FROM " + GenericUtils.TARGET_SCHEMA + "." + targetTable + " WHERE id = :id")
						.setParameter("id", entity.getId()).uniqueResult();

				if (result == null) {
					insertSession.save(entity);
					System.out.println("Inserted MAP: " + entity.getId() + " : " + targetTable);
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