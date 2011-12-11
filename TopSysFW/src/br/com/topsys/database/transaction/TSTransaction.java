/**
 * 
 */
package br.com.topsys.database.transaction;


import javax.transaction.UserTransaction;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSLogUtil;
import br.com.topsys.util.TSServiceLocatorUtil;

/**
 * @author andre
 * 
 */
public final class TSTransaction {

	private UserTransaction userTransaction;

	public  TSTransaction() {
	}
	
	

	public UserTransaction getUserTransaction() {

		if (userTransaction == null) {

			userTransaction = (UserTransaction) TSServiceLocatorUtil
					.getInstance().getUserTransaction(
							"java:comp/UserTransaction");

		}

		return userTransaction;

	}

	public void begin() {

		try {
			getUserTransaction().begin();
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);

		}

	}

	public void commit() {

		try {
			getUserTransaction().commit();
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

	public void rollback() {

		try {
			getUserTransaction().rollback();
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

	public void setRollbackOnly() {

		try {
			getUserTransaction().setRollbackOnly();
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

	public void setTransactionTimeout(int tempo) {

		try {
			getUserTransaction().setTransactionTimeout(tempo);
		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

	public int getStatus() {

		try {

			return getUserTransaction().getStatus();

		} catch (Exception e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

	}

}
