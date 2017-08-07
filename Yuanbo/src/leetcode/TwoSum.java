/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> TwoSum.java
 * Created on 18 Jul 2017-10:27:11 am
 */
package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  18 Jul 2017 10:27:11 am
 * 
 * 
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:
Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].


 */
public class TwoSum {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     18 Jul 2017 10:27:11 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int[] nums = { 3,3,4};
		int  target = 6;
//		twoSum(nums,  target);
//		twoSumHashMap(nums,  target);
		twoSumHashMapOne(nums,  target);
	}
	/**
	 * Complexity Analysis

Time complexity : O(n^2)O(n
​2
​​ ). For each element, we try to find its complement by looping through the rest of array which takes O(n)O(n) time. Therefore, the time complexity is O(n^2)O(n
​2
​​ ).

Space complexity : O(1)O(1).

	 * @function:
	 * @param nums
	 * @param target
	 * @return
	 * @author: Rambo Zhu     18 Jul 2017 10:47:35 am
	 */
    public static int[] twoSum(int[] nums, int target) {
        
    	if(nums == null || nums.length < 2){
    		return null;
    	}
    	
    	for(int i =0; i < nums.length-1;i++){
    		for(int j =i+1; j < nums.length; j++ ){
//    			System.out.println(i +" - " + j );
    			if(nums[i] + nums[j] == target){
//    				System.out.println(i +", " + j);
    				return new int[]{i, j};
    			}	
        	}
    	}
    	
    	return null;
    }

    /**
     * Complexity Analysis:

Time complexity : O(n)O(n). We traverse the list containing nn elements exactly twice. Since the hash table reduces the look up time to O(1)O(1), the time complexity is O(n)O(n).

Space complexity : O(n)O(n). The extra space required depends on the number of items stored in the hash table, which stores exactly nn elements.
     * @function:
     * @param nums
     * @param target
     * @return
     * @author: Rambo Zhu     18 Jul 2017 11:01:12 am
     */
   public static int[] twoSumHashMap(int[] nums, int target) {
        
    	if(nums == null || nums.length < 2){
    		return null;
    	}
    	
    	Map<Integer,Integer> map = new HashMap<Integer,Integer>();
    	for(int i =0; i < nums.length;i++){
    		map.put(nums[i], i);
    	}
    	for(int i =0; i < nums.length;i++){
    		
    		int complement = target - nums[i];
    		if(map.containsKey(complement) && map.get(complement) != i){
    			System.out.println(i +", " + map.get(complement));
    			return new int[] {i, map.get(complement)};
    		}
    	}
        throw new IllegalArgumentException("No two sum solution");
    }
   
   public static int[] twoSumHashMapOne(int[] nums, int target) {
       
   	if(nums == null || nums.length < 2){
   		return null;
   	}
   	
   	/**
   	 * Complexity Analysis:

Time complexity : O(n)O(n). We traverse the list containing nn elements only once. Each look up in the table costs only O(1)O(1) time.

Space complexity : O(n)O(n). The extra space required depends on the number of items stored in the hash table, which stores at most nn elements.
   	 */
   	Map<Integer,Integer> map = new HashMap<Integer,Integer>();
   	for(int i =0; i < nums.length;i++){
   		
   		int complement = target - nums[i];
   		if(map.containsKey(complement)){
   			System.out.println(map.get(complement) +", " + i);
   			return new int[] {map.get(complement), i};
   		}
   		map.put(nums[i], i);
   	}
    throw new IllegalArgumentException("No two sum solution");
   }
}
