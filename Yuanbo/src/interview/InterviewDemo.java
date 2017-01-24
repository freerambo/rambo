package interview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ysc on 7/26/16.
 */
public class InterviewDemo {
    private static void one(){
        String str1 = "hello";
        String str2 = "he"+new String("llo");
        System.err.println(str1==str2);
        System.out.println("1. false");
    }
    private static void two(){
        int i = Integer.MAX_VALUE;
        System.err.println((i+1)<i);
        System.out.println("2. 存在一个i, 使得(i+1)<i");
    }
    private static void three(){
        System.err.println("gc is not a Java Thread, it is a native thread");
        Thread.getAllStackTraces().keySet().forEach(thread -> System.out.println(thread.getName()+"->"+thread.isDaemon()+" "+thread.getPriority()));
        System.out.println("3. gc线程是daemon线程");
    }
    private static volatile int count = 0;
    private static void four(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int j=0; j<10; j++){
            executorService.submit(()->{
                for(int i=0; i<1000000; i++){
                    count++;
                }
            });
        }
        System.out.println("count should be: "+10000000+", actual be: "+count);
        System.out.println("4. volatile不能保证线程安全");
    }
    private static void five(){
        ArrayList<Integer> list = new ArrayList<>(20);
        list.add(1);
        System.out.println("debug code, not execute grow method");
        System.out.println("5. list grow 0 times");
    }
    private static void six() {
        System.out.println("BufferedReader's constructor only accepts a Reader instance");
        System.out.println("6. new BufferedReader(new FileInputStream(\"a.dat\")); is wrong");
    }
    private static void seven() {
        try{
            if(true){
                throw new IOException();
            }
        }catch (FileNotFoundException e){
            System.out.print("FileNotFoundException!");
        }catch (IOException e){
            System.out.print("IOException!");
        }catch (Exception e){
            System.out.print("Exception!");
        }
        System.out.println("\n7. IOException!");
    }
    private static void eight() {
        System.out.println("String s;System.out.println(s); error: variable s might not have been initialized\nRecompile with -Xlint:unchecked for details.");
        System.out.println("8. 由于String s没有初始化, 代码不能编译通过");
    }
    private static void nine() {
        System.out.println("5"+2);
        System.out.println("9. 52");
    }
    private static void ten() {
        int i = 2;
        int result = 0;
        switch(i){
            case  1:
                result = result + i;
            case  2:
                result = result + i * 2;
            case  3:
                result = result + i * 3;
        }
        System.out.println("result="+result);
        System.out.println("10. 10");
    }
    private static class Null{
        public static void hello(){
            System.out.println("hello");
        }
        public static void main(String[] args) {
            ((Null)null).hello();
            Null _null = (Null)null;
            _null.hello();
        }
    }
    private static class StringExample1{
        String str = new String("good");
        char[] ch = {'a', 'b', 'c'};
        public void change(String str, char[] ch){
            str = "test ok";
            ch[0] = 'g';
        }

        public static void main(String[] args) {
            StringExample1 ex = new StringExample1();
            ex.change(ex.str, ex.ch);
            System.out.print(ex.str+" and ");
            System.out.print(ex.ch);
            System.out.println();
        }
    }
    private static class StringExample2{
        public static void change(String str){
            str = "welcome";
        }

        public static void main(String[] args) {
            String str = "1234";
            change(str);
            System.out.println(str);
        }
    }
    private static class ForLoop{
        static boolean foo(char c){
            System.out.print(c);
            return true;
        }

        public static void main(String[] args) {
            int i=0;
            for(foo('A');foo('B')&&(i<2);foo('C')){
                i++;
                foo('D');
            }
            System.out.println();
        }
    }
    private static class HelloA{
        public HelloA(){
            System.out.println("HelloA");
        }

        { System.out.println("I'm A class"); }

        static {
            System.out.println("static A");
        }
    }
    private static class HelloB extends HelloA{
        public HelloB(){
            System.out.println("HelloB");
        }

        { System.out.println("I'm B class"); }

        static {
            System.out.println("static B");
        }

        public static void main(String[] args) {
            System.out.println("main start");
            new HelloB();
            new HelloB();
            System.out.println("main end");
        }
    }
    public static void main(String[] args) {
        one();
        two();
        three();
        four();
        five();
        six();
        seven();
        eight();
        nine();
        ten();
        Null.main(null);
        StringExample1.main(null);
        StringExample2.main(null);
        ForLoop.main(null);
        HelloB.main(null);
    }
}