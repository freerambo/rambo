/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * excercise -> dsasd.java
 * Created on 8 Nov 2017-12:06:13 pm
 */
package excercise;

/**
Writing programming interview questions hasn't made me rich. Maybe trading Apple stocks will.
Suppose we could access yesterday's stock prices as an array, where:

The indices are the time in minutes past trade opening time, which was 9:30am local time.
The values are the price in dollars of Apple stock at that time.
So if the stock cost $500 at 10:30am, stockPricesYesterday[60] = 500.

Write an efficient method that takes stockPricesYesterday 
and returns the best profit I could have made from 1 purchase and 1 sale of 1 Apple stock yesterday.


 */
public class StockPricesYesterday  {
	public static void main(String[] args) {
		  int[] stockPricesYesterday = new int[] {10,4, 7, 5, 8, 11, 9};

		  getMaxProfit(stockPricesYesterday);
		  // returns 6 (buying for $5 and selling for $11)
	}
		
	
	public static int getMaxProfit(int[] a){
		
		int in = a[0];
		int i =0,out = 0,max=0;
		while(i <  a.length-1)
			if(a[i+1] > in){
					while(i < a.length -1 && a[++i] >= out) out = a[i];
					max = out - in > max? out - in : max;
			}else {
				while(i < a.length -1 && a[++i] < in) in = a[i];
			}
		System.out.println(max + " - " + in + " - " + out);
		return max;
	}
}
