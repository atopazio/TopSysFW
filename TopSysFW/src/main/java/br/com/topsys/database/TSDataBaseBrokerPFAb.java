package br.com.topsys.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import br.com.topsys.database.jdbc.TSDBList;
import br.com.topsys.exception.TSSystemException;


public abstract class TSDataBaseBrokerPFAb extends TSDataBaseBrokerAb {

	protected int incrementoProcedure = 1;

	protected int incrementoOutProcedure = 0;
	
	public TSDataBaseBrokerPFAb() {
		super();
	}
	
	public TSDataBaseBrokerPFAb(String jndi) {
		super(jndi);
	}


	public void prepareProcedure(String nomeProcedure, int parametros) {
		StringBuffer sb = new StringBuffer();
		try {

			sb.append("{call ");
			sb.append(nomeProcedure);
			if (parametros <= 0) {
				sb.append("}");
			} else {
				sb.append("(");
				for (int i = 0; i < parametros; i++) {
					sb.append("?,");
				}
				sb = new StringBuffer(sb.substring(0,
						(sb.toString().length() - 1)));
				sb.append(")}");
			}

			super.callableStatement = this.getConnection().prepareCall(
					sb.toString());

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}

	}

	public void prepareProcedure(String nomeProcedure) {
		this.prepareProcedure(nomeProcedure, 0);

	}

	public void prepareFunction(String nomeFunction, int parametros) {
		StringBuffer sb = new StringBuffer();
		try {

			sb.append("{? = call ");
			sb.append(nomeFunction);
			if (parametros <= 0) {
				sb.append("}");
			} else {
				sb.append("(");
				for (int i = 0; i < parametros; i++) {
					sb.append("?,");
				}
				sb = new StringBuffer(sb.substring(0,
						(sb.toString().length() - 1)));
				sb.append(")}");
			}

			super.callableStatement = this.getConnection().prepareCall(
					sb.toString());

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}

	}

	public void prepareFunction(String nomeFunction) {
		this.prepareFunction(nomeFunction, 0);

	}

	public void setProcedureOrFunctionNull() {
		try {
			this.callableStatement.setNull(this.incrementoProcedure++,
					Types.NULL);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}

	}

	public void setProcedureOrFunctionParameter(Object value) {

		try {

			if (value == null) {

				super.callableStatement.setNull(this.incrementoProcedure++,
						Types.NULL);

			} else {

				if (value instanceof Timestamp) {

					super.callableStatement.setObject(
							this.incrementoProcedure++, value, Types.TIMESTAMP);

				} else if (value instanceof Date) {

					super.callableStatement.setObject(
							this.incrementoProcedure++, value, Types.DATE);

				} else {

					super.callableStatement.setObject(
							this.incrementoProcedure++, value);

				}

			}
		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}

	}

	public void setProcedureOrFunctionParameter(Object... values) {

		if (values == null) {

			throw new IllegalArgumentException();

		}

		for (int x = 0; x < values.length; x++) {

			this.setProcedureOrFunctionParameter(values[x]);

		}

	}

	private void setOutParameter(int type) {
		try {
			if (this.incrementoOutProcedure == 0) {
				this.incrementoOutProcedure = this.incrementoProcedure;
			}
			super.callableStatement.registerOutParameter(
					this.incrementoProcedure++, type);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
	}

	public void setOutParameterString() {
		this.setOutParameter(Types.VARCHAR);

	}

	public void setOutParameterInteger() {
		this.setOutParameter(Types.INTEGER);

	}

	public void setOutParameterLong() {
		this.setOutParameter(Types.INTEGER);

	}

	public void setOutParameterNumeric() {
		this.setOutParameter(Types.NUMERIC);

	}

	public void setOutParameterDate() {
		this.setOutParameter(Types.DATE);

	}

	public void setOutParameterBoolean() {
		this.setOutParameter(Types.BOOLEAN);

	}

	public void setOutParameterDouble() {
		this.setOutParameter(Types.DOUBLE);

	}

	public void setOutParameterTimestamp() {
		this.setOutParameter(Types.TIMESTAMP);

	}

	public void setOutParameterOther() {
		this.setOutParameter(Types.OTHER);

	}

	public void setOutParameterRef() {
		this.setOutParameter(Types.REF);

	}

	public TSDBList getTSDBListFunction() {
		TSDBList list = null;

		try {

			list = new TSDBList((ResultSet) callableStatement.getObject(1));

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return list;
	}

	public String getStringFunction() {
		String valor = null;

		try {

			valor = this.callableStatement.getString(1);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Date getDateFunction() {
		Date valor = null;

		try {

			valor = this.callableStatement.getDate(1);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Double getDoubleFunction() {
		Double valor = null;

		try {

			valor = this.callableStatement.getDouble(1);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Long getLongFunction() {
		Long valor = null;

		try {

			valor = this.callableStatement.getLong(1);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Integer getIntegerFunction() {
		Integer valor = null;

		try {

			valor = new Integer(this.callableStatement.getInt(1));

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Timestamp getTimestampFunction() {
		Timestamp valor = null;

		try {

			valor = this.callableStatement.getTimestamp(1);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Object getObjectFunction() {
		Object valor = null;

		try {

			valor = this.callableStatement.getObject(1);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return valor;
	}

	public Object getObjectProcedure(int parametro) {
		Object retorno = null;

		try {

			retorno = this.callableStatement.getObject(parametro);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Object getObjectProcedure() {
		Object retorno = null;

		try {

			retorno = this.callableStatement
					.getObject(this.incrementoOutProcedure++);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Long getLongProcedure() {
		Long retorno = null;

		try {

			retorno = new Long(super.callableStatement
					.getLong(this.incrementoOutProcedure++));

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Integer getIntegerProcedure() {
		Integer retorno = null;

		try {

			retorno = new Integer(super.callableStatement
					.getInt(this.incrementoOutProcedure++));

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Double getDoubleProcedure() {
		Double retorno = null;

		try {

			retorno = new Double(super.callableStatement
					.getDouble(this.incrementoOutProcedure++));

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Float getFloatProcedure() {
		Float retorno = null;

		try {

			retorno = new Float(super.callableStatement
					.getFloat(this.incrementoOutProcedure++));

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Date getDateProcedure() {
		Date retorno = null;

		try {

			retorno = super.callableStatement
					.getDate(this.incrementoOutProcedure++);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public String getStringProcedure() {
		String retorno = null;

		try {

			retorno = super.callableStatement
					.getString(this.incrementoOutProcedure++);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

	public Timestamp getTimestampProcedure() {
		Timestamp retorno = null;

		try {

			retorno = super.callableStatement
					.getTimestamp(this.incrementoOutProcedure++);

		} catch (SQLException e) {
			this.close();

			throw new TSSystemException(e);
		}
		return retorno;
	}

}
