package br.com.database_copier.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.database_copier.entities.Patient;

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

			configuration.addAnnotatedClass(Patient.class);
			
			configuration.setProperty("hibernate.c3p0.min_size", "5");
			configuration.setProperty("hibernate.c3p0.max_size", "200"); // Defina o número máximo de conexões
			configuration.setProperty("hibernate.c3p0.timeout", "3000"); // Timeout para inatividade de 300 segundos (5 minutos)
			configuration.setProperty("hibernate.c3p0.max_statements", "500");
			configuration.setProperty("hibernate.c3p0.idle_test_period", "300");
			configuration.setProperty("hibernate.c3p0.acquire_increment", "5"); // Quantas conexões o pool irá adquirir de uma vez
			configuration.setProperty("hibernate.c3p0.max_idle_time", "600"); // Tempo máximo de inatividade antes de desconectar a conexão
			
			configuration.setProperty("hibernate.hikari.minimumIdle", "5");  // Mínimo de conexões inativas
			configuration.setProperty("hibernate.hikari.maximumPoolSize", "200");  // Máximo de conexões no pool
			configuration.setProperty("hibernate.hikari.idleTimeout", "300000"); // Tempo limite para conexões ociosas (em milissegundos)
			configuration.setProperty("hibernate.hikari.connectionTimeout", "30000"); // Timeout para tentar uma nova conexão
			configuration.setProperty("hibernate.hikari.maxLifetime", "600000");  // Máxima duração de uma conexão no pool (em milissegundos)

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