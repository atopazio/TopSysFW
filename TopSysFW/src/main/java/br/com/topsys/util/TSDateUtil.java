/*
 * Created on 24/08/2003
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.topsys.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.topsys.exception.TSSystemException;

/**
 * @author andre
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public final class TSDateUtil {

	public final static String DD_MM_YYYY = "dd/MM/yyyy";

	public final static String MM_YYYY = "MM/yyyy";

	public final static String MM = "MM";

	public final static String YYYY = "yyyy";

	public final static String DD = "dd";

	public final static String MM_DD_YYYY = "MM/dd/yyyy";

	public final static String YYYY_MM_DD = "yyyy/MM/dd";

	public final static String HH_MM_SS = "HH:mm:ss";

	public final static String hh_MM_SS = "hh:mm:ss";

	public final static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

	public final static String DD_MM_YYYY_hh_MM_SS = "dd/MM/yyyy hh:mm:ss";

	public final static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

	public final static String DD_MM_YYYY_hh_MM = "dd/MM/yyyy hh:mm";

	public static final String HH_MM = "HH:mm";

	private TSDateUtil() {

	}

	/**
	 * 
	 * @param data
	 * @param num
	 * @return
	 */
	public static Date addDayDate(Date data, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_MONTH, num);
		return cal.getTime();
	}

	/**
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	public static boolean isValidDateBeforeDate(String dataInicial, String dataFinal) {
		Date dateIni = TSParseUtil.stringToDate(dataInicial);
		Date dateFim = TSParseUtil.stringToDate(dataFinal);
		return dateIni.before(dateFim);
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isValidDate(String data) {
		Date date = null;
		if (data != null && data.trim().length() > 0) {
			try {
				SimpleDateFormat dtFormat = new SimpleDateFormat(DD_MM_YYYY);
				date = dtFormat.parse(data);
			} catch (Exception e) {
				TSLogUtil.getInstance().warning(e.getMessage());

			}
		}
		return date == null ? false : true;

	}

	/**
	 * 
	 * @param data
	 * @param patterns
	 * @return
	 */
	public static boolean isValidDate(String data, String patterns) {
		Date date = null;
		if (data != null && data.trim().length() > 0) {
			try {
				SimpleDateFormat dtFormat = new SimpleDateFormat(patterns);
				date = dtFormat.parse(data);
			} catch (Exception e) {
				TSLogUtil.getInstance().warning(e.getMessage());
			}
		}
		return date == null ? false : true;

	}

	/**
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @param pattern
	 * @return
	 * @throws br.com.topsys.exception.TSSystemException
	 */
	public static long diferencaDias(String dataInicial, String dataFinal, String pattern) {

		SimpleDateFormat dfFormat = new SimpleDateFormat(pattern);
		long dtInicio = 0;
		long dtFim = 0;
		long resultado = 0;
		try {

			dtInicio = dfFormat.parse(dataInicial).getTime();
			dtFim = dfFormat.parse(dataFinal).getTime();
			resultado = (dtFim - dtInicio) / (24 * 60 * 60 * 1000);

		} catch (Exception e) {
			TSLogUtil.getInstance().warning("Erro na diferença de dias! " + e.getMessage());
			throw new TSSystemException(e);
		}
		return resultado;
	}

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqualDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(5) == cal2.get(5) && cal1.get(2) == cal2.get(2) && cal1.get(1) == cal2.get(1);
	}

	public static String obterDiaSemana(Date data) {

		Calendar calendario = new GregorianCalendar();

		calendario.setTime(TSParseUtil.stringToDate(TSParseUtil.dateToString(data, TSDateUtil.DD_MM_YYYY), TSDateUtil.DD_MM_YYYY));

		int dia = calendario.get(Calendar.DAY_OF_WEEK);

		String diaSemana = null;

		switch (dia) {

		case 1: {
			diaSemana = "Domingo";
			break;
		}
		case 2: {
			diaSemana = "Segunda";
			break;
		}
		case 3: {
			diaSemana = "Terça";
			break;
		}
		case 4: {
			diaSemana = "Quarta";
			break;
		}
		case 5: {
			diaSemana = "Quinta";
			break;
		}
		case 6: {
			diaSemana = "Sexta";
			break;
		}
		case 7: {
			diaSemana = "Sábado";
			break;
		}

		}
		return diaSemana;

	}

}