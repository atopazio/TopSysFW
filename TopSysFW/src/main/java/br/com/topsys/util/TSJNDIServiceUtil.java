/*
 * Created on 19/11/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.topsys.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.topsys.exception.TSSystemException;



/**
 * @author andre.topazio
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class TSJNDIServiceUtil {

	// Propriedade que contem o contexto inicial
	private static Context context;

	static {
		try {
			context = new InitialContext();
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
		}
	}

	/**
	 * Método que retorna uma instancia do objeto Context, retornando null
	 * quando não consegue criar o objeto
	 */
	public static Context getContext() {
		return context;
	}

	/**
	 * Método que captura um valor do registro de nome
	 */
	public static Object getJNDIValue(final String aVariable){
		Object obj = null;
		try {
			obj = context.lookup("java:comp/env/" + aVariable);

		} catch (NamingException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return obj;
	}

	/**
	 * Método que retorna uma instanciaa do EJB especificado
	 */
	public static Object getEJB(final String aVariable){

		Object obj = null;
		try {
			obj = context.lookup(aVariable);

		} catch (NamingException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return obj;
	}

}