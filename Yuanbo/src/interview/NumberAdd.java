/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> NumberAdd.java
 * Created on 9 Oct 2017-5:52:51 pm
 */
package interview;

import java.math.BigInteger;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  9 Oct 2017 5:52:51 pm
 */
public class NumberAdd {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     9 Oct 2017 5:52:52 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int i = Integer.MAX_VALUE;
		int j = Integer.MAX_VALUE;
		System.out.println(Integer.toBinaryString(i));

		BigInteger bj = null;
		
		System.out.println(Integer.toBinaryString(j));

		Integer c = i + j;
		
		System.out.println(Integer.toBinaryString(c));
		
	}

}
