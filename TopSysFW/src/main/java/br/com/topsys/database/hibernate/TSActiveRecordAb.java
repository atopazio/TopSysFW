package br.com.topsys.database.hibernate;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
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


public abstract class TSActiveRecordAb<T> implements TSActiveRecordIf<T>, Serializable {

	protected Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public TSActiveRecordAb() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		
	}
	
	protected Session getSession() {

		return TSHibernateUtil.getSession();
	}
	
	
	protected Class<T> getPersistentClass() {
	
		return this.persistentClass;
	}
	
	

	


	@SuppressWarnings("unchecked")
	public T getById() {
		
		this.openTransaction();
		
		T objeto = null;

		Session session = null;
		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);

			objeto = (T) session.get(this.getPersistentClass(), getId());


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



	public List<T> findAll(String... fieldsOrderBy) {
		this.openTransaction();
		
		List<T> coll = null;
		Session session = getSession();
		Query queryObject = null;
		try {

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
			
			session.setFlushMode(FlushMode.NEVER);
			
			queryObject = session.createQuery(sqlFinal);
			
			coll = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;

		
	}
	
	public T getByModel(String... fieldsOrderBy) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = null;
		
		Criterion criterion=Example.create(this).ignoreCase().excludeZeroes();

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

		return (T) crit.uniqueResult();
		
	
	}
	
	public T getByModel(String[] excludeProperty,String... fieldsOrderBy ) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = getSession();
		try {
				
			session.setFlushMode(FlushMode.NEVER);
			crit = session.createCriteria(getPersistentClass());
		
			Example example = Example.create(this);
			example.ignoreCase();
			
			
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
		return (T) crit.uniqueResult();
	}

	public List<T> findByModel(String... fieldsOrderBy) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = null;
		
		Criterion criterion=Example.create(this).enableLike(
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
	
	


	public List<T> findByModel(String[] excludeProperty,String... fieldsOrderBy ) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = getSession();
		try {
			session.setFlushMode(FlushMode.NEVER);
			crit = session.createCriteria(getPersistentClass());
		
			Example example = Example.create(this);
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

	public T update() throws TSApplicationException {
		Session session = getSession();
		T entityRetorno=null;
		try {

			entityRetorno = (T)session.merge(this);
			
			session.flush();
	
		} catch (ConstraintViolationException e) {
		
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_UNIQUE,e);

		} catch (Exception e) {
		
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}
		return entityRetorno;
	}

	public void save() throws TSApplicationException {
		Session session = getSession();

		try {

			session.persist(this);
			
			session.flush();
			
			
		} catch (ConstraintViolationException e) {
			this.setId(null);
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_UNIQUE,e);

		} catch (Exception e) {
			e.printStackTrace();

			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

	}

	public void delete() throws TSApplicationException {
		Session session = getSession();

		try {

			session.delete(this);
			
			session.flush();
			
		} catch (ConstraintViolationException e) {
		
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_FOREIGNKEY,e);

		} catch (Exception e) {
			
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

	}

	

	
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		
		this.openTransaction();
		
		Criteria crit = null;
		Session session = null;

		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			crit = session.createCriteria(getPersistentClass());

			for (Criterion c : criterion) {
				crit.add(c);
			}

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return crit.list();
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<T> findBySQL(String sql, Object... param) {
		
		this.openTransaction();
		
		Query queryObject=null;
		Session session = null;
		
		List<T> coll=null;

		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			
			queryObject = session.createSQLQuery(sql).addEntity(this.getPersistentClass());

			 if (param != null) {
					int i = 0;
					for (Object o : param) {
						queryObject.setParameter(i, o);
						i++;
					}
				}

				coll = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;
	}

	protected List<T> find(String query) {
		return find(query, null);
	}

	@SuppressWarnings("unchecked")
	protected List<T> find(String query, Object... objects) {
		
		this.openTransaction();
		
		List<T> coll = null;
		Session session = getSession();
		Query queryObject = null;
		try {
			
			session.setFlushMode(FlushMode.NEVER);
			queryObject = session.createQuery(query);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					queryObject.setParameter(i, o);
					i++;
				}
			}

			coll = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;

	}

	protected T get(String hql) {
		return get(hql, null);
	}

	@SuppressWarnings("unchecked")
	protected T get(String hql, Object... objects) {
		
		this.openTransaction();
		
		Query query = null;
		T object = null;
		Session session = getSession();
		try {
			session.setFlushMode(FlushMode.NEVER);
			query = session.createQuery(hql);
			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			object = (T) query.uniqueResult();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return object;
	}

	@SuppressWarnings("unchecked")
	protected T getNamedQuery(String hql, Object... objects) {
		
		this.openTransaction();
		
		Query query = null;
		T object = null;
		Session session = getSession();
		try {
			session.setFlushMode(FlushMode.NEVER);
			query = session.getNamedQuery(hql);
			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			object = (T) query.uniqueResult();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return object;
	}

	protected Object getObject(String ejbql, Object... objects) {
		
		this.openTransaction();
		
		Query query = null;
		Object objeto = null;
		Session session = getSession();
		try {
			session.setFlushMode(FlushMode.NEVER);
			query = session.createQuery(ejbql);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			objeto = query.uniqueResult();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return objeto;
	}

	protected Object getObjectNamedQuery(String ejbql, Object... objects) {
		
		this.openTransaction();
		
		Query query = null;
		Object objeto = null;
		Session session = getSession();
		try {
			session.setFlushMode(FlushMode.NEVER);
			query = session.getNamedQuery(ejbql);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			objeto = query.uniqueResult();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return objeto;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findNamedQuery(String query, Object... objects) {
		
		this.openTransaction();
		
		List<T> coll = null;

		Query queryObject = null;
		Session session = getSession();
		try {

			session.setFlushMode(FlushMode.NEVER);
			queryObject = session.getNamedQuery(query);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					queryObject.setParameter(i, o);
					i++;
				}
			}

			coll = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;
	}

	protected int deleteNamedQuery(String name, Object... objects)
			throws TSApplicationException {
		Query query = null;
		Session session = null;
		int qtdLines = 0;
		try {

			session = getSession();

			query = session.getNamedQuery(name);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			
			session.flush();

		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

		return qtdLines;

	}

	protected int update(String hql, Object... objects)
			throws TSApplicationException {
		Query query = null;
		Session session = getSession();
		int qtdLines = 0;
		try {

			query = session.createQuery(hql);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			
			session.flush();

		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}
		return qtdLines;
	}

	protected int updateNamedQuery(String hql, Object... objects)
			throws TSApplicationException {
		Query query = null;
		Session session = getSession();
		int qtdLines = 0;
		try {

			query = session.getNamedQuery(hql);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			
			session.flush();
			
			
		} catch (ConstraintViolationException e) {
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}
		return qtdLines;

	}

	protected int delete(String hql, Object... objects)
			throws TSApplicationException {
		Query query = null;
		Session session = getSession();
		int qtdLines = 0;
		try {

			query = session.createQuery(hql);

			if (objects != null) {
				int i = 0;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			
			session.flush();
				
		} catch (ConstraintViolationException e) {
		
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

		return qtdLines;
	}


	
}
