package Test.src;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestMap {

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("first");
		list.add("hkjhk");
		list.add("tuohj");
		list.add("first");

		for (Object obj : list) {
			System.out.println(obj);
		}

		Hashtable map = new Hashtable();
		map.put("1", "a");
		map.put("2", "b");
		map.put("4", "d");
		map.put("3", "c");
		
		System.out.println(map.get("1"));
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) map.get(key);
			System.out.println(key + " - " + value);
		}
	}

}
