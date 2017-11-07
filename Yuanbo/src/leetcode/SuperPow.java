/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> sd.java
 * Created on 25 Oct 2017-1:04:53 pm
 */
package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * function description：
 * 
 * The main idea is cashed on the repeated pattern of the remainder of a^b.
As long as we know the length of the pattern m, we just have to find an index point of this pattern based on b mod m.
In addition, if a > 1337, we can let a = a mod 1337.
Because if we let a = (1337x + c) where c = a mod 1337,
(1337x + c)(1337x + c)(1337x + c)...(1337x + c) mod 1337 == ccc...c mod 1337.
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  25 Oct 2017 1:04:53 pm
 */
public class SuperPow {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     25 Oct 2017 1:04:53 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			int rs = new SuperPow().superPow(2, new int[]{1,0});
			System.out.println(rs);
			
	}
	
	int m = 1337;
	 public int superPow(int a, int[] b) {
	      
		 if(a == 0 || b==null || b.length == 0 || a == m) return 0; 
		 if (a==1) return 1;
		 
		 if(a > m) superPow(a%m, b);
		 List<Integer> list  = findLoop(a);
		 int loopsize = list.size();
		 int rem = mod(b, loopsize);
		 return list.get((rem==0? loopsize: rem) -1);
	 }

	 private List<Integer> findLoop(int a) {
	        List<Integer> list = new ArrayList<Integer>();
	        boolean[] bl = new boolean[m];
	        int val = a;
	        while(!bl[val]){
	        	bl[val] = true;
	        	list.add(val);
	        	val = (val * a) % m;
	        }
	        return list;
	    }
	 
	    private int mod(int[] b, int c) {
	    	int rem = 0;
	    	 for (int i=0; i < b.length; i++) {
	             rem = (rem*10+b[i]) % c;
	         }
	    	return rem;
	    }
}
