/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> TemplateTest.java
 * Created on 15 Nov 2017-11:23:48 am
 */
package interview;

import java.util.concurrent.ConcurrentHashMap;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  15 Nov 2017 11:23:48 am
 */
public class TemplateTest {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     15 Nov 2017 11:23:48 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        AbstractTemplate temp1 = new TemplateImpl1();  
        AbstractTemplate temp2 = new TemplateImpl2();  

        temp1.execute();  
        temp2.execute();  

	}

}

abstract class AbstractTemplate {  
    /** 
     *  
     * executeMain(需要在子类实现)  
     * @createDate 2013-3-12 下午02:12:43 
     * @since  1.0.0 
     */  
    public abstract void executeMain();  
      
    public void execute() {  
        beforeAction();  
        executeMain();  
        afterAction();  
    }  
      
    public void beforeAction() {  
        System.out.println("before Action...");  
    }  
      
    public void afterAction() {  
        System.out.println("after Action...");  
    }  
}  


class TemplateImpl1 extends AbstractTemplate{  
    public void executeMain() {  
        System.out.println("核心操作处理...实现方式1");  
    }  
}  

class TemplateImpl2 extends AbstractTemplate{  
    public void executeMain() {  
        System.out.println("核心操作处理...实现方式2");  
    }  
}  