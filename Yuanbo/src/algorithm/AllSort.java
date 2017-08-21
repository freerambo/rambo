/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> AllSort.java
 * Created on 11 Aug 2017-9:44:32 pm
 */
package algorithm;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  11 Aug 2017 9:44:32 pm
 */
public class AllSort {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     11 Aug 2017 9:44:32 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] n ={3,6,4,5,1,2,7,9};
//		bubbleSort(n);
//		slectSort(n);
		insertSort(n);
	}

	public static void swap(int[] n, int i, int j){
		int temp = n[i];
		n[i] = n[j];
		n[j] = temp;
	}

	public static void bubbleSort(int[] n){
		
		for(int i = n.length - 1; i > 0; i--){
			
			for(int j = 0; j < i; j++){
				if(n[j] > n[j+1]){
					swap(n,j,j+1);
				}
			}
		}
		for(int i : n){
			System.out.print(i + "-");
		}
	}
	
	public static void slectSort(int[] n){
		
		for(int i = 0; i < n.length-1; i++){
			int min = n[i];
			int index = i;
			for(int j = i+1; j < n.length; j++){
				if(n[j] < min){
					min = n[j];
					index = j;
				}
			}
			if(i != index)	swap(n, i, index);
		}
		for(int i : n){
			System.out.print(i + "-");
		}
	}
	
//	int[] n ={3,6,4,5,1,2,7,9};

	public static void insertSort(int[] n){
			
			for(int i = 1; i < n.length; i++){
				int curr = n[i];
				int j = i-1;
				while( j >= 0 && n[j] > curr){
//					System.out.print(j + "-" + n[j] + " ");
					n[j+1] = n[j];
					j--;
				}
//				swap(n, i, j+1);
				n[j+1] = curr;

			}
			for(int i : n){
				System.out.print(i + "-");
			}
		}
}
