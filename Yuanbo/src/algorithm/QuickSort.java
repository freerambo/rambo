/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> ＱｕｉｃｋＳｏｒｔ.java
 * Created on 10 Aug 2017-6:30:28 pm
 */
package algorithm;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  10 Aug 2017 6:30:28 pm
 */
public class QuickSort {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     10 Aug 2017 6:30:28 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] s ={3,6,4,5,1,2,7,9};
		for(int i : s){
			System.out.print(i + " ");
		}
		
		System.out.println();
		int l = 0, r = s.length -1;
		new QuickSort().quickSort(s, l, r);
		
		for(int i : s){
			System.out.print(i + " ");
		}
	}

	
	void quickSort(int s[], int l, int r){
		if(l<r){
			int i = l, j = r, x = s[l];
			while(i<j){
				while(i<j && s[j] > x)
					j--;
				if(i < j)
					s[i++] = s[j];
				while(i<j && s[i] < x)
					i++;
				if(i<j)
					s[j--] = s[i];
			}
			s[i] = x;
			
			quickSort(s, l, i-1);
			quickSort(s, i+1, r);
			
		}
		
		
		
	}

	
}
