/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> KMPAlgorithm.java
 * Created on 16 Oct 2017-3:25:11 pm
 */
package algorithm;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  16 Oct 2017 3:25:11 pm
 */
public class KMPAlgorithm {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     16 Oct 2017 3:25:11 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "abacaabaccabacabaabb";
		String b = "abacab";
		char[] text = s.toCharArray();
		char[] pattern = b.toCharArray();

		System.out.println(KMP(text,pattern));
		
	}

	
	public static int KMP(char[] text, char[] pattern){
		int n = text.length;
		int m = pattern.length;
		int i= 0, j=0; 
		for( i = 0; i < n; i++) {
			    for( j = 0; j < m && i + j < n; j++) 
			      if(text[i + j] != pattern[j]) break;
			      // mismatch found, break the inner loop
			    if(j == m) // match found
			    	return i;
	
		}
		return -1;
	}			 
}
