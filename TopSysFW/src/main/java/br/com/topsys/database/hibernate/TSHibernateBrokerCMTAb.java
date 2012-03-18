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
import org.hibernate.exception.ConstraintViolationException;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.database.TSPersistenceBrokerIf;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSBusinessException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSHibernateUtil;

public abstract class TSHibernateBrokerCMTAb<T, ID extends Serializable> implements
		TSPersistenceBrokerIf<T, ID> {

	private Class<T> persistentClass;

	public TSHibernateBrokerCMTAb() {

		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	protected Session getSession() {
		return TSHibernateUtil.getSession();
	}

	protected final Class<T> getPersistentClass() {
		return persistentClass;
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) {
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
		} finally {
			session.close();
		}

		return objeto;

	}

	public T findById(ID id) {
		return this.findById(id, false);
	}

	public List<T> findAll() {
		return this.find("select o from "+this.getPersistentClass().getName()+" o");
	}

	public List<T> findByBean(T instance) {
		return findByCriteria(Example.create(instance).enableLike(
				MatchMode.ANYWHERE).ignoreCase().excludeZeroes());
	}

	@SuppressWarnings("unchecked")
	public List<T> findByBean(T exampleInstance, String[] excludeProperty) {
		Criteria crit = null;

		List<T> list = null;

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

			crit.add(example);

			list = crit.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public T merge(T entity) throws TSApplicationException {
		Session session = getSession();
        Object objeto=null;
		try {

			objeto = session.merge(entity);
			session.flush();

		} catch (ConstraintViolationException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}
		return (T)objeto;
	}

	public void persist(T entity) throws TSApplicationException {
		Session session = getSession();

		try {

			session.persist(entity);
			session.flush();

		} catch (ConstraintViolationException he) {

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

	}

	public void remove(T entity) throws TSApplicationException {
		Session session = getSession();

		try {

			session.delete(entity);
			session.flush();

		} catch (ConstraintViolationException e) {

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = null;
		Session session = null;
		List<T> list = null;

		try {
			session = getSession();

			session.setFlushMode(FlushMode.NEVER);

			crit = session.createCriteria(getPersistentClass());

			for (Criterion c : criterion) {
				crit.add(c);
			}

			list = crit.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

		return list;
	}

	public List<T> find(String query) {
		return find(query, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String query, Object... objects) {
		List<T> list = null;
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

			list = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

		return list;

	}

	public T get(String hql) {
		return get(hql, null);
	}

	@SuppressWarnings("unchecked")
	public T get(String hql, Object... objects) {
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
		} finally {
			session.close();
		}

		return object;
	}

	@SuppressWarnings("unchecked")
	public T getNamedQuery(String hql, Object... objects) {
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
		} finally {
			session.close();
		}

		return object;
	}

	public Object getObject(String ejbql, Object... objects) {
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
		} finally {
			session.close();
		}

		return objeto;
	}

	public Object getObjectNamedQuery(String ejbql, Object... objects) {
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
		} finally {
			session.close();
		}

		return objeto;
	}

	@SuppressWarnings("unchecked")
	public List<T> findNamedQuery(String query, Object... objects) {
		List<T> list = null;

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

			list = queryObject.list();

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

		return list;
	}

	
	public int deleteNamedQuery(String name, Object... objects)
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

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

		return qtdLines;

	}

	public int update(String hql, Object... objects)
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

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}
		return qtdLines;
	}

	public int updateNamedQuery(String hql, Object... objects)
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

			throw new TSBusinessException(TSConstant.MENSAGEM_UNIQUE);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}
		return qtdLines;

	}

	public int delete(String hql, Object... objects)
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

			throw new TSBusinessException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {

			throw new TSSystemException(e);
		} finally {
			session.close();
		}

		return qtdLines;
	}

}
