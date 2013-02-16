package br.com.topsys.database.hibernate;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;



import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSHibernateUtil;
import br.com.topsys.util.TSUtil;


public abstract class TSActiveRecordAb<T> implements TSActiveRecordIf<T>, Serializable {

	protected Class<T> persistentClass;
	
	public TSActiveRecordAb() {
		//this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
		//		.getGenericSuperclass()).getActualTypeArguments()[0];
		
		this.init();
	
	}
	
	private void init(){
		Type type = getClass().getGenericSuperclass();  
        Type arg;  
        
        if(type instanceof  ParameterizedType){  
                arg = ((ParameterizedType)type).getActualTypeArguments()[0];  
        }else if(type instanceof Class){  
                arg = ((ParameterizedType)((Class)type).getGenericSuperclass()).getActualTypeArguments()[0];  
                  
        }else{  
                throw new RuntimeException("Type no construtor invalido '"+getClass()+"'!");  
        }  

        if(arg instanceof Class){  
                this.persistentClass = (Class<T>)arg;  
        }else if(arg instanceof ParameterizedType){  
                this.persistentClass = (Class<T>)((ParameterizedType)arg).getRawType();  
        }else{  
                throw new RuntimeException("Problema para saber a classe generica '"+getClass()+"'! ");  
        }
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
	
	public T getReference() {
		this.openTransaction();
		
		
		Session session = getSession();
		Query queryObject = null;
		
		T objeto = null;
		try {

			StringBuilder sql=new StringBuilder("select new ");
			sql.append(this.getPersistentClass().getName());
			sql.append("(o.id) from "); 
			sql.append(this.getPersistentClass().getName());
			sql.append(" o where o.id = :id");
		
			session.setFlushMode(FlushMode.NEVER);
			
			queryObject = session.createQuery(sql.toString());
			
			queryObject.setLong("id", getId());
			
			objeto = (T)queryObject.uniqueResult();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return objeto;

		
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

	public List<T> findByModel(Map<String, Object> map,String... fieldsOrderBy) {
		
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
			
			if (map != null){
				for (Iterator<Entry<String, Object>> iter =map.entrySet().iterator();iter.hasNext();){
					Entry<String,Object> entry = iter.next();
					crit.createCriteria(entry.getKey()).add(Example.create(entry.getValue()));
					
				}
			}	
			

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return crit.list();
		
	
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
	
	


	public List<T> findByModel(Map<String, Object> map, String[] excludeProperty,String... fieldsOrderBy ) {
		
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
			
			if (map != null){
				for (Iterator<Entry<String, Object>> iter =map.entrySet().iterator();iter.hasNext();){
					Entry<String,Object> entry = iter.next();
					crit.createCriteria(entry.getKey()).add(Example.create(entry.getValue()));
					
				}
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
			 
			session.delete(getById());
			
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
	
	protected List findBySQL(Class classe, String[] atributos, String sql, Object... param) {
		
		this.openTransaction();
		
		Query queryObject=null;
		Session session = null;
		
		List coll=null;

		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			
			if (atributos != null){
				for (String attr : atributos){
					sqlQuery = sqlQuery.addScalar(attr);
				}
			}
			
			
			queryObject = sqlQuery.setResultTransformer(Transformers.aliasToBean(classe));
			
			
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
	
	protected Object getBySQL(Class classe,String[] atributos, String sql, Object... param) {
		
		this.openTransaction();
		
		Query queryObject=null;
		Session session = null;
		
		Object object=null;

		try {
			session = getSession();
			session.setFlushMode(FlushMode.NEVER);
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			
			if (atributos != null){
				for (String attr : atributos){
					sqlQuery = sqlQuery.addScalar(attr);
				}
			}	
			queryObject = sqlQuery.setResultTransformer(Transformers.aliasToBean(classe));
			
			 if (param != null) {
					int i = 0;
					for (Object o : param) {
						queryObject.setParameter(i, o);
						i++;
					}
				}

			 object = queryObject.uniqueResult();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return object;
	}

	protected List<T> find(String query, String order) {
		return find(query, order,null);
	}

	@SuppressWarnings("unchecked")
	protected List<T> find(String query,String order, Object... objects) {
		
		this.openTransaction();
		
		List<T> coll = null;
		Session session = getSession();
		Query queryObject = null;
		try {
			
			if (TSUtil.isNotEmpty(order)){
				query += " order by "+order;
			}	
			
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
	
	protected int executeSQL(String sql, Object... objects)
			throws TSApplicationException {
		Query query = null;
		Session session = getSession();
		int qtdLines = 0;
		try {

			query = session.createSQLQuery(sql);

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
