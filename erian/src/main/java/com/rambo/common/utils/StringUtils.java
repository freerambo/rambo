package com.rambo.common.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * @param args
	 * @description String utils
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 16, 2013 3:00:09 PM
	 */
	public static String stringToDao(String s) {
		if (isNotEmpty(s))
			return new StringBuilder("'").append(s).append("'").toString()
					.trim();
		else
			return "";

	}

}
