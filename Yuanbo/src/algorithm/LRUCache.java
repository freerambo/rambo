/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> df.java
 * Created on 23 Oct 2017-10:39:30 pm
 */
package algorithm;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*Java implementation using Deque and HashMap    */

interface Cache<K,V> {
    V get(K key) ;
    void set(K key, V value);
}



public class LRUCache <K,V> implements Cache<K,V>{
	
	class Node <K,V>{
	    K key;
	    V value;
	
	    public Node (K key, V value) {
	        this.key = key;
	        this.value = value;
	    }
	}
	
    Deque<Node<K,V>> dq = new LinkedList<>();
    
    Map<K, Node<K, V>> map = new HashMap<>();
    static int SIZE;

    public LRUCache(int size) {
        this.SIZE = size;
    }

    public V get(K key) {
        Node<K,V> result = map.get(key);
        
        if(result != null) {
            dq.remove(result);
            dq.addFirst(result);
            System.out.println("Get " +key +" : " +result.value);
            return result.value;
        }
        else {
            System.out.println("Cache miss");
            return null;
        }
    }

    public void set(K key, V value) {
        System.out.println("Set " +key +" : " +value);
        Node<K,V> result = map.get(key);
        if(result != null) {
            result.value = value;
            map.put(key, result);
            dq.remove(result);
            dq.addFirst(result);
            System.out.println("Updated frame " +key+" as " + value);
        }
        else {
            if(dq.size() == SIZE) {
                Node<K,V> toRemove = dq.removeLast();
                map.remove(toRemove);
                System.out.println("Frame removed " +toRemove.key +" : " +toRemove.value);
            }
            Node<K,V> newNode = new Node(key, value);
            dq.addFirst(newNode);
            map.put(key, newNode);
            System.out.println("Frame added " + key + " : " +value);
        }
    }

    public static void main(String[] args) {
        Cache<Integer, Integer> lru = new LRUCache<>(5);
        lru.get(2);
        lru.set(1, 11);
        lru.set(2, 22);
        lru.get(2);
        lru.set(3, 33);
        lru.set(4, 44);
        lru.set(5, 55);
        lru.get(2);
        lru.get(1);
        lru.set(6, 66);
    }
}