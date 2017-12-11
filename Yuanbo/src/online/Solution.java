/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * online -> Solution.java
 * Created on 27 Nov 2017-11:35:44 am
 */
package online;

import java.util.Scanner;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  27 Nov 2017 11:35:44 am
 */
public class Solution {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     27 Nov 2017 11:35:44 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		countOne(5);
		countOne(0);
		countOne(11);
		
		Scanner scan = new Scanner(System.in);
		String s  = scan.nextLine();
		
		System.out.println(s + "first unique char " + firstUniqChar("aabbcddeef"));

	}
	
	static int countOne(Integer n){
		
		int count = 0;
		while(n != 0){
			count += (n & 1);
			n >>>= 1;
		}
		System.out.println("number of one is " + count);
		return count;
	}

	
	static int firstUniqChar(String s) {
		
		int[] freq = new int[26];
		int len = s.length();
		for(int i =0; i < len; i++){
			int m = s.charAt(i) - 'a';
			freq[m]++;
		}
		
		for(int i =0; i < len; i++){
			int m = s.charAt(i) - 'a';
			if(freq[m] == 1)
				return i;
		}

		return -1;
	}
}
