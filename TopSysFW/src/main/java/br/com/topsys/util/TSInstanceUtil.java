package br.com.topsys.util;

import java.lang.reflect.Constructor;

import br.com.topsys.exception.TSSystemException;

public final class TSInstanceUtil {
	
	private TSInstanceUtil(){
		
	}
	
	public static Object getInstance(String nameClass){
		Object obj=null;
		
		try {
		    
			Class classe=Class.forName(nameClass);
			obj = classe.newInstance();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new TSSystemException(e);
		}
		return obj;
	}
	
	public static Object getInstance(String nameClass,String jndi){
		Object obj=null;
		
		try {
		    
			Class classe=Class.forName(nameClass);
			
		    Constructor construtor = classe.getConstructor(String.class);
		    
		    obj = construtor.newInstance(jndi);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new TSSystemException(e);
		}
		return obj;
	}
	
	
	public static Object getInstance(String nameClass,String nameClassDB, String url, String user, String password){
		Object obj=null;
		
		try {
		    
			Class classe=Class.forName(nameClass);
			
		    Constructor construtor = classe.getConstructor(String.class,String.class,String.class,String.class);
		    
		    obj = construtor.newInstance(nameClassDB,url,user,password);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new TSSystemException(e);
		}
		return obj;
	}
}
