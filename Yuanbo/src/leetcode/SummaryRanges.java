/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> sddsa.java
 * Created on 6 Nov 2017-11:49:09 am
 */
package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * function description：
 * 
 * Given a sorted integer array without duplicates, return the summary of its ranges.

Example 1:
Input: [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]
Example 2:
Input: [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]

 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  6 Nov 2017 11:49:09 am
 */
public class SummaryRanges {

	
	  public static List<String> summaryRanges(int[] nums) {
		  List<String> ls = new ArrayList<String>();

	       if(nums == null || nums.length ==0) return ls;
		  int start = 0;
		  int len = nums.length;
		  for(int i = nums[start]; start < len && i <= nums[len-1];){
			  while(start+1 < len && nums[start+1] == nums[start] + 1 ){
				  start++;
			  }
			  if(i == nums[start]) 
				  ls.add(""+i);
			  else
				  ls.add(i+"->"+nums[start]);
			  
			  if(start+1 < len)
				  i = nums[++start];
			  else
				  break;
		  }
		  return ls;
	  }
	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     6 Nov 2017 11:49:09 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {-1};
		summaryRanges(nums).forEach((n)->System.out.println(n));
		
	}

}
