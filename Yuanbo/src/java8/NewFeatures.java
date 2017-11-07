/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * java8 -> NewFeatures.java
 * Created on 24 Oct 2017-4:26:03 pm
 */
package java8;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * function description：
 *http://javarevisited.blogspot.sg/2014/02/10-example-of-lambda-expressions-in-java8.html#at_pco=smlwn-1.0&at_si=59edeca48e026dc2&at_ab=per-2&at_pos=0&at_tot=1
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  24 Oct 2017 4:26:03 pm
 */
public class NewFeatures {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     24 Oct 2017 4:26:03 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		threadRun();
//		iterator();
//		map();
		list();
	}
	/**
	 *  
	 * @function:
	 * @author: Rambo Zhu     24 Oct 2017 4:29:55 pm
	 */
	public static void threadRun(){
		//Before Java 8: 
		new Thread(new Runnable() { 
			@Override 
			public void run() { 
				System.out.println("Before Java8, too much code for too little to do"); 
			}}).start(); 
		//Java 8 way: 
		new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!") ).start();

		
		new Thread(new Runnable(){
			@Override
			public void run(){
				System.out.println("Before java 8, too much code for too little to do");
			}
		});
		
		new Thread(() -> System.out.println("java 8 new feature!!")).start();
	}
	
	/**
	 * interator expression with lambda
	 * @function:
	 * @author: Rambo Zhu     24 Oct 2017 4:37:00 pm
	 */
	public static void iterator(){
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Data","Date and Time API"); 
		// before
		for(String f:features){
			System.out.println(f);
		}
		//java 8
		features.forEach(f -> System.out.println(f));

		Predicate<String> startWithD = (n) -> n.startsWith("D");
		Predicate<String> longThan5 = (n) -> n.length() > 5;
		
		features.stream().filter(startWithD.and(longThan5)).forEach(n -> System.out.println("\n Start with D and longger than 5 - " + n));

	}

	public static void map(){
		List<Integer> features = Arrays.asList(100,200,300,400,500); 
		// before
		for(Integer f:features){
			double price = f + 0.12*f;
			System.out.println(price);
		}
		//java 8
		features.forEach(f -> System.out.println(f + 0.12*f));

		features.stream().map((cost) -> cost + 0.12*cost).forEach(System.out::println);
		System.out.println();
		double bill = features.stream().map((cost) -> cost + 0.12*cost).reduce((sum, cost) -> sum + cost).get();

		System.out.println("Total : " + bill);

	}
	
	public static void list(){
		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "UK","Canada"); 
		
		
		List<String> filted  = G7.stream().filter(x -> x.length() > 5).collect(Collectors.toList());
		filted.forEach(System.out::println);
		
		String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", ")); 
		System.out.println(G7Countries);

		String cs = G7.stream().map(s -> s.toUpperCase()).reduce((sum, s) -> sum + "-" + s ).get(); 
		System.out.println(cs);


	}
	
	/**
	 * statistics 
	 * @function:
	 * @author: Rambo Zhu     24 Oct 2017 6:26:51 pm
	 */
	public static void stats(){
		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29); 

		
		IntSummaryStatistics stats  = primes.stream().mapToInt(x -> x.intValue()).summaryStatistics();
		
		
		System.out.println("getAverage " + stats.getAverage());

		System.out.println("getMax " + stats.getMax());

		System.out.println("getCount " + stats.getCount());

		System.out.println("getMin " + stats.getMin());

		System.out.println("getSum " + stats.getSum());

		
		


	}



}
