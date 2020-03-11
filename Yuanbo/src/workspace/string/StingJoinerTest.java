package workspace.string;

import java.util.StringJoiner;

/**
 * Created by yuzhu on 19/11/18.
 */
public class StingJoinerTest {

    public static void main(String[] args){

        StringJoiner sj = new StringJoiner("-");
        sj.add("hello").add("world");
        System.out.println(sj.toString());

    }
}
