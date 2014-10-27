package Test.src;

import java.util.*;

public class ListDemo {

	public static void main(String[] args) {
		List list = new ArrayList();

//		list.add("first");
//		list.add(new Integer(100));
//		list.add(new Object());
//		list.add(new Boolean(true));
//		list.add(new ListDemo());
//		list.add(10);
//		list.add("first");
		list.add("first");
		list.add("hkjhk");
		list.add("tuohj");
		list.add("kihtg");
		list.add("fihitiu");
		list.add("qqw10");
		list.add("first");
		
		System.out.println(list);
		System.out.println(list.size());
		System.out.println(list.get(3));
		System.out.println("******************");
		// 1.5 after
		for (Object obj : list) {
			System.out.println(obj);
		}
		System.out.println("******************");
		// method 3 µü´úÆ÷
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			System.out.println(obj);
		}
		System.out.println("@#%$^&*(******************");
		for (Iterator it1 = list.iterator(); it1.hasNext();) {
			System.out.println(it1.next());
		}
		System.out.println("@#%$^&*(******************");
	
		
		List vec = new Vector();
		vec.add("tigger");
		vec.add(new Object());
		vec.add(new Boolean(true));
		vec.add(new Test());
		vec.add("tigger");
		System.out.println("******************");
		for (Object obj : vec) {
			System.out.println(obj);
		}
		System.out.println("******************");
		vec.remove(2);
		System.out.println(vec.contains("tigger"));
		for (Object obj : vec) {
			System.out.println(obj);
		}
	}

}
