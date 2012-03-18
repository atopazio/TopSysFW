/**
 * 
 */
package br.com.topsys.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import br.com.topsys.exception.TSSystemException;

/**
 * @author andre
 * 
 */
public final class TSEJBUtil {
	private Context context = null;
	
	private Map<String, Object>cache;
	
	private static TSEJBUtil factory;
	
	
	private TSEJBUtil(){
		
		try {
			
			context = new InitialContext();
			cache = Collections.synchronizedMap(new HashMap<String, Object>());
			
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
		
	}
	
	public static TSEJBUtil getInstance(){
		
		if(factory == null){
			factory = new TSEJBUtil();
		}
		
		return factory;
		
	}
	

	public final Object lookup(Class classe,boolean remote) {
		Object objeto = null;

		try {
			if(!cache.containsKey(classe.getSimpleName())){
				
	            if(remote){
	            	objeto = context.lookup(classe.getSimpleName()+"/remote");
	            }else{
	            	objeto = context.lookup(classe.getSimpleName()+"/local");
	            }
	            
	            cache.put(classe.getSimpleName(), objeto);
			
			}else{
				
				objeto = cache.get(classe.getSimpleName());
			
			}

		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return objeto;
	}
	
	public final Object lookup(String nome,boolean remote) {
		Object objeto = null;

		try {
			if(!cache.containsKey(nome)){
				
	            if(remote){
	            	objeto = context.lookup(nome+"/remote");
	            }else{
	            	objeto = context.lookup(nome+"/local");
	            }
	            
	            cache.put(nome, objeto);
			
			}else{
				
				objeto = cache.get(nome);
			
			}

		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return objeto;
	}

}
