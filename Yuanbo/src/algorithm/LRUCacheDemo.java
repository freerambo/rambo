/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> sd.java
 * Created on 23 Oct 2017-10:44:56 pm
 */
package algorithm;
/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LRUCacheDemo<K, V> extends LinkedHashMap<K, V> {
	public static final int DEFAULT_CACHE_SIZE = 100;
	private static final long serialVersionUID = -3090703237387586885L;
	private int cacheSize;

	public LRUCacheDemo() {
		this(100);
	}

	public LRUCacheDemo(int cacheSize) {
		super(16, 0.75F, true);
		this.cacheSize = cacheSize;
	}

	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return (size() > this.cacheSize);
	}
}