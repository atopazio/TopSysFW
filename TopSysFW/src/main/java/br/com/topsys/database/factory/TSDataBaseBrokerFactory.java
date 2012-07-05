package br.com.topsys.database.factory;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.util.TSInstanceUtil;
import br.com.topsys.util.TSPropertiesUtil;

public final class TSDataBaseBrokerFactory {

	
	private static String nameClass=null;
	
	static{
		nameClass = TSPropertiesUtil.getInstance().getProperty(
				TSConstant.CLASS_BROKER_IMPL);
	}
	
	private TSDataBaseBrokerFactory() {
	}

	
	public static TSDataBaseBrokerIf getDataBaseBrokerIf(){
		return (TSDataBaseBrokerIf)TSInstanceUtil.getInstance(nameClass);
	}
	
	public static TSDataBaseBrokerIf getDataBaseBrokerIf(String jndi){
		return (TSDataBaseBrokerIf)TSInstanceUtil.getInstance(nameClass,jndi);
		
	}
	
	public static TSDataBaseBrokerIf getDataBaseBrokerIf(String classNameDB,String url, String user, String password){
		return (TSDataBaseBrokerIf)TSInstanceUtil.getInstance(nameClass,classNameDB,url,user,password);
		
	}
	
}
