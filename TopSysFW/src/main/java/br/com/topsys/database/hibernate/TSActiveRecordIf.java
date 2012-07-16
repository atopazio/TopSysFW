package br.com.topsys.database.hibernate;

import java.util.List;

import br.com.topsys.exception.TSApplicationException;



public interface TSActiveRecordIf<T> {
	
	
	public Long getId();
	
	public T getById();

	public List<T> findAll(String... fieldsOrderBy);
	
	public T getByModel(String... fieldsOrderBy) ;
	
	public T getByModel(String[] excludeProperty,String... fieldsOrderBy );
	
	public List<T> findByModel(String... fieldsOrderBy);

	public List<T> findByModel(String[] excludeProperty,String... fieldsOrderBy );

	public T update() throws TSApplicationException;

	public void save() throws TSApplicationException;
		
	public void delete() throws TSApplicationException;
		


}
