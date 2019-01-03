package com.rambo.practice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuzhu on 3/12/18.
 */
public class DomainMatcn {


    public static String DEMO_DOMAIN = "^(http:|https:|)[/][/]([^/]+[.])*demo.com(?::([0-9]{0,5}))?(/.*)?$";


    public static void main(String[] args) {

//        testIsMatch();
        getHostname("https://dsds.demo.com:8443/-api/df");

    }

    private static void testIsMatch() {
        String text[] = {
                "http://demo.com",
                "http://www.demo.com",
                "https://demo.com",
                "http://1.2.3.demo.com/123",
                "https://demo.com/123",
                "https://demo.com/123/123",
                "http://demo.com:8887", //true
                "https://hello.demo.com:8443/fd-api/fd",
                "http://1demo.com/123", //false
                "http://1demo.com.123/123" //false

        };
        for (String s : text) {
            System.out.println(isMatch(s, DEMO_DOMAIN));
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
