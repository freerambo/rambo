/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> TryCatchFinally.java
 * Created on 14 Jul 2017-2:13:09 pm
 */
package interview;

import java.util.HashMap;
import java.util.Map;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  14 Jul 2017 2:13:09 pm
 */
public class TryCatchFinally {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     14 Jul 2017 2:13:09 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(test());
		System.out.println(test1());
	    System.out.println(getMap().get("test"));
		
	}
	
	
	public static int test(){
		try{
			
//			throw new Exception();
			return 0;
			}catch(Exception e){
				System.out.println("in Exception");

			  return 1;
			}finally{
			  return 2;
			}
	}
	
	
	
	public static int test1(){
		int i = 0;
		try{
		  i = 1;
		  return i;
		}catch(Exception e){
		  i = 2;
		  return i;
		}finally{
		  i = 3;
		}
	}
	
	
	public static Map<String,Integer> getMap(){
	    Map<String,Integer> map = new HashMap<>();
	    try{
	      map.put("test",1);
	      return map;
	    }catch(Exception e){
	      map.put("test",1);
	      return map;
	    }finally{
	      map.put("test",3);
	    }
	 }

}
