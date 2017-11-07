/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> IntegerBreak.java
 * Created on 21 Jul 2017-11:41:00 am
 */
package leetcode;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  21 Jul 2017 11:41:00 am
 */
public class IntegerBreak {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     21 Jul 2017 11:41:00 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int i = new IntegerBreak().breakInteger(10);
		System.out.println(i);
		
	}
	
	/**
	 * rs [i] = max(rs[k]*rs[i-k], k * (i-k), k * rs[i-k], rs[k] * (i-k))
	 * There is a simple O(n) solution to this problem.
	 * You may check the breaking results of n ranging from 7 to 10 to discover the regularities.
	 * 
	 * @function:
	 * @param n
	 * @return
	 * @author: Rambo Zhu     21 Jul 2017 11:44:40 am
	 */
	public int breakInteger(int n){

		
		if(n <= 2) return -1;
		
		int[] rs = new int[n + 1];

		rs[1] = 1;
		rs[2] = 1;
		
		for(int i=3; i <= n; i++){
			int max = Integer.MIN_VALUE;
			for(int k = 1; k <= i/2; k++){
				max = Integer.max(rs[k] * rs[i-k], rs[k] * (i-k));
				max = Integer.max(k * rs[i-k], max);
				max = Integer.max(k * (i-k), max);
			}
			rs[i] = max;
		}
		return rs[n];
	}
	
	
	public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;//用不着，随便设
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            int max = Integer.MIN_VALUE;
            for (int k = 1; k <= i / 2; k++) {
                int tmpMax = Math.max(dp[k] * dp[i - k], k * (i - k));
                tmpMax = Math.max(tmpMax, dp[k] * (i - k));
                tmpMax = Math.max(tmpMax, dp[i - k] * k);
                max = Math.max(tmpMax, max);
            }
            dp[i] = max;
        }
        return dp[n];
    }
	
    public String convert(String s, int numRows) {
        
        int length = s.length();
        if(length <= numRows) return s;
        if(1 >= numRows) return s;
        StringBuffer[] sb = new StringBuffer[length];

        for(int i=0; i < length; i++){
        	sb[i] = new StringBuffer();
        }
        
        
        int index = 0;
        int incres = 1;
        for(int i = 0; i < length; i++){
            
            sb[index].append(s.charAt(i));
            
            if(index == 0){incres = 1;}
            if(index == numRows -1){incres = -1;}
            
            index += incres;
        }
        String rs = "";
        for(StringBuffer ss: sb ){
        	rs += ss.toString();
        }
        return rs;
    }

}
