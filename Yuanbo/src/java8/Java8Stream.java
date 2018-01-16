/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * java8 -> Java8Stream.java
 * Created on 11 Dec 2017-2:47:05 pm
 */
package java8;

import java.util.ArrayList;
import java.util.List;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  11 Dec 2017 2:47:05 pm
 */
public class Java8Stream {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     11 Dec 2017 2:47:05 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Transaction> transactions = new ArrayList();
		List<Integer> numbers = new ArrayList();
		
		
		int sum = numbers.parallelStream().reduce(0, (a, b) -> a + b);

	}

	
	
	
	final class Transaction{
		int id;
		int value;
		String type;
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
}
