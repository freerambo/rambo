/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> MedianOfTwoSortedArray.java
 * Created on 19 Jul 2017-11:26:15 am
 */
package leetcode;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Jul 2017 11:26:15 am
 */
public class MedianOfTwoSortedArray {

	/**
	 * @function:
	 * 
	 * There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

	 * @param args
	 * @author: Rambo Zhu     19 Jul 2017 11:26:15 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] a ={1,3}, b = {2,7,8};
		System.out.println(findMedianSortedArrays(a, b));
	}
	
	public static int findKthSmallest(int[] a, int m, int begin1, int[] b, int n, int begin2, int k) {

		//make sure m is always the shorter array
		if(m > n) return findKthSmallest(b, n, begin2, a, m, begin1, k);

		if(m ==0) return b[begin2 + k -1];

		if(k == 1) return Integer.min(a[begin1], b[begin2]);
		
		int partA = Integer.min(k/2, m);
		int partB = k - partA;
		
		if(a[begin1 + partA -1] < b[begin2 + partB -1]){
			return findKthSmallest(a, m - partA, begin1 + partA, b, n, begin2, k-partA);
		}else
			return findKthSmallest(a, m, begin1, b, n - partB, begin2 + partB, k-partB);

		
	}

	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		
	    int m = nums1.length, n = nums2.length;
	    int sum = m + n;
	    
	    if(sum % 2 == 0)
	    	return (findKthSmallest(nums1,m,0,nums2,n,0,sum/2) + findKthSmallest(nums1,m,0,nums2,n,0,sum/2 + 1))/2.0;
	    else 
	    	return findKthSmallest(nums1,m,0,nums2,n,0,sum/2+1);
	    

	}

}
