/*
 * Created on 30/03/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.topsys.exception;



/**
 *
 * 
 * @author atopazio
 */

public class TSApplicationException extends Exception {

	
	
	/**
	 * Esse construtor recebe como parametro um objeto Exception.
	 * 
	 * @param e
	 */
	public TSApplicationException(Exception e) {
		super(e);
	}

	/**
	 * Esse metodo recebe como parametro uma chave do arquivo properties da
	 * aplicação, como por exemplo quando for utilizado com o struts o
	 * applicationResource.properties
	 * 
	 * @param chave
	 */
	public TSApplicationException(String chave) {
		super(chave);
	}
	
	
	public TSApplicationException(String chave,Exception e) {
		super(chave,e);
	}

}