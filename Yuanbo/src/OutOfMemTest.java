import com.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OutOfMemTest {



    public static void main(String[] args) {

        TestObj obj = new TestObj("TestObj");
        TestObj1 obj1 = new TestObj1("TestObj1");

        Set<TestObj> objSet = new HashSet<>();
        objSet.add(obj);
        Set<TestObj1> objSet1 = new HashSet<>();
        objSet1.add(obj1);

        System.out.println("objSet - " + objSet.contains(obj));
        System.out.println("objSet1 - " + objSet1.contains(obj1));

        obj.name = "others";
        obj1.name = "others1";

        System.out.println("objSet - " + objSet.contains(obj)); // false, hashCode changed, hence cause memory leakage
        System.out.println("objSet1 - " + objSet1.contains(obj1)); // true as default hashCode is the reference address
    }
}

class TestObj {
    String name;

    TestObj(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObj testObj = (TestObj) o;
        return Objects.equals(name, testObj.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class TestObj1 {
    String name;

    TestObj1(String name) {
        this.name = name;
    }

}