package com.rambo.practice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuzhu on 3/12/18.
 */
public class DomainMatcn {


    public static String VISA_DOMAIN = "^(http:|https:|)[/][/]([^/]+[.])*visa.com(?::([0-9]{0,5}))?(/.*)?$";


    public static void main(String[] args) {

//        testIsMatch();
        getHostname("https://sl73cbpapd001.visa.com:8443/vtsercs-api/vtsercs");

    }

    private static void testIsMatch() {
        String text[] = {
                "http://visa.com",
                "http://www.visa.com",
                "https://visa.com",
                "http://1.2.3.visa.com/123",
                "https://visa.com/123",
                "https://visa.com/123/123",
                "http://visa.com:8887", //true
                "https://sl73cbpapd001.visa.com:8443/vtsercs-api/vtsercs",
                "http://1visa.com/123", //false
                "http://1visa.com.123/123" //false

        };
        for (String s : text) {
            System.out.println(isMatch(s, VISA_DOMAIN));
        }
    }

    private static boolean isMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }

    }

    private static void getHostname(String s) {

        try {
            URL url = new URL(s);
            System.out.println(url.getHost());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
