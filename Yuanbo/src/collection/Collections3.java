
/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Collections3 {

	public static void main(String[] args) {

		List<String> la = new ArrayList<String>();
		List<String> lb = new ArrayList<String>();
		la.add("a");
		la.add("b");
		lb.add("b");
		lb.add("c");
		List<String> c = union(la,lb);
		System.out.println(c.size());

	}

	/**
	 * 杞崲Collection鎵�鏈夊厓绱�(閫氳繃toString())涓篠tring,
	 * 姣忎釜鍏冪礌鐨勫墠闈㈠姞鍏refix锛屽悗闈㈠姞鍏ostfix锛屽<div>mymessage</div>銆�
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 鍒ゆ柇鏄惁涓虹┖.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * 鍒ゆ柇鏄惁涓虹┖.
	 */
	public static boolean isEmpty(Map map) {
		return (map == null) || map.isEmpty();
	}

	/**
	 * 鍒ゆ柇鏄惁涓虹┖.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null) && !(collection.isEmpty());
	}

	/**
	 * 鍙栧緱Collection鐨勭涓�涓厓绱狅紝濡傛灉collection涓虹┖杩斿洖null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 鑾峰彇Collection鐨勬渶鍚庝竴涓厓绱� 锛屽鏋渃ollection涓虹┖杩斿洖null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 褰撶被鍨嬩负List鏃讹紝鐩存帴鍙栧緱鏈�鍚庝竴涓厓绱� 銆�
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 鍏朵粬绫诲瀷閫氳繃iterator婊氬姩鍒版渶鍚庝竴涓厓绱�.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 杩斿洖a+b鐨勬柊List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 杩斿洖a-b鐨勬柊List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 杩斿洖a涓巄鐨勪氦闆嗙殑鏂癓ist.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}

}
