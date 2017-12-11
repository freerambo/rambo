package interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
Question 1: 
In HashMap: all keys must be a string, while value can be any type 
 
-------------------------input-------------------------
a ： 1
b ： 	c ： 1
	d ： [1, 2]
e ： [3, 4]
f ： true
-------------------------output-------------------------
a ： 1
e ： [3, 4]
b.c ： 1
f ： true
b.d ： [1, 2]
 */
public class MapInOneLayer {

	public static void main(String[] args) {
		Map<String,Object> map = mapData();
		System.out.println("-------------------------input-------------------------");
		printMap(map,"");
		map = getMapInOneLayer(map);
		System.out.println("-------------------------output-------------------------");
		printMap(map,"");
	}
	/**
	 * Generate the data of map 
	 */
	public static Map<String,Object> mapData(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("a", 1); // Node 1 
		
		Map<String,Object> mb = new HashMap<String,Object>();
		mb.put("c", 1);
		mb.put("d", new Integer[]{1,2});
		map.put("b", mb); // Node 2 with a Map   
		map.put("e", new Integer[]{3,4}); // Node 3 with a array 
		map.put("f", true); // Node 4 with a boolean
		
		return map;
	}
	/*
	 * method to public 
	 * */
	public static Map<String,Object> getMapInOneLayer(Map<String,Object> m){
		
		if(m == null || m.isEmpty()) return null;
		Map<String,Object> result = new HashMap<String,Object>();
		process(m,result,"");  // a black prefix for 1st time 
		return result;
	}
	/**
	 *  hidden the core process method to public, use back trace to process the inner map  
	 */
	private static void process(Map<String,Object> m, Map<String,Object> result, String pre){
		
		for(Map.Entry<String, Object> entry : m.entrySet()) {
		    String key = pre + (pre==null || pre.isEmpty()?"":".") + entry.getKey();
		    Object value = entry.getValue();
		    if(value == null){
		    	result.put(key, null);		    		
		    }else{
		    	if(value instanceof Map){  // Map
					process((Map<String,Object>)value,result,key); // 
				}else{ // non-Map 
					result.put(key, value);
				}
		    }
		    
		}
	}
	
	/*
	 * print out the Map
	 * */
	public static void printMap(Map<String, Object> map,String pre){
		if(map != null)
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
			    Object value = entry.getValue();
			    System.out.print(pre + key + " ： " );
			   if(value.getClass().isArray()){  // Array 
					System.out.print(Arrays.toString((Object[]) value)+"\n");  // use Arrays Api to print an array 
				}else if(value instanceof Map){  // Map
					printMap((Map<String,Object>) value, pre+"\t");
				}else { // primitive type Integer/Double/Boolean/... 
			    	System.out.println(value);
				} 
			}
	}
}
