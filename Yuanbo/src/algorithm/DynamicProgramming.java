package algorithm;

import java.math.BigDecimal;

public class DynamicProgramming {

	public static void main (String[] args) {
		// TODO Auto-generated constructor stub
		System.out.println(p(610));
//		p(2);
	}
	
	
	
	/**
	 * 
	 * @function: Fibonacci in recursion
	 * @param n
	 * @return
	 * @author: Rambo Zhu     21 Sep 2017 11:27:38 am
	 */
	public static int f(int n){
	    if(n <= 1) return n;
	    return f(n-1) + f(n-2);
	} 
	
	
/*	递归方法的时间复杂度是n的指数级，因为有很多冗余的计算，如下：

	f(5)
	f(4) + f(3)
	f(3) + f(2) + f(2) + f(1)
	f(2) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1)
	f(1) + f(0) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1)*/

//	直接的想法是将递归转换为迭代：
	
	
	/**
	 * 
	 * @function: Fibonacci in Iteration
	 * @param n
	 * @return
	 * @author: Rambo Zhu     21 Sep 2017 11:30:38 am
	 */
	public static Long fIt(int n) {
	    if(n <= 1) return n + 0L; // convert int return into Long
	    Long first = 0L, second = 1L, third = 0L;
	    for (int i = 2; i <= n; i++) {
	        third = first + second;
	        first = second;
	        second = third;
	    }
	    return third;
	}
	
	/**
	 * Set a fix array with size 65535
	 */
	public static BigDecimal P[] = new BigDecimal[65535];
	public static BigDecimal p(int n) {
		if(n > 65535) return null;
		for(int i=0;i< P.length; i++) P[i] = new BigDecimal(0); 
		P[0] = new BigDecimal(1);
		 for(int i=1;i<=n;i++){// we recursively fill the array
		        for(int j=1; j<=6 && i-j>=0 ; j++){
		            P[i] = P[i].add(P[i-j]);
		        }
		    }
		 return P[n];
	}
	
}
