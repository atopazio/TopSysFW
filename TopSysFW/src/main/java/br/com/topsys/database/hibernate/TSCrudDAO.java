package br.com.topsys.database.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class TSCrudDAO<T extends Serializable> extends TSHibernateAb<T> {
	
	public TSCrudDAO(){
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected Class<T> getPersistentClass() {
		// TODO Auto-generated method stub
		return this.persistentClass;
	}
	
	
	
}
