/**
 * 
 */
package br.com.topsys.exception;

import javax.ejb.ApplicationException;

/**
 * @author andre
 *
 */
@ApplicationException(rollback=true)
public class TSBusinessException extends TSApplicationException {

	private String mensageDataBase;
	
	public TSBusinessException(Exception e) {
		super(e);
	}

	public TSBusinessException(String chave) {
		super(chave);
		this.mensageDataBase=chave;
		
	}
	
	public TSBusinessException(String chave,Exception e) {
		super(chave,e);
	}
	
	
		
		
}
