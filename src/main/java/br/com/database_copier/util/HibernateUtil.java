package br.com.database_copier.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.database_copier.entities.Account;
import br.com.database_copier.entities.AccountCode;
import br.com.database_copier.entities.BankData;
import br.com.database_copier.entities.BenefitsApplicationForm;
import br.com.database_copier.entities.BenefitsApplicationFormQuestionsAndAnswers;
import br.com.database_copier.entities.Branch;
import br.com.database_copier.entities.Category;
import br.com.database_copier.entities.Client;
import br.com.database_copier.entities.ClientAddress;
import br.com.database_copier.entities.ClientEmail;
import br.com.database_copier.entities.ClientPhone;
import br.com.database_copier.entities.Company;
import br.com.database_copier.entities.CompanyAdditionalBenefit;
import br.com.database_copier.entities.CompanyAddress;
import br.com.database_copier.entities.CompanyAttachment;
import br.com.database_copier.entities.CompanyElegiblePatient;
import br.com.database_copier.entities.CompanyEmail;
import br.com.database_copier.entities.CompanyHealthPlan;
import br.com.database_copier.entities.CompanyManager;
import br.com.database_copier.entities.CompanyManagerEmail;
import br.com.database_copier.entities.CompanyManagerPhone;
import br.com.database_copier.entities.CompanyPhone;
import br.com.database_copier.entities.CompanyProduct;
import br.com.database_copier.entities.CostCenter;
import br.com.database_copier.entities.CustomerSurvey;
import br.com.database_copier.entities.Event;
import br.com.database_copier.entities.Expense;
import br.com.database_copier.entities.Occupation;
import br.com.database_copier.entities.Patient;
import br.com.database_copier.entities.PatientAddress;
import br.com.database_copier.entities.PatientAttachment;
import br.com.database_copier.entities.PatientCase;
import br.com.database_copier.entities.PatientCaseAttachment;
import br.com.database_copier.entities.PatientCaseEvolution;
import br.com.database_copier.entities.PatientCaseForwarding;
import br.com.database_copier.entities.PatientCaseForwardingAttendance;
import br.com.database_copier.entities.PatientCompany;
import br.com.database_copier.entities.PatientEmail;
import br.com.database_copier.entities.PatientFamilyGroup;
import br.com.database_copier.entities.PatientImportNaturaData;
import br.com.database_copier.entities.PatientPhone;
import br.com.database_copier.entities.Provider;
import br.com.database_copier.entities.ProviderAdditionalCourse;
import br.com.database_copier.entities.ProviderAddress;
import br.com.database_copier.entities.ProviderAttachment;
import br.com.database_copier.entities.ProviderEmail;
import br.com.database_copier.entities.ProviderFormation;
import br.com.database_copier.entities.ProviderHealthPlan;
import br.com.database_copier.entities.ProviderLanguage;
import br.com.database_copier.entities.ProviderPayment;
import br.com.database_copier.entities.ProviderPaymentItem;
import br.com.database_copier.entities.ProviderPhone;
import br.com.database_copier.entities.ProviderServiceModel;
import br.com.database_copier.entities.ProviderServiceModelAvailability;
import br.com.database_copier.entities.QuestionsAndAnswers;
import br.com.database_copier.entities.Revenue;
import br.com.database_copier.entities.Schedule;
import br.com.database_copier.entities.SessionReview;
import br.com.database_copier.entities.SpreadsheetImport;
import br.com.database_copier.entities.Subarea;
import br.com.database_copier.entities.Supplier;
import br.com.database_copier.entities.SupplierAddress;
import br.com.database_copier.entities.SupplierEmail;
import br.com.database_copier.entities.SupplierPhone;
import br.com.database_copier.entities.TroubleArea;
import br.com.database_copier.entities.TroubleSubtype;
import br.com.database_copier.entities.TroubleType;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	static {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Logger.getLogger("org.hibernate.annotations.common.reflection.java").setLevel(Level.SEVERE);
		Logger.getLogger("org.hibernate.Version").setLevel(Level.SEVERE);
		Logger.getLogger("org.hibernate.cfg").setLevel(Level.SEVERE);
		Logger.getLogger("org.hibernate.engine.jdbc.spi").setLevel(Level.SEVERE);
		Logger.getLogger("org.hibernate.engine.transaction.internal").setLevel(Level.SEVERE);
		Logger.getLogger("org.hibernate.type").setLevel(Level.SEVERE);
	}

	private static SessionFactory buildSessionFactoryTargetDatabase() {
		try {
			final Configuration configuration = new Configuration();
			configuration.configure("targetDatabase.cfg.xml");

			configuration.addAnnotatedClass(Account.class);
			configuration.addAnnotatedClass(AccountCode.class);
			configuration.addAnnotatedClass(BankData.class);
			configuration.addAnnotatedClass(BenefitsApplicationForm.class);
			configuration.addAnnotatedClass(BenefitsApplicationFormQuestionsAndAnswers.class);
			configuration.addAnnotatedClass(Branch.class);
			configuration.addAnnotatedClass(Category.class);
			configuration.addAnnotatedClass(Client.class);
			configuration.addAnnotatedClass(ClientAddress.class);
			configuration.addAnnotatedClass(ClientEmail.class);
			configuration.addAnnotatedClass(ClientPhone.class);
			configuration.addAnnotatedClass(Company.class);
			configuration.addAnnotatedClass(CompanyAdditionalBenefit.class);
			configuration.addAnnotatedClass(CompanyAddress.class);
			configuration.addAnnotatedClass(CompanyAttachment.class);
			configuration.addAnnotatedClass(CompanyElegiblePatient.class);
			configuration.addAnnotatedClass(CompanyEmail.class);
			configuration.addAnnotatedClass(CompanyHealthPlan.class);
			configuration.addAnnotatedClass(CompanyManager.class);
			configuration.addAnnotatedClass(CompanyManagerEmail.class);
			configuration.addAnnotatedClass(CompanyManagerPhone.class);
			configuration.addAnnotatedClass(CompanyPhone.class);
			configuration.addAnnotatedClass(CompanyProduct.class);
			configuration.addAnnotatedClass(CostCenter.class);
			configuration.addAnnotatedClass(CustomerSurvey.class);
			configuration.addAnnotatedClass(Event.class);
			configuration.addAnnotatedClass(Expense.class);
			configuration.addAnnotatedClass(br.com.database_copier.entities.Level.class);
			configuration.addAnnotatedClass(Occupation.class);
			configuration.addAnnotatedClass(Patient.class);
			configuration.addAnnotatedClass(PatientAddress.class);
			configuration.addAnnotatedClass(PatientAttachment.class);
			configuration.addAnnotatedClass(PatientCase.class);
			configuration.addAnnotatedClass(PatientCaseAttachment.class);
			configuration.addAnnotatedClass(PatientCaseEvolution.class);
			configuration.addAnnotatedClass(PatientCaseForwarding.class);
			configuration.addAnnotatedClass(PatientCaseForwardingAttendance.class);
			configuration.addAnnotatedClass(PatientCompany.class);
			configuration.addAnnotatedClass(PatientEmail.class);
			configuration.addAnnotatedClass(PatientFamilyGroup.class);
			configuration.addAnnotatedClass(PatientImportNaturaData.class);
			configuration.addAnnotatedClass(PatientPhone.class);
			configuration.addAnnotatedClass(Provider.class);
			configuration.addAnnotatedClass(ProviderAdditionalCourse.class);
			configuration.addAnnotatedClass(ProviderAddress.class);
			configuration.addAnnotatedClass(ProviderAttachment.class);
			configuration.addAnnotatedClass(ProviderEmail.class);
			configuration.addAnnotatedClass(ProviderFormation.class);
			configuration.addAnnotatedClass(ProviderHealthPlan.class);
			configuration.addAnnotatedClass(ProviderLanguage.class);
			configuration.addAnnotatedClass(ProviderPayment.class);
			configuration.addAnnotatedClass(ProviderPaymentItem.class);
			configuration.addAnnotatedClass(ProviderPhone.class);
			configuration.addAnnotatedClass(ProviderServiceModel.class);
			configuration.addAnnotatedClass(ProviderServiceModelAvailability.class);
			configuration.addAnnotatedClass(QuestionsAndAnswers.class);
			configuration.addAnnotatedClass(Revenue.class);
			configuration.addAnnotatedClass(Schedule.class);
			configuration.addAnnotatedClass(SessionReview.class);
			configuration.addAnnotatedClass(SpreadsheetImport.class);
			configuration.addAnnotatedClass(Subarea.class);
			configuration.addAnnotatedClass(Supplier.class);
			configuration.addAnnotatedClass(SupplierAddress.class);
			configuration.addAnnotatedClass(SupplierEmail.class);
			configuration.addAnnotatedClass(SupplierPhone.class);
			configuration.addAnnotatedClass(TroubleArea.class);
			configuration.addAnnotatedClass(TroubleSubtype.class);
			configuration.addAnnotatedClass(TroubleType.class);

//			<mapping class="br.com.database_copier.entities.Account" />
//			<mapping class="br.com.database_copier.entities.AccountCode" />
//			<mapping class="br.com.database_copier.entities.BankData" />
//			<mapping class="br.com.database_copier.entities.BenefitsApplicationForm" />
//			<mapping
//				class="br.com.database_copier.entities.BenefitsApplicationFormQuestionsAndAnswers" />
//			<mapping class="br.com.database_copier.entities.Branch" />
//			<mapping class="br.com.database_copier.entities.Category" />
//			<mapping class="br.com.database_copier.entities.Client" />
//			<mapping class="br.com.database_copier.entities.ClientAddress" />
//			<mapping class="br.com.database_copier.entities.ClientEmail" />
//			<mapping class="br.com.database_copier.entities.ClientPhone" />
//			<mapping class="br.com.database_copier.entities.Company" />
//			<mapping
//				class="br.com.database_copier.entities.CompanyAdditionalBenefit" />
//			<mapping class="br.com.database_copier.entities.CompanyAddress" />
//			<mapping class="br.com.database_copier.entities.CompanyAttachment" />
//			<mapping class="br.com.database_copier.entities.CompanyElegiblePatient" />
//			<mapping class="br.com.database_copier.entities.CompanyEmail" />
//			<mapping class="br.com.database_copier.entities.CompanyHealthPlan" />
//			<mapping class="br.com.database_copier.entities.CompanyManager" />
//			<mapping class="br.com.database_copier.entities.CompanyManagerEmail" />
//			<mapping class="br.com.database_copier.entities.CompanyManagerPhone" />
//			<mapping class="br.com.database_copier.entities.CompanyPhone" />
//			<mapping class="br.com.database_copier.entities.CompanyProduct" />
//			<mapping class="br.com.database_copier.entities.CostCenter" />
//			<mapping class="br.com.database_copier.entities.CustomerSurvey" />
//			<mapping class="br.com.database_copier.entities.Event" />
//			<mapping class="br.com.database_copier.entities.Expense" />
//			<mapping class="br.com.database_copier.entities.Level" />
//			<mapping class="br.com.database_copier.entities.Occupation" />
//			<mapping class="br.com.database_copier.entities.Patient" />
//			<mapping class="br.com.database_copier.entities.PatientAddress" />
//			<mapping class="br.com.database_copier.entities.PatientAttachment" />
//			<mapping class="br.com.database_copier.entities.PatientCase" />
//			<mapping class="br.com.database_copier.entities.PatientCaseAttachment" />
//			<mapping class="br.com.database_copier.entities.PatientCaseEvolution" />
//			<mapping class="br.com.database_copier.entities.PatientCaseForwarding" />
//			<mapping
//				class="br.com.database_copier.entities.PatientCaseForwardingAttendance" />
//			<mapping class="br.com.database_copier.entities.PatientCompany" />
//			<mapping class="br.com.database_copier.entities.PatientEmail" />
//			<mapping class="br.com.database_copier.entities.PatientFamilyGroup" />
//			<mapping class="br.com.database_copier.entities.PatientImportNaturaData" />
//			<mapping class="br.com.database_copier.entities.PatientPhone" />
//			<mapping class="br.com.database_copier.entities.Provider" />
//			<mapping
//				class="br.com.database_copier.entities.ProviderAdditionalCourse" />
//			<mapping class="br.com.database_copier.entities.ProviderAddress" />
//			<mapping class="br.com.database_copier.entities.ProviderAttachment" />
//			<mapping class="br.com.database_copier.entities.ProviderEmail" />
//			<mapping class="br.com.database_copier.entities.ProviderFormation" />
//			<mapping class="br.com.database_copier.entities.ProviderHealthPlan" />
//			<mapping class="br.com.database_copier.entities.ProviderLanguage" />
//			<mapping class="br.com.database_copier.entities.ProviderPayment" />
//			<mapping class="br.com.database_copier.entities.ProviderPaymentItem" />
//			<mapping class="br.com.database_copier.entities.ProviderPhone" />
//			<mapping class="br.com.database_copier.entities.ProviderServiceModel" />
//			<mapping
//				class="br.com.database_copier.entities.ProviderServiceModelAvailability" />
//			<mapping class="br.com.database_copier.entities.QuestionsAndAnswers" />
//			<mapping class="br.com.database_copier.entities.Revenue" />
//			<mapping class="br.com.database_copier.entities.Schedule" />
//			<mapping class="br.com.database_copier.entities.SessionReview" />
//			<mapping class="br.com.database_copier.entities.SpreadsheetImport" />
//			<mapping class="br.com.database_copier.entities.Subarea" />
//			<mapping class="br.com.database_copier.entities.Supplier" />
//			<mapping class="br.com.database_copier.entities.SupplierAddress" />
//			<mapping class="br.com.database_copier.entities.SupplierEmail" />
//			<mapping class="br.com.database_copier.entities.SupplierPhone" />
//			<mapping class="br.com.database_copier.entities.TroubleArea" />
//			<mapping class="br.com.database_copier.entities.TroubleSubtype" />
//			<mapping class="br.com.database_copier.entities.TroubleType" />

			final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			final SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static SessionFactory buildSessionFactorySourceDatabase() {
		try {
			final Configuration configuration = new Configuration();
			configuration.configure("sourceDatabase.cfg.xml");
			final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			final SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory startSessionFactoryTargetDatabase() {
		sessionFactory = buildSessionFactoryTargetDatabase();
		return sessionFactory;
	}

	public static SessionFactory startSessionFactorySourceDatabase() {
		sessionFactory = buildSessionFactorySourceDatabase();
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}