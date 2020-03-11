package workspace.string;

import java.util.Objects;

public class StringCompare {

    public static final String date1 = "2018-01-22 12;23:67";
    public static final String date2 = "2019-01-22 12;23:67";
    public static final String date3 = "2019-01-22 12;23:67";

    public static void main(String[] args) {


        System.out.println(date1.compareTo(date2));
        System.out.println(date3.compareTo(date2));
        //Exception in thread "main" java.lang.NullPointerException
        // System.out.println(date3.compareTo(null));
        Integer i = null;
        System.out.println(Objects.isNull(i) || i < 0);

    }
}
