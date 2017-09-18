/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> CompareTest.java
 * Created on 23 Aug 2017-12:22:32 pm
 */
package interview;

import java.util.TreeSet;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  23 Aug 2017 12:22:32 pm
 */

 class Parent implements Comparable {
	private int age = 0;
	public Parent(int age){
		this.age = age;
	}
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		System.out.println("method of parent");
		Parent o1 = (Parent)o;
		return age>o1.age?1:age<o1.age?-1:0;
	}

}

 class Child extends Parent {

	public Child(){
		super(3);
	}
	public int compareTo(Object o) {

			// TODO Auto-generated method stub
			System.out.println("method of child");
//			Child o1 = (Child)o;
			return 1;

	}
}

public class TreeSetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeSet set = new TreeSet();
		set.add(new Parent(3));
		set.add(new Child());
		set.add(new Parent(4));
		System.out.println(set.size());
	}

}
