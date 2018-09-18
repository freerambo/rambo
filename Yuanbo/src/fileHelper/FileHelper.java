package fileHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FileHelper {

	// sum(IF(meter_id='DA3509748',value,0)) AS DA3509748,

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String s = readLineOfFile();
//		System.out.print(s);
		process();
//		System.out.print(fileRead("D:/meters.txt"));
	}
	
	static void process(){
		BufferedReader read = null;    
		try {    
			read = new BufferedReader(new FileReader("/Users/yuzhu/git/rambo/Yuanbo/src/fileHelper/errorcode"));    
			String s = read.readLine();
			StringBuilder sb = new StringBuilder();
			while(null != s ){
				s = s.trim();
				
				String errorName = s.substring(0, s.indexOf("("));
				String errorCode = s.substring(s.indexOf("(")+1, s.indexOf(","));
				String errorMsg = s.substring(s.indexOf(",")+1, s.indexOf(")"));
				
				System.out.println(errorMsg.replace(".getErrorMessage(", "").replaceAll("\"", "").trim());

//				System.out.println(errorName + "\t" + errorCode.replace("ErrorReason.", "") + "\t" + errorMsg.replace(".getErrorMessage(", ""));
//				System.out.println(s);
//				if(!s.endsWith(")"))
//						System.err.println(s);
				s = read.readLine();
			}
			read.close();

		} catch (IOException e) {    
		    // error processing code   
			e.printStackTrace();
		} 

	}
	static String readLineOfFile(){
		BufferedReader read = null;    
		try {    
			read = new BufferedReader(new FileReader("/Yuanbo/src/fileHelper/errorcode"));    
			String s = read.readLine();
			StringBuilder sb = new StringBuilder();
			while(null != s ){
				s = s.trim();
				if(!"".equals(s) && !s.startsWith("//")){
//					s = s.replace("//*", "");
//					s.replaceAll("//*", "");
					int i = s.lastIndexOf("),");
					if (i!= -1 && i != s.length() -1){
						s = s.substring(0, i+2);
					}
					sb.append(s);
					System.out.println(s);
				}
				s = read.readLine();

			}
			read.close();
			return sb.toString();

		} catch (IOException e) {    
		    // error processing code   
			e.printStackTrace();
		} 
		return null;
	}
/**
 * file reader 
 * @param file
 * @return
 */
	static String fileRead(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				line = sqlProcess(line);
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	final static String prefix = "sum(IF(meter_id=\'";
	final static String subfix = "\',value,0)) AS ";
	/**
	 * 
	 * @param merterId
	 */
	static String sqlProcess(String meterId){
		
	
		return prefix +meterId+subfix + meterId + ",";
	}
}
