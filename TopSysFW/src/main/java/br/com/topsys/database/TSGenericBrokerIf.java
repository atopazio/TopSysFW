package br.com.topsys.database;

import java.io.Serializable;
import java.util.List;

import br.com.topsys.exception.TSApplicationException;

public interface TSGenericBrokerIf<T, ID extends Serializable> {

	public T findById(ID id, boolean lock);

	public T findById(ID id);

	public List<T> findAll();

	public List<T> findByBean(T instance);

	public List<T> findByBean(T exampleInstance, String[] excludeProperty);

	public T merge(T entity) throws TSApplicationException;

	public void persist(T entity) throws TSApplicationException;

	public void remove(T entity) throws TSApplicationException;

	

}
