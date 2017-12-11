/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> ConcurrentHapTest.java
 * Created on 15 Nov 2017-3:28:57 pm
 */
package interview;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * function description：  http://blog.csdn.net/qq_27093465/article/details/52279473
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  15 Nov 2017 3:28:57 pm
 */
public class ConcurrentHapTest {

	/**
	 * Hash algorith 
	 * 
	 * HashTable -- Sysnchronized  访问HashTable的线程都必须竞争同一把锁
	 * ConcurrentHashMap lock 锁分段技术，首先将数据分成一段一段的存储，然后给每一段数据配一把锁
	 * 当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     15 Nov 2017 3:28:57 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ConcurrentHashMap map = null;
        Hashtable ht = null;
        float loadFactor = 0.87f;
        Float.isNaN(loadFactor);
	}
	
	

}
