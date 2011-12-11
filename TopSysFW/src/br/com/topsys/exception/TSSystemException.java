/*
 * Created on 20/03/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.topsys.exception;

/**
 * @author André Topázio
 * 
 * Essa Classe é usada para quando ocorrer erro no sistema, quando essa classe
 * de exceção é invocada irá ser mostrada o erro em uma tela de erro, mostrando
 * todos os detalhes da exceção gerada.
 */
public class TSSystemException extends RuntimeException {

	/**
	 * Esse construtor recebe como parametro um objeto Exception.
	 * 
	 * @param e
	 */
	public TSSystemException(Exception e) {
		super(e);
	}

	/**
	 * Esse construtor recebe como parametro um String e um objeto Exception
	 * Essa String poderá ser uma frase para ser mostrada na tela de erro.
	 * 
	 * @param x
	 * @param e
	 */
	public TSSystemException(String x, Exception e) {
		super(x, e);
	}

}