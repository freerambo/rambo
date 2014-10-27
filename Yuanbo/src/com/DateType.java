package com;


/**
 * java 数据类型转换演示程序-<b>TestDateType</b>
 * @author Rambo Zhu 朱渊博
 * @version 1.6 2008/9/19
 * @deprecated testDateType
 */
public class DateType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		定义八种数据类型
        boolean f=false;
        char ch='a';
        byte byteNum=0;
        short shortNum=1;
        int intNum=2;
        long longNum=3;
        float floatNum=1.2f;
        double doubleNum=5.6;
       
        //输出八种数据类型
        System.out.println("####################################"); 
        System.out.println("布尔型数据f="+f);
        System.out.println("字符型数据ch="+ch);
        System.out.println("byte型数据byteNum="+byteNum);
        System.out.println("short型数据shortNum="+shortNum);
        System.out.println("int型数据intNum="+intNum);
        System.out.println("long型数据longNum="+longNum);
        System.out.println("浮点型数据floatNum="+floatNum);
        System.out.println("双精度浮点型数据doubleNum="+doubleNum);
        System.out.println("\n####################################"); 
        
        // int&long的数据类型转换及打印
        longNum=intNum;//将int类型数据直接赋予long型
        System.out.println("long型数据longNum="+longNum);
        intNum=(int)longNum;//须强制转换
        System.out.println("int型数据intNum="+intNum);
        System.out.println("####################################\n"); 
        // float和double型数据转换及打印
        doubleNum=floatNum;
        System.out.println("双精度浮点型数据doubleNum="+doubleNum);
        floatNum=(float)doubleNum;//须强制转换
        System.out.println("浮点型数据floatNum="+floatNum);
        System.out.println("####################################\n"); 
        
        
        
	}

}
