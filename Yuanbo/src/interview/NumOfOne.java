/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> NumOfOne.java
 * Created on 18 Sep 2017-6:14:15 pm
 */
package interview;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  18 Sep 2017 6:14:15 pm
 */
public class NumOfOne {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     18 Sep 2017 6:14:15 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int i = 0b1111111111111111111111111111111;
		int count = 0;
		for(int n = 0; n <= 31; n++){
			
			count += (i & 1);
			i >>>= 1;
		}
		System.out.print(count);
	}

}
