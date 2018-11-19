package com.rambo.practice;

public class ShowSystemDefaultEncoding {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String encoding = System.getProperty("file.encoding");
		System.out.println(encoding);
	}

}
