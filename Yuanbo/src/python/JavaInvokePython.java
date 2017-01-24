package python;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author rambo
 * use java to invoke method of Python
 */
public class JavaInvokePython {
	 public static void main(String[] args){
         try{
        	 
                 System.out.println("start");
                 Long start = System.currentTimeMillis();
                 Process pr = Runtime.getRuntime().exec("python test.py");
                 
                 BufferedReader in = new BufferedReader(new
                         InputStreamReader(pr.getInputStream()));
                 String line;
                 while ((line = in.readLine()) != null) {
                     System.out.println(line);
                 }
                 in.close();
                 pr.waitFor();
                 Long end  =  System.currentTimeMillis();
                 System.out.println("end " +  (end -start)/1000.0);                 
                 // deal with log file
                 
         } catch (Exception e){
                     e.printStackTrace();
                 }
         }
}

