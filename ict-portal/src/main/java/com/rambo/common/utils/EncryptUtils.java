package com.rambo.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 
 * @description 字符串加密
 * @version currentVersion(1.0)
 * @author Rambo
 * @createtime Aug 13, 2013 3:41:13 PM
 */
public class EncryptUtils {

	/**
	 * 
	 * @param source
	 * @return String 加密后字符串
	 * @description 字符串 MD5加密算法
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 13, 2013 3:41:45 PM
	 */
	public static final String encryptMD5(String source) {
		if (source == null) {
			source = "";
		}
		Md5Hash md5 = new Md5Hash(source);
		return md5.toString();
	}
	
	public static void main(String[] args){
		System.out.println(encryptMD5("12321312312qsafcdasfsadfsdf").length());
	}
}
