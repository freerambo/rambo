/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> NumToChanise.java
 * Created on 22 Nov 2017-11:44:35 am
 */
package leetcode;

import java.util.Scanner;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  22 Nov 2017 11:44:35 am
 */
public class NumToChanise {

	
	private String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {
            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
            	 System.out.println(" unit  "+ (n - 2 - i));
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  "+result);
        }

        System.out.println("----------------");
        System.out.println(result);
        int i = 0, j=result.length()-1;
        while(i<result.length() && result.charAt(i) == '零') i++;
        while(j > 0 && result.charAt(j) == '零') j--;
        result = result.substring(i, j+1);
        result = result.replaceAll("零+", "零");
    	System.out.println(result);
       /* for(; i <result.length();i++){
        	if(i != result.length() - 1 && result.charAt(i) != '零')
        		System.out.print(result.charAt(i));
	    }*/
        return result;

    }
	

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入字符串：");
        String str = scanner.next();
        // 将字符串数字转化为汉字
        NumToChanise main1 = new NumToChanise();
        main1.toChinese(str);
    }
    
}
