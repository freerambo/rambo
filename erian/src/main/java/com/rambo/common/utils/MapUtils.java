package com.rambo.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;


public class MapUtils {

	/**
	 * @param args
	 * @description
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Sep 23, 2013 2:29:55 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void paramsProcess(Map<String, Object> searchParams,
			ServletRequest request) {

		// System.out.println(searchParams.get("LIKE_title"));
		// get 方式请求 转换字符集 例如 %E6%89%93%E7%90%83 -----> 打球
		if (searchParams != null && !searchParams.isEmpty()) {
			Set<String> key = searchParams.keySet();
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				String result = (String) searchParams.get(s);
				try {
					String value = new String(result.getBytes("ISO-8859-1"),
							"utf-8");
					searchParams.put(s, value);
					request.setAttribute("search_" + s, value);
					// System.out.println(s + " ----> "+ searchParams.get(s));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
