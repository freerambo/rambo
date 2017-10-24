/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> PeakElement.java
 * Created on 23 Oct 2017-7:40:08 pm
 */
package algorithm;

/**
 * 
 * Binary search O(nLog(n))
 * 
 这个就是O（n+m）？
比较两个值，那个小move哪个？
A[i]<=A[i+1]
B[j]<=B[j+1]
如果 A[i] 如果A[i+1] 如果A[i+1]>B[j]， 在该移动策略下，无论A[i+1]>B[j+1]与否A[i+1]和B[j],B[j+1
]都会发生比较。
反之同理。
 * 
 * d(i,j) = min(|a[i] - b[j]|, |a[i] - b[j-1]|, |a[i-1] - b[j]|)
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  23 Oct 2017 7:40:08 pm
 */
public class MinDiffOfTwoSortedArray {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     23 Oct 2017 7:40:08 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int[] a = {1};
		int[] b = {4,7,12};
		
		int r = minDiff(a,b);
		
		System.out.println(r);
	}
	
	/**
	 * 逐渐逼近法  O(m+n)
	 * 
	 * @function:
	 * @param a
	 * @param b
	 * @return
	 * @author: Rambo Zhu     23 Oct 2017 8:50:15 pm
	 */
	public static int minDiff(int[] a, int[] b){

		int i = 0, j = 0;
		int min = Integer.MAX_VALUE;
		
		while(i < a.length){
			
			int diff = Math.abs(a[i] - b[j]);
			
			min = Integer.min(diff, min);
			if (min == 0) return min;
			
			if(a[i] > b[j]) j++;
			
			if(a[i] < b[j]) i++;
			
		}
		
		return min;
		
	}

}
