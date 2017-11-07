/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * common -> CommPractice.java
 * Created on 16 Oct 2017-2:20:53 pm
 */
package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  16 Oct 2017 2:20:53 pm
 */
public class CommPractice {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     16 Oct 2017 2:20:53 pm
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
	
		Integer i = 100;
		String s = "111";
		s = String.valueOf(i); //integer to numeric string   
		i = Integer.parseInt(s); //numeric string to an int  
		
		try {
			addLineAtEndOfFile(); // add line at the end of file
			readLineOfFile();  // read line of file
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get current method name 
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName(); 
		
		System.out.println(methodName);
		methodName.indexOf("a");
		
		SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );    
		Date date = format.parse("30/01/1989");  
		System.out.println(date);

}
	
	static void addLineAtEndOfFile() throws IOException{
		BufferedWriter out = null;    
		try {    
		    out = new BufferedWriter(new FileWriter("test123.txt", true));    
		    out.write("aString456\r\n");    
		} catch (IOException e) {    
		    // error processing code    
		} finally {    
		    if (out != null) {    
		        out.close();    
		    }    
		}  
		
	}
	
	static void readLineOfFile() throws IOException{
		BufferedReader read = null;    
		try {    
			read = new BufferedReader(new FileReader("test123.txt"));    
			String s = read.readLine();
			while(null != s){
				System.out.println(s);
				s = read.readLine();
			}
		} catch (IOException e) {    
		    // error processing code    
		} finally {    
		    if (read != null) {    
		    	read.close();    
		    }    
		}  
	}
	
	
	
	

}
