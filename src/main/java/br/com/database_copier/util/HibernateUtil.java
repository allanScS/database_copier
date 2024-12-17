package br.com.database_copier.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
		//XML based configuration
		private static SessionFactory sessionFactory;
		
	    private static SessionFactory buildSessionFactoryTargetDatabase() {
	        try {
	        	Configuration configuration = new Configuration();
	        	configuration.configure("targetDatabase.cfg.xml");
	        	System.out.println("Hibernate Configuration loaded");
	        	
	        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	        	System.out.println("Hibernate serviceRegistry created");
	        	
	        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	        	
	            return sessionFactory;
	        }
	        catch (Throwable ex) {
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	    
	    private static SessionFactory buildSessionFactorySourceDatabase() {
	    	try {
	    		Configuration configuration = new Configuration();
	    		configuration.configure("sourceDatabase.cfg.xml");
	    		System.out.println("Hibernate Configuration loaded");
	    		
	    		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	    		System.out.println("Hibernate serviceRegistry created");
	    		
	    		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    		
	    		return sessionFactory;
	    	}
	    	catch (Throwable ex) {
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