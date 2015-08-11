package com.rambo.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class NumberUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "";
		
		s = RandomStringUtils.randomAscii(8);
		System.out.println(s);
		s = RandomStringUtils.randomNumeric(8);

		System.out.println(s);
		s = RandomStringUtils.randomAlphabetic(8);

		System.out.println(s);
		
		s = RandomStringUtils.randomAlphanumeric(8);

		System.out.println(s);

		s = RandomStringUtils.random(8, "er");

		System.out.println(s);
	}

}
