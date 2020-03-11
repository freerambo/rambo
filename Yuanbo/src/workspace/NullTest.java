package workspace;

/**
 * Created by yuzhu on 3/5/19.
 */
public class NullTest {

    public static void main(String[] args) {


        isObject();
        nullString();

    }

    public static void isObject() {


        // false
        System.out.println(null instanceof Object);
        System.out.println(System.currentTimeMillis());
    }

    public static void nullString() {
        System.out.println(null + "hello"); //nullhello

        String s = null + "test";//nulltest

//        String s = String.valueOf(null) + "test";//NullPointerException


        System.out.println(s);
    }
}
