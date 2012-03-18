package br.com.topsys.constant;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSPropertiesUtil;

public final class TSConstraint {

	private TSConstraint() {

	}

	public static int getUnique() {
		return Integer.parseInt(getConstraint(TSConstant.CODIGO_UNIQUE));
	}

	public static int getForeignKey() {
		return Integer.parseInt(getConstraint(TSConstant.CODIGO_FOREIGNKEY));
	}

	public static String getRaiseException() {
		return getConstraint(TSConstant.CODIGO_RAISE_EXCEPTION);
	}

	private static String getConstraint(String constraint) {
		String valor = "";
		try {

			valor = TSPropertiesUtil.getInstance().getProperty(constraint);

		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		return valor;
	}
}
