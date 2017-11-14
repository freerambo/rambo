/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> MSTest1.java
 * Created on 14 Nov 2017-2:41:42 pm
 */
package interview;

/**

Write a method to compute Excel column identifier 

Example: 
1 -> A
2 -> B
26 -> Z
27 -> 26 + 1 --> AA --> A * Z + A 
28 - AB  -- > A * Z + B 
52 - -> AZ -> A * Z + Z 
53 --> BA -> B * Z + A 
78 -- > BZ 
79 --> CA 

 */
public class MSTest1 {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     14 Nov 2017 2:41:42 pm
	 */
	public static void main(String[] args) {
		columnConvert(53);
	}
	
	static String[] ss = {"Z","A","B","C","D","E","F"}; 

	static void columnConvert(int num){
	    // input check
	    if(num <= 0) return;
	    
	    StringBuilder sb = new StringBuilder();
	    
	    int mod = 0;      

	    while(num > 0){
	        mod = num % 26;   // 53 --> mod = 1
	        num /= 26;        // mum -> 2
	      
	        if(mod == 0) num--; // when num is exactly the times of 26, then decrese by 1 
	        
	        sb.append(ss[mod]);  // A B
	    }
	    String st = sb.toString();
	    for(int i =st.length()-1; i>=0;i--){
	        System.out.print(sb.charAt(i)); // print - BA
	    }
	}

}
