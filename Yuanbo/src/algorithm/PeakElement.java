/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> PeakElement.java
 * Created on 23 Oct 2017-7:40:08 pm
 */
package algorithm;

/**
 * function description：
 * 
 * Given an array of integers. Find a peak element in it. An array element is peak if it is NOT smaller than its neighbors. For corner elements, we need to consider only one neighbor. For example, for input array {5, 10, 20, 15}, 20 is the only peak element. For input array {10, 20, 15, 2, 23, 90, 67}, there are two peak elements: 20 and 90. Note that we need to return any one peak element.

Following corner cases give better idea about the problem.
1) If input array is sorted in strictly increasing order, the last element is always a peak element. For example, 50 is peak element in {10, 20, 30, 40, 50}.
2) If input array is sorted in strictly decreasing order, the first element is always a peak element. 100 is the peak element in {100, 80, 60, 50, 20}.
3) If all elements of input array are same, every element is a peak element.
 *
 *
 *A. Linear solution O(n)
 *
 *B. divide and conquer O(logn)
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  23 Oct 2017 7:40:08 pm
 */
public class PeakElement {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     23 Oct 2017 7:40:08 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int[] a = {4,2};
		
		int r = FindPeak(a,0,a.length-1);
		
		System.out.println(r);
	}
	
	public static int FindPeak(int[] a, int l, int r){
//		if(l == r) return a[l];
		
		if(r-l <= 1) return a[l] > a[r]?a[l]:a[r];
				
		int m = (l+r)/2;
			
		if(a[m] >= a[m-1] && a[m] >= a[m+1])
			return a[m];
		
		if(a[m] < a[m-1]){
			return FindPeak(a, l, m-1);
		}
		
		return FindPeak(a, m+1,r);
	}

}
