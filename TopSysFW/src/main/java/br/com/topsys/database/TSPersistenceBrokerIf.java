package br.com.topsys.database;

import java.io.Serializable;
import java.util.List;

import br.com.topsys.exception.TSApplicationException;

public interface TSPersistenceBrokerIf<T, ID extends Serializable> extends TSGenericBrokerIf<T,ID> {
	
	public List<T> findByCriteria(
			org.hibernate.criterion.Criterion... criterion);
	
	public List<T> find(String query);
	
	public List<T> find(String query, Object... objects);
	
	public T get(String hql);
	
	public T get(String hql, Object... objects);
	
	public T getNamedQuery(String hql,
			Object... objects);
	
	public Object getObject(String ejbql, Object... objects);
	
	public Object getObjectNamedQuery(String ejbql, Object... objects);
	
	public List<T> findNamedQuery(String query, Object... objects);
	
	public int deleteNamedQuery(String name,Object... objects) throws TSApplicationException;
	
	public int update(String hql,Object... objects)throws TSApplicationException;
	
	public int updateNamedQuery(String hql,Object... objects)throws TSApplicationException;
	
	public int delete(String hql,Object... objects)throws TSApplicationException;
}
