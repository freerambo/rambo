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
        System.out.println("9d377b10ce778c4938b3c7e2c63a229a".length());
    }
}