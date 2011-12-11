/*
 * Created on 25/05/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.topsys.util;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import br.com.topsys.exception.TSSystemException;



/**
 * @author andre
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * This class is an implementation of the Service Locator pattern. It is used to
 * looukup resources such as EJBHomes, JMS Destinations, etc. This
 * implementation uses the "singleton" strategy and also the "caching" strategy.
 * This implementation is intended to be used on the web tier and not on the ejb
 * tier.
 */
public final class TSServiceLocatorUtil {

	private InitialContext ic;

	private Map cache; //used to hold references to EJBHomes/JMS Resources for
					   // re-use

	private static TSServiceLocatorUtil me;

	@SuppressWarnings("unchecked")
	private TSServiceLocatorUtil(){
		try {
			ic = new InitialContext();
			cache = Collections.synchronizedMap(new HashMap());
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		}

	}

	/**
	 * 
	 * @return
	 * @throws br.com.topsys.exception.TSServiceLocatorException
	 */
	public static TSServiceLocatorUtil getInstance(){
		if (me == null) {
				
			me = new TSServiceLocatorUtil();
			
		}
		return me;
	}

	/**
	 * will get the ejb Local home factory. If this ejb home factory has already
	 * been clients need to cast to the type of EJBHome they desire
	 * 
	 * @return the EJB Home corresponding to the homeName
	 */
	@SuppressWarnings("unchecked")
	public EJBLocalHome getLocalHome(String jndiHomeName){
		EJBLocalHome home = null;
		try {
			if (cache.containsKey(jndiHomeName)) {
				home = (EJBLocalHome) cache.get(jndiHomeName);
			} else {
				home = (EJBLocalHome) ic.lookup(jndiHomeName);
				cache.put(jndiHomeName, home);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		}
		return home;
	}

	/**
	 * will get the ejb Remote home factory. If this ejb home factory has
	 * already been clients need to cast to the type of EJBHome they desire
	 * 
	 * @return the EJB Home corresponding to the homeName
	 */
	@SuppressWarnings("unchecked")
	public EJBHome getRemoteHome(String jndiHomeName, Class className){
		EJBHome home = null;
		try {
			if (cache.containsKey(jndiHomeName)) {
				home = (EJBHome) cache.get(jndiHomeName);
			} else {
				Object objref = ic.lookup(jndiHomeName);
				Object obj = PortableRemoteObject.narrow(objref, className);
				home = (EJBHome) obj;
				cache.put(jndiHomeName, home);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 

		return home;
	}

	/**
	 * @return the factory for the factory to get queue connections from
	 */
	@SuppressWarnings("unchecked")
	public QueueConnectionFactory getQueueConnectionFactory(
			String qConnFactoryName){
		QueueConnectionFactory factory = null;
		try {
			if (cache.containsKey(qConnFactoryName)) {
				factory = (QueueConnectionFactory) cache.get(qConnFactoryName);
			} else {
				factory = (QueueConnectionFactory) ic.lookup(qConnFactoryName);
				cache.put(qConnFactoryName, factory);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return factory;
	}

	/**
	 * @return the Queue Destination to send messages to

	 */
	@SuppressWarnings("unchecked")
	public Queue getQueue(String queueName){
		Queue queue = null;
		try {
			if (cache.containsKey(queueName)) {
				queue = (Queue) cache.get(queueName);
			} else {
				queue = (Queue) ic.lookup(queueName);
				cache.put(queueName, queue);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 

		return queue;
	}

	/**
	 * This method helps in obtaining the topic factory
	 * 
	 * @return the factory for the factory to get topic connections from
	 */
	@SuppressWarnings("unchecked")
	public TopicConnectionFactory getTopicConnectionFactory(
			String topicConnFactoryName){
		TopicConnectionFactory factory = null;
		try {
			if (cache.containsKey(topicConnFactoryName)) {
				factory = (TopicConnectionFactory) cache
						.get(topicConnFactoryName);
			} else {
				factory = (TopicConnectionFactory) ic
						.lookup(topicConnFactoryName);
				cache.put(topicConnFactoryName, factory);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return factory;
	}

	/**
	 * This method obtains the topc itself for a caller
	 * 
	 * @return the Topic Destination to send messages to
	 */
	@SuppressWarnings("unchecked")
	public Topic getTopic(String topicName){
		Topic topic = null;
		try {
			if (cache.containsKey(topicName)) {
				topic = (Topic) cache.get(topicName);
			} else {
				topic = (Topic) ic.lookup(topicName);
				cache.put(topicName, topic);
			}
		} catch (NamingException ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return topic;
	}

	/**
	 * This method obtains the datasource itself for a caller
	 * 
	 * @return the DataSource corresponding to the name parameter
	 */
	@SuppressWarnings("unchecked")
	public DataSource getDataSource(String dataSourceName) {
		DataSource dataSource = null;
		try {
			if (cache.containsKey(dataSourceName)) {
				dataSource = (DataSource) cache.get(dataSourceName);
			} else {
				dataSource = (DataSource) ic.lookup(dataSourceName);
				cache.put(dataSourceName, dataSource);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return dataSource;
	}
	
	public UserTransaction getUserTransaction(String tran) {
		UserTransaction transaction = null;
		try {
			if (cache.containsKey(tran)) {
				transaction = (UserTransaction) cache.get(tran);
			} else {
				transaction = (UserTransaction) ic.lookup(tran);
				cache.put(tran, transaction);
			}
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return transaction;
	}

	/**
	 * @return the URL value corresponding to the env entry name.
	 */
	public URL getUrl(String envName){
		URL url = null;
		try {
			url = (URL) ic.lookup(envName);
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return url;
	}

	/**
	 * @return the boolean value corresponding to the env entry such as
	 *         SEND_CONFIRMATION_MAIL property.
	 */
	public boolean getBoolean(String envName) {
		Boolean bool = null;
		try {
			bool = (Boolean) ic.lookup(envName);
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return bool.booleanValue();
	}

	/**
	 * @return the String value corresponding to the env entry name.
	 */
	public String getString(String envName) {
		String envEntry = null;
		try {
			envEntry = (String) ic.lookup(envName);
		} catch (Exception ne) {
			TSLogUtil.getInstance().severe(ne.getMessage());
			throw new TSSystemException(ne);
		} 
		return envEntry;
	}

}