package br.com.topsys.database.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSHibernateUtil;

public abstract class TSHibernateAb<T extends Serializable> {

	protected Class<T> persistentClass;

	public TSHibernateAb() {
		// TODO Auto-generated constructor stub
	}

	private Session getSession() {

		return TSHibernateUtil.getCurrentSession();
	}

	protected abstract Class<T> getPersistentClass();

	@SuppressWarnings("unchecked")
	public T findById(Long id, boolean lock) {
		
		this.openTransaction();
		
		T objeto = null;

		Session session = null;
		try {
			session = getSession();

			session.setFlushMode(FlushMode.NEVER);

			if (lock) {
				objeto = (T) session.get(this.getPersistentClass(), id,
						org.hibernate.LockMode.UPGRADE);
			} else {
				objeto = (T) session.get(this.getPersistentClass(), id);
			}

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return objeto;

	}

	protected void openTransaction() {
		
		if(!this.getSession().getTransaction().isActive()){
			this.getSession().beginTransaction();
		}
		
	}

	public T findById(Long id) {
		return this.findById(id, false);
	}

	public List<T> findAll(String... fieldsOrderBy) {
		this.openTransaction();
		
		List<T> coll = null;
		Session session = getSession();
		Query queryObject = null;
		try {

			session.setFlushMode(FlushMode.NEVER);
			
			StringBuilder sql=new StringBuilder("select o from ");
			sql.append(this.getPersistentClass().getName());
			sql.append(" o ");
			if(fieldsOrderBy!=null && fieldsOrderBy.length > 0){
				sql.append(" order by ");
			}
			for(String propertyName : fieldsOrderBy){
				sql.append("o.");
				sql.append(propertyName);
				sql.append(",");
			}
			
			String sqlFinal=sql.substring(0, sql.length()-1);
			
			queryObject = session.createQuery(sqlFinal);
			
			coll = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;

		
	}

	public List<T> findByBean(T instance,String... fieldsOrderBy) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = null;
		
		Criterion criterion=Example.create(instance).enableLike(
				MatchMode.ANYWHERE).ignoreCase().excludeZeroes();

		try {
			session = getSession();

			session.setFlushMode(FlushMode.NEVER);

			crit = session.createCriteria(getPersistentClass());
			
			for(String propertyName : fieldsOrderBy){
				
				crit.addOrder(Order.asc(propertyName));
				
			}
			
			crit.add(criterion);
			
		

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return crit.list();
		
	
	}
	
	/*
	public List<T> findByBeanAttribute(T instance,String[] atributos,String... fieldsOrderBy) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = null;
		
		Criterion criterion=Example.create(instance).enableLike(
				MatchMode.ANYWHERE).ignoreCase().excludeZeroes();

		try {
			session = getSession();

			session.setFlushMode(FlushMode.NEVER);

			crit = session.createCriteria(getPersistentClass());
			
			for(String atributo : atributos){
				
			}
			
			
			for(String propertyName : fieldsOrderBy){
				
				crit.addOrder(Order.asc(propertyName));
				
			}
			
			crit.add(criterion);
			
		

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return crit.list();
		
	
	}
	
	*/
	

	/**
	 * Parametro tipo se for false é para excluir as propriedades da consulta,
	 * caso contrario é para ser adicionado na consulta.
	 * 
	 * @param exampleInstance
	 * @param excludeProperty
	 * @param tipo
	 * @return
	 */
	public List<T> findByBean(T exampleInstance, String[] excludeProperty,String... fieldsOrderBy ) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = getSession();
		try {
			crit = session.createCriteria(getPersistentClass());
			session.setFlushMode(FlushMode.NEVER);
			Example example = Example.create(exampleInstance);
			example.ignoreCase();
			example.enableLike(MatchMode.ANYWHERE);
			
			example.excludeZeroes();

			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
			
			for(String propertyName : fieldsOrderBy){
				crit.addOrder(Order.asc(propertyName));
			}
			
			crit.add(example);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}
		return crit.list();
	}

	public T merge(T entity) throws TSApplicationException {
		Session session = getSession();
		T entityRetorno=null;
		try {

			entityRetorno = (T)session.merge(entity);
			session.flush();

		} catch (ConstraintViolationException e) {
			/* Forï¿½a o rollback da aplicaï¿½ï¿½o */
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_UNIQUE,e);

		} catch (Exception e) {
			/* Forï¿½a o rollback da aplicaï¿½ï¿½o */
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}
		return entityRetorno;
	}

	public void persist(T entity) throws TSApplicationException {
		Session session = getSession();

		try {

			session.persist(entity);
			session.flush();

		} catch (ConstraintViolationException e) {
			/* Forï¿½a o rollback da aplicaï¿½ï¿½o */

			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_UNIQUE,e);

		} catch (Exception e) {
			/* Forï¿½a o rollback da aplicaï¿½ï¿½o */

			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

	}

	public void remove(T entity) throws TSApplicationException {
		Session session = getSession();

		try {

			session.delete(entity);
			session.flush();

		} catch (ConstraintViolationException e) {
			/* Forï¿½a o rollback da aplicaï¿½ï¿½o */
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_FOREIGNKEY,e);

		} catch (Exception e) {
			/* Forï¿½a o rollback da aplicaï¿½ï¿½o */
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

	}

	
}
