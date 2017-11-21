/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> PriceStrategy.java
 * Created on 15 Nov 2017-11:15:08 am
 */
package interview;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  15 Nov 2017 11:15:08 am
 */
interface MemberStrategy {
	double calcPrice(double price);
}
class JuniorMemberStrategy implements MemberStrategy{

	/* (non-Javadoc)
	 * @see interview.MemberStrategy#calcPrice(double)
	 */
	@Override
	public double calcPrice(double price) {
		// TODO Auto-generated method stub
		return price;
	}
	
	
}

class SeniorMemberStrategy implements MemberStrategy{

	/* (non-Javadoc)
	 * @see interview.MemberStrategy#calcPrice(double)
	 */
	@Override
	public double calcPrice(double price) {
		// TODO Auto-generated method stub
		return price*0.85;
	}
	
}


public class StrategyTest{
	
	MemberStrategy strategy;
	
	double price;
	
	
	 public StrategyTest(MemberStrategy strategy, double price) {
		super();
		this.strategy = strategy;
		this.price = price;
	}
	 
	 /**
	  * 
	  * @function: strategy pattern works
	  * @return
	  * @author: Rambo Zhu     15 Nov 2017 11:21:34 am
	  */
    public double getRealPrice() {  
        return strategy.calcPrice(this.price);  
    }  

	public static void main(String[] args) {  
//	        MemberStrategy strategy=new JuniorMemberStrategy();  
        MemberStrategy strategy=new SeniorMemberStrategy();  

		StrategyTest bookPrice = new StrategyTest(strategy,99.0);  
	        System.out.println("价格是："+bookPrice.getRealPrice());  
	    }  
}