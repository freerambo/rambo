package com.rambo.practice;

public class NullTest {
    public static void main(String[] args) {
    	int i =0;
    	Integer k =0;

//    	 System.out.println(i == null); // error! int can not be compared with null
    	System.out.println(k == null); // ok, output false
    	System.out.println(0x130 + "ocm0vvzhw0_nsZPXKNgEUXol0OYQ".length()); 
    	System.out.println("ocm0vvzhw0_".length()); 

    	
    	
        if (null instanceof Object) {
            System.out.println("null is java.lang.Object");
        } else {
            System.out.println("null is not java.lang.Object");
        }
        Object obj = (Object)null;
        System.out.println("9d377b10ce778c4938b3c7e2c63a229a".length());
        EnumTest value = EnumTest.fromValue("DEVICE_ROOT");
        System.out.println(value.name());

    }
}