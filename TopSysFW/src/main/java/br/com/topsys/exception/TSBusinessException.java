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

	
	public TSBusinessException(Exception e) {
		super(e);
	}

	public TSBusinessException(String chave) {
		super(chave);
		
	}
	
	public TSBusinessException(String chave,Exception e) {
		super(chave,e);
	}
	
	
		
		
}
