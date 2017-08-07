/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> LongestPalindrome.java
 * Created on 19 Jul 2017-11:49:13 am
 */
package leetcode;
import static java.lang.System.*;
/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Jul 2017 11:49:13 am
 */
public class LongestPalindrome {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     19 Jul 2017 11:49:13 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			out.println(new LongestPalindrome().longestPalindrome("acbhbjjbcajsd"));
	}
	
	 public String longestPalindrome(String s) {
	 
		 int start = 0, end = 0;
		 for(int i = 0; i < s.length(); i++){
			 
			 int len1 = expandAroundCenter(s, i,i);

			 int len2 = expandAroundCenter(s, i,i+1);
			 
	         int len = Math.max(len1, len2);

			 if(len > end - start){
				 start = i - (len -1) / 2;
				 end = i + len/2;
			 }
				 
		 }
		 
		 return s.substring(start, end+1);
		 
	 }
	 
	 public int expandAroundCenter(String s, int left, int right){
		 int l = left, r = right;
		 while(l >=0 && r < s.length() && s.charAt(l) == s.charAt(r)){
			 l--;
			 r++;
		 }
		 return r-l-1;
	 }

}
