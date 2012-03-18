package br.com.topsys.database.jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSLogUtil;


/**
 * 
 * @author andre.topazio
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class TSDBList {
	private ResultSet result = null;

	/**
	 * Esse construtor recebe um ResultSet como parametro.
	 * 
	 * @param rs
	 */
	public TSDBList(ResultSet rs) {
		result = rs;
	}
	
	/**
	 * Esse metodo só deve ser usado quando o desenvolvedor precisar manipular os dados diretamente com o ResultSet.
	 * O recomendado é usar os metodos da classe TSDBList...pois já trata os dados para o desenvolvedor.
	 * @return
	 */
	public ResultSet getResultSet(){
		return result;
	}

	/**
	 * Esse metodo chama o proximo registro do banco de dados.
	 * 
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public boolean next() {
		boolean retorno = false;
		try {
			retorno = this.result.next();
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}
		return retorno;
	}

	/**
	 * Esse metodo chama o registro anterior do banco de dados, cuidado em usar
	 * este metodo pois alguns drivers não suportam está operação.
	 * 
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public boolean previous() {
		boolean retorno = false;
		try {
			retorno = this.result.previous();
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return retorno;
	}

	/**
	 * Esse metodo retorna o numero de registros do banco de dados.
	 * 
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public int getRow() {
		int row = 0;
		try {
			row = this.result.getRow();
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return row;
	}
	
	/**
	 * 
	 * @param parametro
	 * @return
	 */
	public Boolean getBoolean(int parametro) {
	    Boolean booleano = null;
		try {

		    booleano = new Boolean(this.result.getBoolean(parametro));
			if (this.result.wasNull()) {
			    booleano = null;
			}
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return booleano;
	}
		
	
	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Blob getBlob(int parametro) {
		Blob blob = null;
		try {

			blob = this.result.getBlob(parametro);
			if (this.result.wasNull()) {
				blob = null;
			}
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return blob;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Clob getClob(int parametro) {
		Clob clob = null;
		try {
			clob = this.result.getClob(parametro);

			if (this.result.wasNull()) {
				clob = null;
			}
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return clob;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Integer getInteger(int parametro) {
		Integer value = null;
		try {
			value = new Integer(this.result.getInt(parametro));

			if (this.result.wasNull()) {
				value = null;
			}
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Long getLong(int parametro) {
		Long value = null;
		try {
			value = new Long(this.result.getLong(parametro));

			if (this.result.wasNull()) {
				value = null;
			}
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Double getDouble(int parametro) {
		Double value = null;
		try {
			value = new Double(this.result.getDouble(parametro));

			if (this.result.wasNull()) {
				value = null;
			}
		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public java.util.Date getDate(int parametro) {
		java.util.Date value = null;
		try {
			value = this.result.getDate(parametro);

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Float getFloat(int parametro) {
		Float value = null;
		try {
			value = new Float(this.result.getFloat(parametro));

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public String getString(int parametro) {
		String value = null;
		try {
			value = this.result.getString(parametro);

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public BigDecimal getBigDecimal(int parametro) {
		BigDecimal value = null;
		try {
			value = this.result.getBigDecimal(parametro);

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Byte getByte(int parametro) {
		Byte value = null;
		try {
			value = new Byte(this.result.getByte(parametro));

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Short getShort(int parametro) {
		Short value = null;
		try {
			value = new Short(this.result.getShort(parametro));

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Timestamp getTimestamp(int parametro) {
		Timestamp value = null;
		try {
			value = this.result.getTimestamp(parametro);

			if (this.result.wasNull()) {
				value = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return value;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public Object getObject(int parametro) {
		Object objeto = null;
		try {
			objeto = this.result.getObject(parametro);

			if (this.result.wasNull()) {
				objeto = null;
			}

		} catch (SQLException e) {
			TSLogUtil.getInstance().severe(e.getMessage());
			throw new TSSystemException(e);
		}

		return objeto;
	}

}