public class NullTest {
    public static void main(String[] args) {
        testNullObject();
    }
    /**
     * 
     *   
     * @description    testNullObject
     * @version currentVersion  
     * @author Yuanbo Zhu  
     * @createtime Aug 11, 2015 10:03:45 PM
     */
    public static void testNullObject() {
        if (null instanceof java.lang.Object) {
            System.out.println("null属于java.lang.Object类型");
        } else {
            System.out.println("null不属于java.lang.Object类型");
        }
    }
}