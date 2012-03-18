package br.com.topsys.database.jdbc;



import java.sql.SQLException;

import br.com.topsys.database.TSDataBaseBrokerPFAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;



public final class TSDataBaseBrokerCMTImpl extends TSDataBaseBrokerPFAb {
	
	public TSDataBaseBrokerCMTImpl() {
		super();
	}
	
	public TSDataBaseBrokerCMTImpl(String jndi) {
		super(jndi);
	}

	public boolean executeProcedureOrFunction() throws TSApplicationException {
		boolean retorno = false;
		
		try {

			retorno = this.callableStatement.execute();

		} catch (SQLException e) {
			
			super.catchException(e);
			
			
		}catch(Exception e){
			
			throw new TSSystemException(e);
			
		} finally {
		
			this.close();
		}
		return retorno;

	}

	public int execute() throws TSApplicationException {
		int retorno = 0;
	
		try {

			retorno = statement.executeUpdate();
		
		} catch (SQLException e) {

			super.catchException(e);
			

		} catch (Exception e) {
			
			throw new TSSystemException(e);

		} finally {
			
			this.close();
		}
		return retorno;
	}

	public int[] executeBatch() throws TSApplicationException {
		int[] retorno = null;
		
		try {
		
			retorno = statement.executeBatch();
		
		} catch (SQLException e) {

			super.catchException(e);
			
		
		} finally {
		
			this.close();
		}

		return retorno;
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
			}
			
		
		} catch (SQLException e) {

			super.catchException(e);
			
			

		} catch (Exception e) {
			
			throw new TSSystemException(e);

		} finally {
			
			this.close();
		}
		return identity;
	}

		
	
}
 