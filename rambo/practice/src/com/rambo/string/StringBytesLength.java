package com.rambo.string;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by yuzhu on 28/11/18.
 */
public class StringBytesLength {

    public static void length(String s) {
        System.out.println("String length " + s.length());

        System.out.println("String bytes UTF-8length " + s.getBytes(Charset.forName("UTF-8")).length);
        System.out.println("String bytes UTF-16  length " + s.getBytes(Charset.forName("UTF-16")).length);


        System.out.println("String bytes UTF-16 length " + new String(s.getBytes(), Charset.forName("UTF-16")).length());


    }

    public static String generateNonce36() {
        String uuid36 = null;
        do {
            uuid36 = UUID.randomUUID().toString().replaceAll("-", "").concat(UUID.randomUUID().toString().substring(0, 4));
        } while (uuid36.length() < 36);
        return uuid36;
    }
    public static void main(String[] args) {
        length("asbd朱渊博");

        System.out.println(generateNonce36().length());
        System.out.println(generateNonce36().getBytes().length);

        System.out.println(UUID.randomUUID().toString());

        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").length());

    }

}
