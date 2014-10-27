package Test.src;

import java.io.*;


public class JavaList {

	public static void main(String[] args) {
		File currDir = new File("D:/工作目录");
		//String[] files = currDir.list();
		String[] files = currDir.list(new JavaFilter());
		for(String f : files){
			System.out.println(f);
		}
		
	}

}
