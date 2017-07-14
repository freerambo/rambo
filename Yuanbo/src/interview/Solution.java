/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> asd.java
 * Created on 4 Jul 2017-5:00:06 pm
 */
package interview;

import java.util.Random;

/**
 * 
 * A zero-indexed array A consisting of N integers is given. An equilibrium
 * index of this array is any integer P such that 0 ≤ P < N and the sum of
 * elements of lower indices is equal to the sum of elements of higher indices,
 * i.e. A[0] + A[1] + ... + A[P−1] = A[P+1] + ... + A[N−2] + A[N−1]. Sum of zero
 * elements is assumed to be equal to 0. This can happen if P = 0 or if P = N−1.
 * 
 * For example, consider the following array A consisting of N = 8 elements:
 * 
 * A[0] = -1 A[1] = 3 A[2] = -4 A[3] = 5 A[4] = 1 A[5] = -6 A[6] = 2 A[7] = 1 P
 * = 1 is an equilibrium index of this array, because:
 * 
 * A[0] = −1 = A[2] + A[3] + A[4] + A[5] + A[6] + A[7] P = 3 is an equilibrium
 * index of this array, because:
 * 
 * A[0] + A[1] + A[2] = −2 = A[4] + A[5] + A[6] + A[7] P = 7 is also an
 * equilibrium index, because:
 * 
 * A[0] + A[1] + A[2] + A[3] + A[4] + A[5] + A[6] = 0 and there are no elements
 * with indices greater than 7.
 * 
 * P = 8 is not an equilibrium index, because it does not fulfill the condition
 * 0 ≤ P < N.
 * 
 * Write a function:
 * 
 * class Solution { public int solution(int[] A); }
 * 
 * that, given a zero-indexed array A consisting of N integers, returns any of
 * its equilibrium indices. The function should return −1 if no equilibrium
 * index exists.
 * 
 * For example, given array A shown above, the function may return 1, 3 or 7, as
 * explained above.
 * 
 * Assume that:
 * 
 * N is an integer within the range [0..100,000]; each element of array A is an
 * integer within the range [−2,147,483,648..2,147,483,647]. Complexity:
 * 
 * expected worst-case time complexity is O(N); expected worst-case space
 * complexity is O(N), beyond input storage (not counting the storage required
 * for input arguments). Elements of input arrays can be modified. function
 * description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 4 Jul 2017 5:00:06 pm
 */
public class Solution {
	public static void main(String[] args) {
		// write your code in Java SE 8

		int[] a = { 1, 2, 0, -3, 1 };

//		System.out.print(xorTest(7, 9));
		
		for(int i =0; i < 100; i++){
			System.out.println(randMTorandN(17, 19));

		}

		
//		double result = Math.pow(5, 6);
//		System.out.println(result);

//		System.out.println("times of N : "+ Math.round(Math.log(result-1)/Math.log(7)));
//		double time = Math.pow(result, );
		
//		Math.l
		
		// System.out.print(equi(a,a.length));

	}

	/*
	 * 
	 * if m<=n, f(m, n) = m xor m+1 xor m+2 xor ... xor n-1 xor n
	 * 
	 * As binary bit operation, from 0 xor to 2^k -1, the results will be zero.
	 * (k>=2)
	 * 
	 * so f(0, n) = f(0, 2^k - 1) xor f(2^k, n) = f(2^k, n)
	 * 
	 * 
	 * f(0, n) = f(1, n) = { n , n%4==0; 1 , n%4==1; n+1 , n%4==2; 0 , n%4==3; }
	 * 
	 */
	public static int xorTest(int m, int n) {
		// write your code in Java SE 8
		if (m < 0 || n < 0)
			return -1;
		if (m > n)
			return -1;
		int[] xorResults;
		if (m % 2 == 0)
			xorResults = new int[] { n, 1, n ^ 1, 0 };
		else
			xorResults = new int[] { m, m ^ n, m - 1, (m - 1) ^ n };

		return xorResults[(n - m) % 4];
	}

	public static int equi(int arr[], int n) {
		if (n == 0)
			return -1;
		long sum = 0;
		int i;
		for (i = 0; i < n; i++)
			sum += (long) arr[i];

		long sum_left = 0;
		for (i = 0; i < n; i++) {
			long sum_right = sum - sum_left - (long) arr[i];
			if (sum_left == sum_right)
				return i;
			sum_left += (long) arr[i];
		}
		return -1;
	}
	
	
	public static long randMTorandN(int m, int n) {

		Random r = new Random();
		
		int num = getM_N(m,n);
		System.out.println("times of " + m +" is " + num);
		long result = 0;
		for(int i=1; i <= num; i++ ){
			int r1 = r.nextInt(m) + 1;
			result = result * m + r1;
		}

		
		System.out.println("result --> " + result);
		
		
		return result % n + 1;

//		return (int)( Math.round(Math.log(result)/Math.log(times)));
	}
	
	
	public static int getM_N(int m, int n) {
		if(m < 1) return -1;
		if(m > n) return -1;
		int i=1;
		long powerM = m;
		while(powerM%n > 1){
			powerM *= m;
			i++;
		}
		return i;
	}
	
	public static int getTimeofN(double results, int n) {
		return (int) Math.round(Math.log(results-1)/Math.log(n));
	}


}
