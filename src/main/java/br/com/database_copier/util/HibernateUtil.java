package br.com.database_copier.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	// XML based configuration
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