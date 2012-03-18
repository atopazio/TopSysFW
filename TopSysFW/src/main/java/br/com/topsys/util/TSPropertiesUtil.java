package br.com.topsys.util;

import java.util.ResourceBundle;

import br.com.topsys.exception.TSSystemException;

public final class TSPropertiesUtil {
	
	private static TSPropertiesUtil propertiesUtil=null;
	
	private static final String SQL = "config.sql";
	
	private ResourceBundle rb = null;

	
	private TSPropertiesUtil() {
		
		try {
		
			rb = ResourceBundle.getBundle(SQL);
			
		
		} catch (Exception e) {
			
			throw new TSSystemException(e);
		}
    
	}

	public static TSPropertiesUtil getInstance() {
		if(propertiesUtil == null){
			
			propertiesUtil = new TSPropertiesUtil();
		
	    }
		return propertiesUtil;
	}

	public String getProperty(String chave) {
		String valor=null;
		try{
			
			valor = this.rb.getString(chave.trim());
			
			if(valor != null){
				valor = valor.trim();
			}
		
		}catch(Exception e){	
			TSLogUtil.getInstance().severe(
                    "N�o achou a chave -> " + chave + " - no arquivo sql.properties "
                            + e.getMessage());
			throw new TSSystemException(e);
		}
		return valor;
	}
}
