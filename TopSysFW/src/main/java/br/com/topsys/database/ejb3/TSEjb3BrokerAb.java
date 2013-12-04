package br.com.topsys.database.ejb3;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.hibernate.FlushMode;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSBusinessException;
import br.com.topsys.exception.TSSystemException;

@SuppressWarnings("unchecked")
public abstract class TSEjb3BrokerAb<T> {

	private Class<T> persistentClass;

	@PersistenceContext(unitName="default")
	protected EntityManager em;

	public TSEjb3BrokerAb() {

		this.init();

	}

	private void init() {
		Type type = getClass().getGenericSuperclass();
		Type arg;

		if (type instanceof ParameterizedType) {
			arg = ((ParameterizedType) type).getActualTypeArguments()[0];
		} else if (type instanceof Class) {
			arg = ((ParameterizedType) ((Class) type).getGenericSuperclass())
					.getActualTypeArguments()[0];

		} else {
			throw new RuntimeException("Type no construtor invalido '"
					+ getClass() + "'!");
		}

		if (arg instanceof Class) {
			this.persistentClass = (Class<T>) arg;
		} else if (arg instanceof ParameterizedType) {
			this.persistentClass = (Class<T>) ((ParameterizedType) arg)
					.getRawType();
		} else {
			throw new RuntimeException(
					"Problema para saber a classe generica '" + getClass()
							+ "'! ");
		}
	}

	protected void setEm(EntityManager em) {
		this.em = em;
	}

	protected Class getPersistentClass() {
		return persistentClass;
	}

	public List<T> find(String query) {
		return find(query, null);
	}

	public List<T> find(String query, Object... objects) {
		List<T> coll = null;

		javax.persistence.Query queryObject = null;
		try {

			queryObject = em.createQuery(query);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					queryObject.setParameter(i, o);
					i++;
				}
			}

			coll = queryObject.getResultList();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;
	}
	
	
	public List<T> find(int maxResult,String query,Object... objects) {
		List<T> coll = null;

		javax.persistence.Query queryObject = null;
		try {

			queryObject = em.createQuery(query);
			
			queryObject.setMaxResults(maxResult);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					queryObject.setParameter(i, o);
					i++;
				}
			}

			coll = queryObject.getResultList();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;
	}

	public T get(String hql) {
		return get(hql, null);
	}

	public T get(String hql, Object... objects) {
		javax.persistence.Query query = null;
		T object = null;

		try {

			query = em.createQuery(hql);
			
			
			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			object = (T) query.getSingleResult();
		}catch (NoResultException e) {
			// retorna objeto null.
			
		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return object;
	}

	public T getNamedQuery(String hql, Object... objects) {
		javax.persistence.Query query = null;
		T object = null;

		try {

			query = em.createNamedQuery(hql);
			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			object = (T) query.getSingleResult();
		}catch (NoResultException e) {
			// retorna objeto null.
			
		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return object;
	}

	public Object getObject(String ejbql, Object... objects) {
		javax.persistence.Query query = null;
		Object objeto = null;

		try {

			query = em.createQuery(ejbql);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			objeto = query.getSingleResult();
		}catch (NoResultException e) {
			// retorna objeto null.
			
		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return objeto;
	}

	public Object getObjectNamedQuery(String ejbql, Object... objects) {
		javax.persistence.Query query = null;
		Object objeto = null;

		try {

			query = em.createNamedQuery(ejbql);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			objeto = query.getSingleResult();
		}catch (NoResultException e) {
			// retorna objeto null.
			
		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return objeto;
	}

	public List<T> findNamedQuery(String query, Object... objects) {
		List<T> coll = null;

		javax.persistence.Query queryObject = null;
		try {

			// em.setFlushMode(flushMode);
			queryObject = em.createNamedQuery(query);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					queryObject.setParameter(i, o);
					i++;
				}
			}

			coll = queryObject.getResultList();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return coll;
	}

	public int deleteNamedQuery(String name, Object... objects)
			throws TSApplicationException {
		javax.persistence.Query query = null;
		int qtdLines = 0;

		try {

			query = em.createNamedQuery(name);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (PersistenceException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return qtdLines;
	}

	public int update(String hql, Object... objects)
			throws TSApplicationException {
		javax.persistence.Query query = null;
		int qtdLines = 0;

		try {

			query = em.createQuery(hql);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return qtdLines;
	}

	public int updateNamedQuery(String hql, Object... objects)
			throws TSApplicationException {
		javax.persistence.Query query = null;
		int qtdLines = 0;

		try {

			query = em.createNamedQuery(hql);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (PersistenceException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return qtdLines;
	}

	public int delete(String hql, Object... objects)
			throws TSApplicationException {
		javax.persistence.Query query = null;
		int qtdLines = 0;

		try {

			query = em.createQuery(hql);

			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}

			qtdLines = query.executeUpdate();
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (PersistenceException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return qtdLines;

	}

	public T findById(Long id) {
		T entity = null;
		try {

			entity = (T) em.find(getPersistentClass(), id);
		}catch (NoResultException e) {
			// retorna objeto null.
			
		} catch (Exception e) {

			throw new TSSystemException(e);
		}
		return entity;
	}

	public List<T> findAll(String... order) {
		StringBuilder sql = new StringBuilder();
		sql.append("select obj from ");
		sql.append(getPersistentClass().getSimpleName());
		sql.append(" obj ");
		
		if(order!=null && order.length > 0){
			sql.append(" order by ");
			
			for(String propertyName : order){
				sql.append("obj.");
				sql.append(propertyName);
				sql.append(",");
			}
		}
		
		
		String sqlFinal=sql.substring(0, sql.length()-1);
		
		return em.createQuery(sqlFinal
				)
				.getResultList();
	}

	public T merge(T entity) throws TSApplicationException {
		T ent = null;
		try {

			ent = em.merge(entity);
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		return ent;
	}

	public void persist(T entity) throws TSApplicationException {
		try {

			em.persist(entity);
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

	}

	public void remove(T entity) throws TSApplicationException {
		try {

			em.remove(em.merge(entity));
			em.flush();
			

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

	}
	


}
