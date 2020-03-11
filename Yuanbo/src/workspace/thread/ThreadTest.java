package workspace.thread;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    public static void main(String[] args) throws Exception {
//        new ThreadTest().threadTest();
//        method();
//        method_01();
//        method_02();
        method_04();
    }


    void threadTest() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("Thread " + Thread.currentThread().getName());
            }
        }.start();

    }

    public static void method() throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {

            final int index = i;

            Thread.sleep(1000);

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "  " + index);
                }
            });
        }
    }

    public static void method_01() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {

            Thread.sleep(1000);
            final int index = i;

            executor.execute(() -> {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "  " + index);
            });
        }
        executor.shutdown();
    }

    public static void method_02() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long start = new Date().getTime();
                System.out.println("scheduleAtFixedRate 开始执行时间:" +
                        DateFormat.getTimeInstance().format(new Date()));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = new Date().getTime();
                System.out.println("scheduleAtFixedRate 执行花费时间=" + (end - start) / 1000 + "s");
                System.out.println("scheduleAtFixedRate 执行完成时间：" + DateFormat.getTimeInstance().format(new Date()));
                System.out.println("======================================");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }


    /**
     * https://blog.csdn.net/fuyuwei2015/article/details/83825851
     * really appreciate if you can please help us to execute the script in SBX. Thanks in advance.
     *
     * that are planned to be delivered by SG Team.
     */

    public static void method_04() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "   " + index);
            });
        }
        executor.shutdown();
    }


    public static void method_05() {

    }

}
