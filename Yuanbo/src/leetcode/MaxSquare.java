/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> MaxSquare.java
 * Created on 22 Nov 2017-10:40:45 am
 */
package leetcode;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  22 Nov 2017 10:40:45 am
 */
public class MaxSquare {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     22 Nov 2017 10:40:45 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] matrix = {{1,1,1},{1,1,1},{1,1,1}};
		System.out.println(matrix[0].length);
		System.out.println(maxSquare(matrix));
	}
	
    /**
     * @param matrix: a matrix of 0 and 1
     * @return: an integer
     */
    public static int maxSquare(int[][] matrix) {
    	
    	int n = matrix.length;
    	
    	int rs = 0;
    	
    	int m = 0;
    	if(0 > n) return rs;
    	else m = matrix[0].length;
    	
    	int[][] res = new int[n][m];
    	
    	for(int i = 0; i < n; i++){
    		res[i][0] = matrix[i][0];
    		rs = Integer.max(rs, res[i][0]);
            for(int j = 1; j < m; j++) {
            	if(i > 0)
	            	if(matrix[i][j] > 0)
	            		res[i][j] = 1 + Integer.min(res[i][j-1], Integer.min(res[i-1][j],res[i-1][j-1]));
	            	else
	            		res[i][j] = 0;
            	else
            		res[i][j] = matrix[i][j];
            	
            	rs = Integer.max(rs, res[i][j]);
            }
            
    	}
    	return rs*rs;
    }

}
