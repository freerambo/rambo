/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> Tets.java
 * Created on 7 Aug 2017-3:20:11 pm
 */
package interview;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  7 Aug 2017 3:20:11 pm
 */
public class NumOfPerms {

	
	
	public static void main(String[] ags){
		System.out.println(numOfPerms(4));
		System.out.println(numOfPerms1(4));
		System.out.println(numOfPerms3(4));


	}
	static int numOfPerms(int x){
	    
	    if(x <= 2) return x;
	    x = x - 1;
	    return ((numOfPerms(x) * (2*x+2) * (2*x+1))/((x+1)*(x+2)));
	}
	
	
static int numOfPerms1(int x){
	    
	    if(x <= 2) return x;
	    int res = 1;
	    for(int k=2; k<=x; k++)
	    	res *= (4*k-2)/(k+1);
	    return res;
	}

public static int numOfPerms3(int numOfNodes) {
    if (numOfNodes<=2 && numOfNodes > 0) {
        return numOfNodes;
    }
    int res = 1;
    for (int i=2; i<=numOfNodes; i++) {
        res = res*(4*i-2)/(i+1);
    }
    return res;
 } 
}
