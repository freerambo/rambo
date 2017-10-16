/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> MinStepTo1.java
 * Created on 16 Oct 2017-10:52:45 pm
 */
package algorithm;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 16 Oct 2017 10:52:45 pm
 */
public class MinStepTo1 {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu 16 Oct 2017 10:52:45 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(min_steps_dyn(3));
		System.out.println(min_steps_recur(3));

		
	}

	/**
	 * 1 + min(f(n/3),f(n/2),f(n-1))
	 * 
	 * @function:
	 * @param n
	 * @return
	 * @author: Rambo Zhu 16 Oct 2017 10:57:41 pm
	 */
	public static int min_steps_recur(int num) {
		int x = num, y = num, z = num;
		if (num <= 1) 			return 0;
	
		if (num % 3 == 0) {
			y = min_steps_recur(num / 3);
		}
		if (num % 2 == 0) {
			x = min_steps_recur(num / 2);
		}
		z = min_steps_recur(num - 1);
		return 1 + (min(x, y, z));
	}

	
	public static int min_steps_dyn(int num) {
		int x = 0, y = 0, z = 0;
		if (num <= 1) 			return 0;
		
		int[] rs = new int[num + 1];
		
		rs[0] = 0;
		rs[1] = 0;

		
		for(int i = 2; i<= num; i++){
			
			if(i%3 == 0) x = rs[i/3];
			else x = Integer.MAX_VALUE;
			if(i%2 == 0) y = rs[i/2];
			else y = Integer.MAX_VALUE;

			z = rs[i-1];
			
			rs[i] = 1 + min(x,y,z);
			
		}
		return rs[num];
	}
	/**
	 * 
	 * @function: minimumal bumber from three integers
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @author: Rambo Zhu     16 Oct 2017 11:02:14 pm
	 */
	public static int min(int x, int y, int z) {
		x = (x <= y ? x : y);
		return x <= z ? x : z;
	}

}
