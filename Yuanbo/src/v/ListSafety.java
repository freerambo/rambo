package v;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ListSafety {

	
	
	public static void main(String[] args) {
		
		Date d = new Date();
		System.out.println(d.getTime());
		List<String> ls = new ArrayList<String>();
		ls.add("1");
		ls.add("2");
		ls.add("3");
		ls.add("4");
		ls.add("5");
		listIterate(ls);
	}

	/**
	 * 
		1
		2
		Exception in thread "main" java.util.ConcurrentModificationException
			at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
			at java.util.ArrayList$Itr.next(ArrayList.java:859)
			at v.ListSafety.listIterate(ListSafety.java:26)
			at v.ListSafety.main(ListSafety.java:20)

	 * @param ls
	 */
	static void listForEach(List<String> ls){
		for(String s  : ls){
			if("3".equals(s))	ls.remove(s);
			else 
				System.out.println(s);
		}
	}
	
	/**
	    1 - 5
		2 - 5
		5 - 4
	
	Wrong output 
	
	 * @param ls
	 */
	static void listForLoop(List<String> ls){
		for(int i = 0; i < ls.size(); i++){
			String s = ls.get(i);
			if("3".equals(s))	ls.remove(s);
			else 
				System.out.println(s + " - " + ls.size());
		}
	}
	
	
	/**
	 * 
	 * 
		1 - 5
		2 - 5
		4 - 4
		5 - 4
		
		Expected output 
	 * @param ls
	 */
	static void listIterate(List<String> ls){
		Iterator<String> i = ls.iterator();
		
		do{
			String s = i.next();
			if("3".equals(s))	i.remove();  // Iterator remove instead of list it self
			else 
				System.out.println(s + " - " + ls.size());
		}while(i.hasNext());
	}
}
