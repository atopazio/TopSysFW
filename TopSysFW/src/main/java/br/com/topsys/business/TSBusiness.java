package br.com.topsys.business;

import java.io.Serializable;

import br.com.topsys.database.hibernate.TSCrudDAO;

public abstract class TSBusiness<T extends Serializable> extends TSCrudDAO<T>{
	

	public TSBusiness() {
		

		
	}
	

}
