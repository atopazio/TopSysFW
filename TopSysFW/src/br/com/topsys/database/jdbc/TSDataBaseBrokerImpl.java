package br.com.topsys.database.jdbc;

import java.sql.SQLException;

import br.com.topsys.database.TSDataBaseBrokerPFAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;



public class TSDataBaseBrokerImpl extends TSDataBaseBrokerPFAb {
	
	public TSDataBaseBrokerImpl(){
		super();
	}
	
	public TSDataBaseBrokerImpl(String jndi){
		super(jndi);
	}
	

	private int transaction = 0;
	
	private void resetIncrements(){
		super.incrementoProcedure = 1;
		super.incrementoOutProcedure = 0;
		super.incremento = 1;
	}

	public boolean executeProcedureOrFunction() throws TSApplicationException {
		boolean retorno = false;

		try {

			retorno = this.callableStatement.execute();
			
			this.resetIncrements();

		} catch (SQLException e) {

			this.rollback();

			super.catchException(e);

		

		} finally {
			
			if (transaction == 0) {
				super.close();
			}
		}
		return retorno;
	}

	public int execute() throws TSApplicationException {
		int retorno = 0;
		try {

			retorno = statement.executeUpdate();
			
			this.resetIncrements();
		
		} catch (SQLException e) {

			this.rollback();

			super.catchException(e);
			
		

		} catch (Exception e) {
			
			throw new TSSystemException(e);

		}finally{
			
			if(transaction==0){
				this.close();
			}
		}

		return retorno;
	}

	public int[] executeBatch() throws TSApplicationException {
		int[] retorno = null;
		try {

			retorno = statement.executeBatch();
			
			this.resetIncrements();

		} catch (SQLException e) {

			this.rollback();

			super.catchException(e);

		

		} finally {

			if (transaction == 0) {
				super.close();
			}
		}

		return retorno;
	}

	@Override
	public void beginTransaction() {
		try {

			super.getConnection().setAutoCommit(false);

			this.transaction = 1;

		} catch (SQLException e) {
			super.close();

			throw new TSSystemException(e);
		}
	}

	@Override
	public void endTransaction() {
		try {

			super.getConnection().commit();

		} catch (SQLException e) {

			throw new TSSystemException(e);
		} finally {
			super.close();
		}
	}

	@Override
	public boolean getAutoCommit() {
		boolean retorno = false;

		try {

			retorno = super.getConnection().getAutoCommit();

		} catch (SQLException e) {
			super.close();
			throw new TSSystemException(e);
		}

		return retorno;

	}

	@Override
	public void rollback() {
		try {
			if (transaction == 1) {
				super.getConnection().rollback();
			}

		} catch (SQLException e) {

			throw new TSSystemException(e);
		} finally {
			super.close();
		}
	}

	public Long executeIdentity() throws TSApplicationException {
		Long identity = null;
		
		
		try {

			int i = statement.executeUpdate();
			
			if(i != 0){
				
				
				this.statement = this.getConnection().prepareStatement("SELECT @@IDENTITY");
				this.resultSet=this.statement.executeQuery();
				
				if(this.resultSet.next()){
					identity = this.resultSet.getLong(1);
				}
				
				this.resetIncrements();
				
			}
			
		
		} catch (SQLException e) {
			this.rollback();
			super.catchException(e);
			
			

		} catch (Exception e) {
			this.rollback();
			
			throw new TSSystemException(e);

		} finally {
			
			if (transaction == 0) {
				super.close();
			}
		}
		return identity;
	}

}
