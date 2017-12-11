/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> TopKRequest.java
 * Created on 19 Jul 2017-4:22:27 pm
 */
package leetcode;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 19 Jul 2017 4:22:27 pm
 */
public class TopKRequest {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu 19 Jul 2017 4:22:27 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] a = { 1, 2,1,3,5,6,1,5 };
		out.print(topKFrequent(a, 2));

	}

	public static List<Integer> topKFrequent(int[] nums, int k) {

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int n : nums) {
			map.put(n, map.getOrDefault(n, 0) + 1);
		}

		List<Integer> list = new ArrayList<Integer>();

		List<Integer>[] bucket = new List[nums.length + 1];

		for (Integer i : map.keySet()) {

			int freq = map.get(i);
           if(bucket[freq] == null)
        	   bucket[freq] = new ArrayList<Integer>();
			bucket[freq].add(i);
		}

		List<Integer> res = new ArrayList<>();

		for (int i = bucket.length - 1; i >= 0 && res.size() < k; i--) {

			if (bucket[i] != null) {
				res.addAll(bucket[i]);
			}

		}
		return res;
	}

}

// use treeMap. Use freqncy as the key so we can get all freqencies in order
class Solution1 {
	public List<Integer> topKFrequent(int[] nums, int k) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int n : nums) {
			map.put(n, map.getOrDefault(n, 0) + 1);
		}

		TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
		for (int num : map.keySet()) {
			int freq = map.get(num);
			if (!freqMap.containsKey(freq)) {
				freqMap.put(freq, new LinkedList<>());
			}
			freqMap.get(freq).add(num);
		}

		List<Integer> res = new ArrayList<>();
		while (res.size() < k) {
			// Removes and returns a key-value mapping associated with the
			// greatest key in this map, or null if the map is empty.
			Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
			if (entry != null && entry.getValue() != null)
				res.addAll(entry.getValue());
		}
		return res;
	}
}
