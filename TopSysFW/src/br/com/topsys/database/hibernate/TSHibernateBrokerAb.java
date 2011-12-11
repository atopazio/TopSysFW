package br.com.topsys.database.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSHibernateUtil;

public abstract class TSHibernateBrokerAb<T extends Serializable> extends TSHibernateAb<T>
//implements TSPersistenceBrokerIf<T, ID> 
{

	
	public TSHibernateBrokerAb() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		
	}
	
	protected Session getSession() {

		return TSHibernateUtil.getCurrentSession();
	}
	
	@Override
	protected Class<T> getPersistentClass() {
		// TODO Auto-generated method stub
		return this.persistentClass;
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
			/* For�a o rollback da aplica��o */
			session.getTransaction().rollback();

			throw new TSApplicationException(TSConstant.MENSAGEM_FOREIGNKEY);

		} catch (Exception e) {
			session.getTransaction().rollback();

			throw new TSSystemException(e);
		}

		return qtdLines;
	}


	
}
