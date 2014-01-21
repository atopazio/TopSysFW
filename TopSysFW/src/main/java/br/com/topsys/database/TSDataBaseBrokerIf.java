package br.com.topsys.database;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.topsys.database.jdbc.TSDBList;
import br.com.topsys.exception.TSApplicationException;


public interface TSDataBaseBrokerIf {
	
	public Long getSequenceNextValue(String nome);
	
	public Long getSequenceNextValue(String nome,String className);
	
	public Long getSequenceCurrentValue(String nome);
	
	public Long getSequenceCurrentValue(String nome,String className);
	
	public Connection getConnection();
		
	public void close();

	public void beginTransaction();

	public boolean getAutoCommit();

	public void rollback();

	public void endTransaction();

	public void prepareProcedure(String nomeProcedure, int parametros);

	public void prepareProcedure(String nomeProcedure);

	public void prepareFunction(String nomeFunction, int parametros);

	public void prepareFunction(String nomeFunction);
     
	public void addPropertySQL(String chave);
	
	public String getPropertySQL();
    
    public void setSQL(String query);
    
    public void setSQL(String query,Object... objects);

	public void setPropertySQL(String query);
	
	public void setPropertySQL(String query,Object... objects);
	
	public void set(Object... values);
	
	public void set(Object object);
	
	public void set(Object value, Calendar GMT);
	
	public void setProcedureOrFunctionNull();

	public void setProcedureOrFunctionParameter(Object value);
	
	public void setProcedureOrFunctionParameter(Object... value);

	public void setOutParameterString();

	public void setOutParameterInteger();

	public void setOutParameterLong();

	public void setOutParameterNumeric();

	public void setOutParameterDate();

	public void setOutParameterBoolean();

	public void setOutParameterDouble();

	public void setOutParameterTimestamp();

	public void setOutParameterOther();

	public void setOutParameterRef();

	public boolean executeProcedureOrFunction() throws TSApplicationException;

	public TSDBList executeQuery();

	public Object getObjectBean(Class bean,String... values);
	
	public Object getObject();
	
	public List getCollectionBean(Class bean,String... values);
	
	public void addBatch() throws TSApplicationException;

	public int execute() throws TSApplicationException;
	
	public Long executeIdentity() throws TSApplicationException;

	public int[] executeBatch() throws TSApplicationException;

	public TSDBList getTSDBListFunction();

	public String getStringFunction();

	public Date getDateFunction();

	public Double getDoubleFunction();

	public Long getLongFunction();

	public Integer getIntegerFunction();

	public Timestamp getTimestampFunction();

	public Object getObjectFunction();

	public Object getObjectProcedure(int parametro);

	public Object getObjectProcedure();

	public Long getLongProcedure();

	public Integer getIntegerProcedure();

	public Double getDoubleProcedure();

	public Float getFloatProcedure();

	public Date getDateProcedure();

	public String getStringProcedure();

	public Timestamp getTimestampProcedure();


}
 
