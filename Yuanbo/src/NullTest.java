public class NullTest {
    public static void main(String[] args) {
        testNullObject();
    }
    
    public static void testNullObject() {
        if (null instanceof java.lang.Object) {
            System.out.println("null属于java.lang.Object类型");
        } else {
            System.out.println("null不属于java.lang.Object类型");
        }
    }
}