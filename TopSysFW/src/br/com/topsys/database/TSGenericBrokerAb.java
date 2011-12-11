package br.com.topsys.database;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import br.com.topsys.exception.TSApplicationException;

public abstract class TSGenericBrokerAb<T, ID extends Serializable> {
 
	private TSPersistenceBrokerIf<T,ID> persistenceDAOIf=null;
	
	 
	 
	
	public TSGenericBrokerAb(TSPersistenceBrokerIf persistenceDAOIf){
		
		this.persistenceDAOIf = persistenceDAOIf;
	}
	
	public TSGenericBrokerAb(){
    
		//this.persistenceDAOIf= new TSHibernateBrokerAb<T,ID>();
	}
	
	public List<T> findByCriteria(Criterion... criterion) {
		return this.persistenceDAOIf.findByCriteria(criterion);
	}

	public List<T> find(String query) {
		return this.persistenceDAOIf.find(query);
	}

	public List<T> find(String query, Object... objects) {
		return this.persistenceDAOIf.find(query,objects);
	}

	public T get(String hql) {
		return (T) this.persistenceDAOIf.get(hql);
	}

	public T get(String hql, Object... objects) {
		return (T) this.persistenceDAOIf.get(hql,objects);
	}

	public T getNamedQuery(String hql, Object... objects) {
		return (T) this.persistenceDAOIf.getNamedQuery(hql,objects);
	}

	public Object getObject(String ejbql, Object... objects) {
		return this.persistenceDAOIf.getObject(ejbql,objects);
	}

	public Object getObjectNamedQuery(String ejbql, Object... objects) {
		return this.persistenceDAOIf.getObjectNamedQuery(ejbql,objects);
	}

	public List<T> findNamedQuery(String query, Object... objects) {
		return this.persistenceDAOIf.findNamedQuery(query,objects);
	}

	public int deleteNamedQuery(String name, Object... objects) throws TSApplicationException {
		return this.persistenceDAOIf.deleteNamedQuery(name,objects);
	}

	public int update(String hql, Object... objects) throws TSApplicationException {
		return this.persistenceDAOIf.update(hql,objects);
	}

	public int updateNamedQuery(String hql, Object... objects) throws TSApplicationException {
		return this.persistenceDAOIf.updateNamedQuery(hql,objects);
	}

	public int delete(String hql, Object... objects) throws TSApplicationException {
		return this.persistenceDAOIf.delete(hql,objects);
	}

	public T findById(ID id, boolean lock) {
		return (T) this.persistenceDAOIf.findById(id,lock);
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) this.persistenceDAOIf.findById(id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return this.persistenceDAOIf.findAll();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByBean(T instance) {
		return this.persistenceDAOIf.findByBean(instance);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByBean(T exampleInstance, String[] excludeProperty) {
		return this.persistenceDAOIf.findByBean(exampleInstance,excludeProperty);
	}

	@SuppressWarnings("unchecked")
	public T merge(T entity) throws TSApplicationException {
		return (T) this.persistenceDAOIf.merge(entity);
	}

	@SuppressWarnings("unchecked")
	public void persist(T entity) throws TSApplicationException {
		this.persistenceDAOIf.persist(entity);
		
	}

	@SuppressWarnings("unchecked")
	public void remove(T entity) throws TSApplicationException {
		this.persistenceDAOIf.remove(entity);
		
	}
	

	
	
	
	
	 
}
 
