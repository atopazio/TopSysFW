package br.com.topsys.database.ejb3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion; 
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.exception.ConstraintViolationException;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSBusinessException;
import br.com.topsys.exception.TSSystemException;

@SuppressWarnings("unchecked")
public abstract class TSEjb3BrokerAb<T extends Serializable>  {


	private Class<T> persistentClass;

	@PersistenceContext
	protected EntityManager em;

	
	public TSEjb3BrokerAb() {

		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected void setEm(EntityManager em) {
		this.em = em;
	}

	protected Class getPersistentClass() {
		return persistentClass;
	}

	/*
	public List<T> findByCriteria(Criterion... criterion) {
		// Using Hibernate, more difficult with EntityManager and EJB-QL
		org.hibernate.Criteria crit = null;
		List<T> list = null;

		try {
			org.hibernate.Session session = ((org.hibernate.ejb.HibernateEntityManager) em)
					.getSession();
			crit = session.createCriteria(getPersistentClass());

			for (org.hibernate.criterion.Criterion c : criterion) {
				crit.add(c);
			}

			list = crit.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}
		return list;
	}
	*/

	public List<T> find(String query) {
		return find(query, null);
	}


	public List<T> find(String query, Object... objects) {
		List<T> coll = null;

		javax.persistence.Query queryObject = null;
		try {

			// em.setFlushMode(flushMode);
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

	public T get(String hql) {
		return get(hql, null);
	}


	public T get(String hql, Object... objects) {
		javax.persistence.Query query = null;
		T object = null;

		try {

			// em.setFlushMode(flushMode);
			query = em.createQuery(hql);
			if (objects != null) {
				int i = 1;
				for (Object o : objects) {
					query.setParameter(i, o);
					i++;
				}
			}
			object = (T) query.getSingleResult();

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
		} catch (ConstraintViolationException e) {

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

		} catch (ConstraintViolationException e) {

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

		} catch (ConstraintViolationException e) {

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

		} catch (ConstraintViolationException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

		return qtdLines;

	}

	
	public T findById(Long id, boolean lock) {
		T entity;
		try {
			if (lock) {
				entity = (T) ((org.hibernate.ejb.HibernateEntityManager) em)
						.getSession().load(getPersistentClass(), id,
								org.hibernate.LockMode.UPGRADE);
			} else {
				entity = (T) em.find(getPersistentClass(), id);
			}
		} catch (Exception e) {

			throw new TSSystemException(e);
		}
		return entity;
	}

	public T findById(Long id) {

		return (T) this.findById(id, false);

	}


	public List<T> findAll() {
		return em.createQuery(
				"select obj from " + getPersistentClass().getName() + " obj")
				.getResultList();
	}

	/*
	public List<T> findByBean(T instance) {
		return findByCriteria(Example.create(instance).enableLike(
				MatchMode.ANYWHERE).ignoreCase());
	}
*/
	/*
	public List<T> findByBean(T exampleInstance, String[] excludeProperty) {
		// Using Hibernate, more difficult with EntityManager and EJB-QL
		Criteria crit = null;
		List<T> list = null;

		try {
			crit = ((org.hibernate.ejb.HibernateEntityManager) em).getSession()
					.createCriteria(getPersistentClass());

			Example example = Example.create(exampleInstance);

			example.ignoreCase();
			example.enableLike(MatchMode.ANYWHERE);

			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
			crit.add(example);

			list = crit.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		}
		return list;
	}
*/
	public T merge(T entity) throws TSApplicationException {
		T ent = null;
		try {

			ent = em.merge(entity);
			em.flush();

		} catch (EntityExistsException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);
		}catch (ConstraintViolationException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);
			

		}catch (PersistenceException e) {

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
		}catch (ConstraintViolationException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);
		
		}catch (PersistenceException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);
			
		
		} catch (Exception e) {

			throw new TSSystemException(e);
		}

	}

	public void remove(T entity) throws TSApplicationException {
		try {

			em.remove(entity);
			em.flush();

		} catch (ConstraintViolationException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		}

	}

}
