/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> IntegerReplacement.java
 * Created on 19 Jul 2017-12:30:16 pm
 */
package leetcode;
import static java.lang.System.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * function description：
 * 
 * Given a positive integer n and you can do operations as follow:

If n is even, replace n with n/2.
If n is odd, you can replace n with either n + 1 or n - 1.
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Jul 2017 12:30:16 pm
 */
public class IntegerReplacement {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     19 Jul 2017 12:30:16 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		out.println(Integer.bitCount(7));
//		out.print(integerReplacement2(100000000));
	
	}
	
    public static int integerReplacement(int n) {
        
    	if (n == 1) return 0;
    	if (n == 2) return 1;
    	if (n == 3) return 2;
    	
    	if(n % 2 == 0){
    		return 1 + integerReplacement(n/2);
    	}else 
    		return  1 + Integer.min(integerReplacement(n+1), integerReplacement(n-1));
    }
   
   
   static int a[] = new int[100];
   public static int integerReplacement1(int n) {
        
	   if (n == 1) return 0;
   	if (n == 2) return 1;
   	if (n == 3) return 2;
    	
    	if(a[n] > 0) return a [n];
    	
    	if(n % 2 == 0){
    		a[n] = 1 + integerReplacement(n/2);
    	}else 
    		a[n] = 1 + Integer.min(integerReplacement(n+1), integerReplacement(n-1));
    
    	return a[n];
   }
    
    
    

   
   public static int integerReplacement2(int n) {
        
	   
		int count = 0;
   		while (n != 1){
   			
   			if((n&1) == 0 ){
   				n >>>= 1;
   			}else{
   				if(n == 3 || Integer.bitCount(n + 1) > Integer.bitCount(n - 1)){  // when 011 then - 1
   					n--;
   				}else{  // otherwise - 1
   					n++;
   				}
   			}
   			
   			count++;
   		}
    	return count;

   }
   
  
}
