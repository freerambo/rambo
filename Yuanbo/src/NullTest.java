public class NullTest {
    public static void main(String[] args) {
        testNullObject();
    }
    
    public static void testNullObject() {
        if (null instanceof java.lang.Object) {
            System.out.println("null����java.lang.Object����");
        } else {
            System.out.println("null������java.lang.Object����");
        }
    }
}