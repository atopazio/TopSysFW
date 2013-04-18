package br.com.topsys.util;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public final class TSHibernateUtil {

	private static SessionFactory sessionFactory;
	
	static{
		sessionFactory = buildSessionFactory();
	}

	private static SessionFactory buildSessionFactory() {
		try {

			return new Configuration().configure().buildSessionFactory();
	

		} catch (Throwable ex) {

			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	//public static Session getCurrentSession() {
	//	return getSessionFactory().getCurrentSession();
	//}

	public static Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	public static Session getSession(String configuracao){
		SessionFactory factory = new Configuration().configure(configuracao).buildSessionFactory();
		return factory.openSession();
	}

}
