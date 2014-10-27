package com;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Pattern p = Pattern.compile("a+b");
//		Matcher m = p.matcher("aaaaab");
//		boolean b = m.matches();
//		System.out.println(b);
		String str = "javajavaibmjava";
		String regex = "(java)+";
		System.out.println(str.replaceAll(regex, "java"));
	}

}
