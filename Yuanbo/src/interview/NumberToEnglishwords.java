package interview;
/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 *  -> NumberToEnglishwords.java
 * Created on 6 Jul 2017-4:21:21 pm
 */

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  6 Jul 2017 4:21:21 pm
 */
public class NumberToEnglishwords {

		/**
	Print number in English form
	Example

	    111  = One Hundred Eleven = 100 + 11 = [100, 11]/ [100 + 10 + 1]
	    123  = One Hundred Twenty Three = [100 + 20 + 3]
	    106  = [100, 06]
	    2412 = Two Thousand Four Hundred Twelve
	    2453 = Two Thousand Four Hundred Fifty Three

	*/
	    public static void main (String[] args) throws java.lang.Exception
	    {
	        
	            
	             
	    }
	    
	    
	    public static String convertNumToString(int num, String unit){
	        
	        String result = null;
	        
	        
	        if (num < 0) return "invalid input";
	        
	        
	       
	        
	        
	        
	        if(num <= 20){
	           
	            return number[num - 1];
	                    
	        }else if (num < 100){
	            
	             int remain = num / 10;
	             int left = num % 10;
	             if(left == 0){
	                 
	             }else{
	                 return number[num - 1];
	                 
	             }
	            
	            
	            convertNumToString(remain, "");
	            
	        
	        }else{

	            
	             int remain = num / 100;
	             int left = num % 100;
	            
	              if(left == 0){
	                 
	             }else{
	                 return number[num - 1];
	                 
	             }
	            
	            
	            convertNumToString(remain, "unit");
	        }
			return result;
	        
	        
	        
	        
	        
	        
	    }
	    
	    /**  
	    
	     English word for number below 20 
	    **/
	    public static final String[] number = {
	        "one",
	        "two",
	        "three",
	        "four",
	        "five",

	        "eleven",
	        "telve",
	        "thirten",
	        "fourthen",
	        "fifthen"
	        
	    };
	    
	    
	    
	    public static final String[] tenNum = {
	        "ten",
	        "twenty",
	        "thirty",
	        "fourty",
	        "fifty"
	        
	    };
	    

}
