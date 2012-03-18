package br.com.topsys.util;

import java.util.logging.Logger;

public final class TSLogUtil {
	private TSLogUtil() {
	}

	/**
	 * 
	 * @return
	 */
	public static Logger getInstance() {
		return Logger.getLogger("br.com.topsys");
	}
	
}