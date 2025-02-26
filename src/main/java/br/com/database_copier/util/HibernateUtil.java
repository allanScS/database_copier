package br.com.database_copier.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.database_copier.entities.Account;
import br.com.database_copier.entities.AccountCode;
import br.com.database_copier.entities.Address;
import br.com.database_copier.entities.Call;
import br.com.database_copier.entities.Card;
import br.com.database_copier.entities.Channel;
import br.com.database_copier.entities.Company;
import br.com.database_copier.entities.DataIntegration;
import br.com.database_copier.entities.Device;
import br.com.database_copier.entities.DeviceNotification;
import br.com.database_copier.entities.Evaluation;
import br.com.database_copier.entities.HealthCondition;
import br.com.database_copier.entities.Hospitalization;
import br.com.database_copier.entities.Insurance;
import br.com.database_copier.entities.Invitation;
import br.com.database_copier.entities.MassMessage;
import br.com.database_copier.entities.MedicalAppointment;
import br.com.database_copier.entities.Message;
import br.com.database_copier.entities.MyHealth;
import br.com.database_copier.entities.News;
import br.com.database_copier.entities.Notification;
import br.com.database_copier.entities.Prescription;
import br.com.database_copier.entities.Profile;
import br.com.database_copier.entities.Provider;
import br.com.database_copier.entities.ProviderInsurance;
import br.com.database_copier.entities.RefreshToken;
import br.com.database_copier.entities.Related;
import br.com.database_copier.entities.Review;
import br.com.database_copier.entities.Schedule;
import br.com.database_copier.entities.WorkShift;

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
			configuration.addAnnotatedClass(Address.class);
			configuration.addAnnotatedClass(Call.class);
			configuration.addAnnotatedClass(Card.class);
			configuration.addAnnotatedClass(Channel.class);
			configuration.addAnnotatedClass(Company.class);
			configuration.addAnnotatedClass(DataIntegration.class);
			configuration.addAnnotatedClass(Device.class);
			configuration.addAnnotatedClass(DeviceNotification.class);
			configuration.addAnnotatedClass(Evaluation.class);
			configuration.addAnnotatedClass(HealthCondition.class);
			configuration.addAnnotatedClass(Hospitalization.class);
			configuration.addAnnotatedClass(Insurance.class);
			configuration.addAnnotatedClass(Invitation.class);
			configuration.addAnnotatedClass(MassMessage.class);
			configuration.addAnnotatedClass(MedicalAppointment.class);
			configuration.addAnnotatedClass(Message.class);
			configuration.addAnnotatedClass(MyHealth.class);
			configuration.addAnnotatedClass(News.class);
			configuration.addAnnotatedClass(Notification.class);
			configuration.addAnnotatedClass(Prescription.class);
			configuration.addAnnotatedClass(Profile.class);
			configuration.addAnnotatedClass(Provider.class);
			configuration.addAnnotatedClass(RefreshToken.class);
			configuration.addAnnotatedClass(Related.class);
			configuration.addAnnotatedClass(Review.class);
			configuration.addAnnotatedClass(Schedule.class);
			configuration.addAnnotatedClass(WorkShift.class);
			configuration.addAnnotatedClass(ProviderInsurance.class);

			configuration.setProperty("hibernate.c3p0.min_size", "5");
			configuration.setProperty("hibernate.c3p0.max_size", "200"); // Defina o número máximo de conexões
			configuration.setProperty("hibernate.c3p0.timeout", "3000"); // Timeout para inatividade de 300 segundos (5
																			// minutos)
			configuration.setProperty("hibernate.c3p0.max_statements", "500");
			configuration.setProperty("hibernate.c3p0.idle_test_period", "300");
			configuration.setProperty("hibernate.c3p0.acquire_increment", "5"); // Quantas conexões o pool irá adquirir
																				// de uma vez
			configuration.setProperty("hibernate.c3p0.max_idle_time", "600"); // Tempo máximo de inatividade antes de
																				// desconectar a conexão

			configuration.setProperty("hibernate.hikari.minimumIdle", "5"); // Mínimo de conexões inativas
			configuration.setProperty("hibernate.hikari.maximumPoolSize", "200"); // Máximo de conexões no pool
			configuration.setProperty("hibernate.hikari.idleTimeout", "300000"); // Tempo limite para conexões ociosas
																					// (em milissegundos)
			configuration.setProperty("hibernate.hikari.connectionTimeout", "30000"); // Timeout para tentar uma nova
																						// conexão
			configuration.setProperty("hibernate.hikari.maxLifetime", "600000"); // Máxima duração de uma conexão no
																					// pool (em milissegundos)

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