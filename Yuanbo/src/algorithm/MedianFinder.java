/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> sadas.java
 * Created on 16 Oct 2017-11:20:04 pm
 */
package algorithm;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  16 Oct 2017 11:20:04 pm
 */
public class MedianFinder {

    static PriorityQueue<Integer> small = new PriorityQueue();
    static PriorityQueue<Integer> large = new PriorityQueue();

    public static void addNum(int num) {
        large.add(num);
        small.add(-large.poll());
        if (large.size() < small.size())
            large.add(-small.poll());
    }

    public static double findMedian() {
        return large.size() > small.size()
               ? large.peek()
               : (large.peek() - small.peek()) / 2.0;
    }
    
    public static void main(String[] args){
    	
    	for(int i = 0; i < 1; i++){
    		Integer l = new Random().nextInt(1000);
//    		large.add(l);
    		addNum(l);
    	}
		System.out.println(large.peek());

    	while(!large.isEmpty()){
    		System.out.print(large.poll() + " -> ");
    	}
		System.out.println();

    	while(!small.isEmpty()){
    		System.out.print(small.poll() + " -> ");
    	}
    	
    	
    }
}
