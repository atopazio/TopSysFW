package br.com.topsys.database.factory;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.database.jdbc.sequence.TSSequenceIf;
import br.com.topsys.util.TSInstanceUtil;
import br.com.topsys.util.TSPropertiesUtil;

public final class TSSequenceFactory {

	
	private static String nameClass=null;
	
	static{
		nameClass = TSPropertiesUtil.getInstance().getProperty(
				TSConstant.CLASS_SEQUENCE_IMPL);
	}
	
	private TSSequenceFactory(){
		
		
	}
	
	
	public static TSSequenceIf getSequenceIf(String jndi){
		return (TSSequenceIf)TSInstanceUtil.getInstance(nameClass,jndi);
	}
	
	public static TSSequenceIf getSequenceIf(String jndi,String className){
		return (TSSequenceIf)TSInstanceUtil.getInstance(className,jndi);
	}
	

}
