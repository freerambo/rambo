/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> LoggestSubString.java
 * Created on 18 Jul 2017-3:24:00 pm
 */
package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  18 Jul 2017 3:24:00 pm
 */
public class LoggestSubString {

	/**
	 * 
	 * Given a string, find the length of the longest substring without repeating characters.

Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.

Given "bbbbb", the answer is "b", with the length of 1.

Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.


	 * @function:
	 * @param args
	 * @author: Rambo Zhu     18 Jul 2017 3:24:00 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "";
    	System.out.println(lengthOfLongestSubstring(s));

		lengthOfLongestSubstring(s);
	}
    public static int lengthOfLongestSubstring(String s) {
    	
    	if(s == null || s == "") return 0;
    	if(s.length() == 1) return 1;
    	int length = 0;
    	
    	Map<Character, Integer> map = new HashMap<Character, Integer>();
    	
    	for(int i =0, j =0; i < s.length(); i++){
    		Character c =  s.charAt(i);
        	if(map.containsKey(c)){
        		j = Math.max(j, map.get(c));
        	}
        	
        	length = Math.max(length, i-j); 
        	map.put(s.charAt(i),i);
    	}
//    	System.out.println(length);
        return length;
    }
    
    public double findMedianSortedArrays1(int[] A, int[] B) {
	    int m = A.length, n = B.length;
	    int l = (m + n + 1) >> 1;
	    int r = (m + n + 2) >> 1;
	    return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
	}

public double getkth(int[] A, int aStart, int[] B, int bStart, int k) {

	
	
	
	if(k == 1) return Math.min(aStart, bStart);
	
	
	int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE; 
	
	if((aStart + k/2 -1) < A.length) aMid = A[aStart + k/2 -1];
	if((bStart + k/2 -1) < A.length) aMid = A[bStart + k/2 -1];
	
	if(aMid < bMid) return getkth(A, aStart + k/2, B, bStart, k -k/2);
	else return getkth(A, aStart, B, bStart + k/2, k -k/2);

	
	
	
	
}


public static int findKthSmallest(int[] a, int m, int begin1, int[] b, int n, int begin2, int k) {

	if (m > n)
		return findKthSmallest(b, n, begin2, a, m, begin1, k);
	if (m == 0)
		return b[begin2 + k - 1];
	if (k == 1)
		return Integer.min(a[begin1], b[begin2]);
	
	int partA = Integer.min(k / 2, m), partB = k - partA;
	if (a[begin1 + partA - 1] == b[begin2 + partB - 1])
		return a[begin1 + partA - 1];
	else if (a[begin1 + partA - 1] > b[begin2 + partB - 1])
		return findKthSmallest(a, m, begin1, b, n - partB, begin2 + partB, k - partB);
	else
		return findKthSmallest(a, m - partA, begin1 + partA, b, n, begin2, k - partA);

}

public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
	int len1 = nums1.length, len2 = nums2.length, sumLen = len1 + len2;
	if (sumLen % 2 != 0) {

		return findKthSmallest(nums1, len1, 0, nums2, len2, 0, sumLen / 2 + 1);
	} else {
		return (findKthSmallest(nums1, len1, 0, nums2, len2, 0, sumLen / 2)
				+ findKthSmallest(nums1, len1, 0, nums2, len2, 0, sumLen / 2 + 1)) / 2.0;
	}

}

}
