package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestCmd {

	public TestCmd() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testMk();
	}

	
	private static void testMk() {
		// TODO Auto-generated method stub
		
		try {
//			Runtime.getRuntime().exec("mkdir D:\\MyDir");
			   // µ«Õ¯’æ
			   Process process = Runtime.getRuntime().exec(
			     "cmd.exe  /k  start  http://www.hao123.net/");

			   //  π”√”√Ping√¸¡Ó

			 
//			Process ee = Runtime.getRuntime().exec(
//				     "cmd.exe  /k  start  ping 192.168.121.110");
//			Runtime.getRuntime().exec(
//				     "cmd.exe  /c  start  ipconfig");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
