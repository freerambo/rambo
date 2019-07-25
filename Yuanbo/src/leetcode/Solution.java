/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> Solution.java
 * Created on 25 Jul 2017-11:43:50 am
 */
package leetcode;

import java.util.HashSet;
import java.util.Set;


/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  25 Jul 2017 11:43:50 am
 */
public class Solution {

	/**
	 * 
	 * Reverse digits of an integer.

Example1: x = 123, return 321
Example2: x = -123, return -321

	 * @function:
	 * @param args
	 * @author: Rambo Zhu     25 Jul 2017 11:43:50 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "-1234";
//		System.out.println( s.toCharArray()[0]);
//		System.out.println(  new Solution().reverse(+58787687));
		System.out.println(  (-9) % 5);
		int i = 15;
		System.out.println(Integer.toBinaryString(i) + " = " + Integer.toBinaryString(-i));
		System.out.println(Integer.toBinaryString(i & (-i)));
        int[] res = {0,0};

		System.out.println(new Solution().isPalindrome(-1));

		try {
//			System.out.println(  new Solution().myAtoi("2147483648"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		;
	}

	
	
	  public int reverse1(int x) {
		  
		  int result = 0;
          while(x != 0){
              result = result * 10 + x%10;
              x /= 10;
              if( result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
                  return 0;
          }
        		  
  		  return result; 
		  		  
	   }
	  
	  public int reverse(int x) {
		  

		  if(x > Integer.MAX_VALUE || x < Integer.MIN_VALUE) return 0;
		  String s = Integer.toString(x);
		  int start = 0;
		  int end = s.length();
		  if(x < 0){ start = 1;}
		  
		  return reverseString(s,  start, end); 
		  
		  }
		  
		  
		  
		  
		  public int reverseString(String s,  int start, int end) {
			  
			  char[] array = s.toCharArray();
			  while(start < end -1){
				char c = array[start];
				array[start] = array[end-1];
				array[end-1] = c;
				++start;
				--end;
			  }
			  
			  
			  
			  
			  return Integer.valueOf(String.copyValueOf(array));
			  
		  }
	  
		  /**
		   * Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same, 
		   * where in each step you can delete one character in either string.
		   * @function:
		   * @param word1
		   * @param word2
		   * @return
		   * @author: Rambo Zhu     25 Jul 2017 12:34:05 pm
		   */
		    public int minDistance(String word1, String word2) {
		        
		    	
		    	int[][] dp = new int[word1.length() +1][word2.length() +1];
		    	
		    	for(int i = 0; i <= word1.length(); i++){
		    		
		    		for(int j =0; j <= word2.length(); j++){
		    			if(i == 0 || j == 0){dp[i][j] = 0;}
		    			else {
		    				if(word1.charAt(i-1) == word2.charAt(j-1)){
			    				dp[i][j] = dp[i-1][j-1] + 1;
		    				}else{
			    				dp[i][j] = Integer.max(dp[i][j-1], dp[i-1][j] );
		    				}
		    			}
		    		}
		    	}
		    	
		        int len =  dp[word1.length()][word2.length()];
		    	
		    	return word1.length() + word2.length() - 2 * len;
		    }
		    
		    
		    public int findMaximumXOR(int[] nums) {
		        int max = 0, mask = 0;
		        for(int i = 31; i >= 0; i--){
		            mask = mask | (1 << i);
		            Set<Integer> set = new HashSet<>();
		            for(int num : nums){
		                set.add(num & mask);
		            }
		            int tmp = max | (1 << i);
		            for(int prefix : set){
		                if(set.contains(tmp ^ prefix)) {
		                    max = tmp;
		                    break;
		                }
		            }
		        }
		        return max;
		    }
		    
		    
		    public int myAtoi(String str) {
		    	int index = 0, sign = 1, total = 0;
		        //1. remove spaces
		        str = str.trim();
		       //2. Empty string
		       if(str.length() == 0) return 0;


		       //3. Handle signs
		       if(str.charAt(index) == '+' || str.charAt(index) == '-'){
		           sign = str.charAt(index) == '+' ? 1 : -1;
		           index ++;
		       }
		       
		       //4. Convert number and avoid overflow
		       while(index < str.length()){
		           int digit = str.charAt(index) - '0';
		           if(digit < 0 || digit > 9) break;

		           //check if total will be overflow after 10 times and add digit
		           if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
		               return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

		           total = 10 * total + digit;
		           index ++;
		       }
		       return total * sign;
		    }
		    
		    
		    
		    public boolean isPalindrome(int x) {
		    	if(x < 0) return false;
		    	int m = x;
		    	int rec = 0;
		    	while(m != 0){
		    		rec = rec*10 + m%10;
		    		m = m/10;
		    	}
		    	
		    	return rec == x;
    }
		    
		
		    
//		@Test    
	    public void singleNumber() {
	        
			int[] nums = new int[] {3,3,4,4,4,9,3};
	    	int r = 0;
			for(int i =0; i < 32; i++){
				int sum = 0;

				for(int num: nums){
					if (((num >> i) & 1) == 1){
						sum++;
						if(sum >= 3) sum %= 3;
					}
				}
				r |= (sum << i);
			}
			System.out.println(r);

			
//	    	return r;
	    }
		
		 public boolean isMatch(String s, String p) {
		        
		 
			 if(s == null || p == null) return false;
			 
			 boolean[][] dp = new boolean[s.length()+1][p.length()+1];
			 
			 dp[0][0] = true;
		    for (int i = 0; i < p.length(); i++) {
		        if (p.charAt(i) == '*' && dp[0][i-1]) {
		            dp[0][i+1] = true;
		        }
		    }
		    
			for(int i = 0; i < s.length(); i++) {
				
		        for (int j = 0; j < p.length(); j++) {
		        	
		        	if(s.charAt(i) == p.charAt(j))
		        		dp[i+1][j+1] = dp[i][j];
		        	if(p.charAt(j) == '.')
		        		dp[i+1][j+1] = dp[i][j];
		        	if(p.charAt(j) == '*')
		        		 if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
		                     dp[i+1][j+1] = dp[i+1][j-1];
		                 } else {
		                     dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
		                 }
		        	
		        }
				
			}
			 
			 return dp[s.length()][p.length()];
		 
		 }
		    
		 
		 
		    public int maxArea(int[] height) {

		        int left = 0, right = height.length-1;
		        int max = 0;
		        
		        while(left < right){
		            max = Integer.max(max, (right-left) * Integer.min(height[left], height[right]));
		            if(height[left] < height[right]) left++;
		            else right--;
		            
		        }
		        return max;                  
		    }
		    
		    public void isPowerOfThree() {
		        
		    	int n = 3;
		    	long result = 1;
		        while(result < Integer.MAX_VALUE){
		        	result *= n; 
		        }
		        
		    	System.out.println(result/3);
		    }
		    
		    public boolean isPowerOfThree(int n) {
		        
			       return (n > 0) && (1162261467 % n ==0);
		    }
		    
		    public int lengthOfLastWord(String s) {
		        s = s.trim();
		        return s.length() - s.lastIndexOf(" ") - 1;
		    }
		    
		    
		    public int[] singleNumber(int[] nums) {
		        int xor = 0;
		        for(int num: nums)
		            xor ^= num;
		        
		        xor &= -xor;
		        int[] res = {0,0};
		        for(int num: nums){
		            if((xor & num) == 0) res[0] ^= num;
		            else res[1] ^= num;
		        }
		        
		        return res;
		    }
		    
		    
		    public boolean canJump(int[] nums) {
		        
		        int max = 0; 
		        for(int i = 0; i < nums.length; i++){
		            
		            max = Integer.max(i + nums[i], max);
		            
		            if(max < i) return false;
		        }
		        return true;
		    }
		    
		    
		    public int[] findErrorNums(int[] nums) {
		        
		        int[] s = new int[nums.length];
		        int[] r = new int[2];
		        for(int num: nums){
		            
		            if(s[num-1] != num){
		                s[num-1] = num;
		            }else{
		                r[0] = num;
		            }    
		        }
		         for(int i = 0; i < s.length; i++){
		            if(s[i] == 0)
		                r[1] = i + 1;
		        }
		        
		        return r; 
		        
		    }
		    
}

