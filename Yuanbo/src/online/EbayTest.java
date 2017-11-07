/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * online -> EbayTest.java
 * Created on 26 Oct 2017-2:00:32 pm
 */
package online;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  26 Oct 2017 2:00:32 pm
 */
public class EbayTest {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     26 Oct 2017 2:00:32 pm
	 */
	public static void main(String[] args) {
		String a = "10010";
		String b =  "1011";
//		System.out.println(('0' - '0'));
		sum1(a,b);
	}
	
	
	static String sum(String a, String b){
		
		int la = a.length();
		int lb = b.length();
		
		if(la < lb) sum (b,a);
		char[] result = new char[la + 1];
		
//		result.toString();
		char rem = '0';
		
		for(int i = 0; i < la; i++){
			char bi = '0';
			if(i < lb)
				bi = b.charAt(i);
				
			char ai = a.charAt(i);
			
			if(rem == '1')
				if(a.charAt(i) == '1'){
					ai = '0';
					rem = '1';
				} else if(a.charAt(i) == '0'){
					rem = '0';
					ai = '1';
				} 
			
			if(ai == '0'){
				result[i] = bi;
			}
			else if(ai == '1'){
				if(bi == '0'){
					result[i] = '1';
					rem = '0';
				} else if(bi == '1'){
					result[i] = '0';
					rem = '1';
				}else{
					throw new IllegalArgumentException();
				}	
			}else{
				throw new IllegalArgumentException();
			}	
		}
		result[la] = rem;
		return String.copyValueOf(result);
	}

	
static void sum1(String a, String b){
		
		int la = a.length();
		int lb = b.length();
		
		if(la < lb) sum (b,a);

		int[] result = new int[la + 1];

		int rem = 0;
		int temp = 0;

		
		for(int i = 0; i < la; i++){
			
			int bi = 0;
			if(i < lb)
				bi = (int) (b.charAt(lb-i-1) - '0');
				
			int ai = (int) (a.charAt(la-i-1) - '0');
			

			if(bi > 1 || bi < 0 || ai > 1 || ai < 0)
				throw new IllegalArgumentException();

			temp = ai;
			ai ^= rem;
			rem &= temp;
			temp = bi;
			bi ^= ai;
			rem = (int) (rem | (ai & temp));
			
			result[i] = bi;
			
		}
		result[la] = rem;
		for(int rb=la-1; rb>=0; rb--)
			System.out.print(result[rb]);
	}
}
