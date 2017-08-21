/*
 * Copyright: Zhu Yuanbo @ NTU
 * Yuanbo
 * grab -> MemMgnt.java
 * Created on 14 Aug 2017-2:34:33 pm
 */
package grab;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  14 Aug 2017 2:34:33 pm
 */

class Space{
	int size;
	int start;
	Ref[] refs = null;
	int firstAddress = 0; 
	public Space(int size, int start) {
		super();
		this.size = size;
		this.start = start;
		firstAddress = start;
		refs = new Ref[size];
		int i =0;
		for(Ref ref : refs){
			ref.address = this.start + i++;
			ref.flag = true;
		}
	}
	
}
class Ref{
	boolean flag = false;
	int address = 0;
	public Ref(boolean flag, int address) {
		super();
		this.flag = flag;
		this.address = address;
	}
	
	
}

public class MemMgnt {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     14 Aug 2017 2:34:33 pm
	 */
	public static void main(String[] args) {
	
		// TODO Auto-generated method stub

		
		Space space = new Space(0x1<<26, 0x0);
		
		
		Ref[] ref =  alloc(space, 0x1 << 16);
		
		free(ref, space);
		
	}
	
	public static Ref[] find(Space space, int size){
		
		Ref[] refs = space.refs;
		int first = space.firstAddress;
		int start = space.start;
		
		if(first - start < refs.length && first - start + size < refs.length){
			// check availability here 
			
			Ref[] ref = new Ref[size];
			for(int i = first; i < refs.length; i++){
				ref[i] = new Ref(false, i);
			}
			
			return ref;
		}
		
		return null;
		
	}

	public static Ref[] alloc(Space space, int size){
		
		return find(space, size);
	}
	
	public static void free(Ref[] ref, Space space){
		
		for(Ref r : ref){
			r.flag = true;
			r.address = 0;
			r =null;
		}
		space.firstAddress -= ref.length;
		ref = null;
	}
	

}
