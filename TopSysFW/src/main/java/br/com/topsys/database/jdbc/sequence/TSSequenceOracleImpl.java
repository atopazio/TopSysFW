package br.com.topsys.database.jdbc.sequence;

import br.com.topsys.database.jdbc.TSDBList;
import br.com.topsys.database.jdbc.TSDataBaseBrokerImpl;

public final class TSSequenceOracleImpl extends TSDataBaseBrokerImpl implements
		TSSequenceIf {
	
	public TSSequenceOracleImpl(){
		super();
	}
	
	public TSSequenceOracleImpl(String jndi){
		super(jndi);
	}

	public Long getNextValue(String nomeSequence) {
		Long retorno = new Long(0);
		try {

			super.setSQL("SELECT " + nomeSequence + ".NEXTVAL FROM DUAL");

			TSDBList list = super.executeQuery();
			if(list.next()){
				retorno = list.getLong(1);
			}
			

		} finally {
			super.close();
		}
		return retorno;
	}

	public Long getCurrentValue(String nomeSequence) {
		Long retorno = new Long(0);
		try {

			super.setSQL("SELECT " + nomeSequence + ".CURRVAL FROM DUAL");

			TSDBList list = super.executeQuery();
			
			if(list.next()){
				retorno = list.getLong(1);
			}

		} finally {
			super.close();
		}
		return retorno;
	}

}
