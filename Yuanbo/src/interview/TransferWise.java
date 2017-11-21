/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> TransferWise.java
 * Created on 14 Nov 2017-8:31:02 pm
 */
package interview;

import java.util.HashSet;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  14 Nov 2017 8:31:02 pm
 */
public class TransferWise {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     14 Nov 2017 8:31:02 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	  /*
     * Complete the function below.
     */
    static String[] romanizer(int[] numbers) {
        String[] res = new String[numbers.length];
        
        for(int i =0; i < numbers.length; i++)
            res[i] = process(numbers[i]);
        return res;
            
    }

   static String process(int num){
        char roman[] = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        int value[] = {1000, 500, 100, 50, 10, 5, 1};
        String res = "";
        for (int n = 0; n < 7; n += 2) {
            int x = num / value[n];
            if (x < 4) {
                for (int i = 1; i <= x; ++i) res += roman[n];
            } else if (x == 4) res = res + roman[n] + roman[n - 1];
            else if (x > 4 && x < 9) {
                res += roman[n - 1];
                for (int i = 6; i <= x; ++i) res += roman[n];
            }
            else if (x == 9) res = res + roman[n] + roman[n - 2];
            num %= value[n];            
        }
        return res;
   }
   
   
   static LinkedListNode distinct(LinkedListNode head) {
       HashSet<Integer> hs = new HashSet();
       LinkedListNode h = head, h1 = head;
       
       while(head != null){
           
           if(hs.contains(head.val)){
        	   head = head.next;
           }else{
        	   hs.add(head.val);
               h1.next = head;
               h1 = h1.next;
           }
          
       }
       return h;
          
   }


}
