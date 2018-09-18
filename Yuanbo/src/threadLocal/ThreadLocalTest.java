package threadLocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalTest {

	AtomicInteger ai = new  AtomicInteger(0);
	volatile Integer count = 0;
	
	private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>(){
		public Integer initialValue(){
			return 0;
		}
	};
	
	public Integer getNextNum(){
		tl.set(tl.get() + 1);
		count++;
		return tl.get();
	}
	
	public static void main(String[] args) {
		ThreadLocalTest tlt = new ThreadLocalTest();
		
		ThreadClient t1 = new ThreadClient(tlt);
		ThreadClient t2 = new ThreadClient(tlt);
		ThreadClient t3 = new ThreadClient(tlt);
		
		t1.start();
		t2.start();
		t3.start();

	}
	
		
	private static class ThreadClient extends Thread {
	
		private ThreadLocalTest tlt;
		
		
		ThreadClient(ThreadLocalTest tlt){
			this.tlt = tlt;
		}
		@Override
		public void run() {
			for(int i = 0; i < 3; i++)
				System.out.println(Thread.currentThread().getName() + " - " + this.tlt.getNextNum() + " - " + this.tlt.ai.incrementAndGet() + " - volatile :  " + this.tlt.count);
		}
		
	}
}
