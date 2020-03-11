public class Exzample {

    /**
     * @param args
     */
    int k;

    //	int i;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Exzample e = new Exzample();
        int i = 0;
        i = i++;
        System.out.println(i);//0 or 1  result 0
        e.test();
        System.err.println("gc is not a Java Thread, it is a native thread");
        Thread.getAllStackTraces().keySet().forEach(thread -> System.out.println(thread.getName() + "->" + thread.isDaemon() + " " + thread.getPriority()));
        System.out.println("3. gc线程是daemon线程");
    }

    public void test() {
        System.out.println(k);
    }
}
