package com;


/**
 * java ��������ת����ʾ����-<b>TestDateType</b>
 * @author Rambo Zhu ��Ԩ��
 * @version 1.6 2008/9/19
 * @deprecated testDateType
 */
public class DateType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		���������������
        boolean f=false;
        char ch='a';
        byte byteNum=0;
        short shortNum=1;
        int intNum=2;
        long longNum=3;
        float floatNum=1.2f;
        double doubleNum=5.6;
       
        //���������������
        System.out.println("####################################"); 
        System.out.println("����������f="+f);
        System.out.println("�ַ�������ch="+ch);
        System.out.println("byte������byteNum="+byteNum);
        System.out.println("short������shortNum="+shortNum);
        System.out.println("int������intNum="+intNum);
        System.out.println("long������longNum="+longNum);
        System.out.println("����������floatNum="+floatNum);
        System.out.println("˫���ȸ���������doubleNum="+doubleNum);
        System.out.println("\n####################################"); 
        
        // int&long����������ת������ӡ
        longNum=intNum;//��int��������ֱ�Ӹ���long��
        System.out.println("long������longNum="+longNum);
        intNum=(int)longNum;//��ǿ��ת��
        System.out.println("int������intNum="+intNum);
        System.out.println("####################################\n"); 
        // float��double������ת������ӡ
        doubleNum=floatNum;
        System.out.println("˫���ȸ���������doubleNum="+doubleNum);
        floatNum=(float)doubleNum;//��ǿ��ת��
        System.out.println("����������floatNum="+floatNum);
        System.out.println("####################################\n"); 
        
        
        
	}

}
