/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * java8 -> MapTest.java
 * Created on 25 Oct 2017-3:03:22 pm
 */
package java8;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  25 Oct 2017 3:03:22 pm
 */
public class MapTest {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     25 Oct 2017 3:03:22 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("语文", 1);
		map.put("数学", 2);
		map.put("英语", 3);
		map.put("历史", 4);
		map.put("政治", 5);
		map.put("地理", 6);
		map.put("生物", 7);
		map.put("化学", 8);
		for(Entry<String, Integer> entry : map.entrySet()) {
		    System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

}
