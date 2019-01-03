package com.rambo.encrypt;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by yuzhu on 6/12/18.
 */
public class EncrptionTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {

//      testBase64("TGFTYW04Ng==");
//        testBase64("Od5p7TdMvZLH8GuWv8QX4yUWlqPEwEe5iHgl1ujZ+eQ=");
       testSHA("sajkdkjasdjksahkh");

        testBase64Encode("eb2e070515637922106f8bdfc519477b9adc6c5b90380a8c52462884235fa739");
//        System.out.println("Od5p7TdMvZLH8GuWv8QX4yUWlqPEwEe5iHgl1ujZ+eQ=".length());

    }


    static void testBase64(String code){
        String s =  new String(Base64.getDecoder().decode(code));
        System.out.println(s);
    }

    static void testBase64Encode(String code){
        String s =  Base64.getEncoder().encodeToString(code.getBytes(StandardCharsets.UTF_8));
        System.out.println(s + " - " + s.length());
    }

    static void testSHA(String code) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                code.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));
        System.out.println( " base64 encoding - " + Base64.getEncoder().encodeToString(hash) + Base64.getEncoder().encodeToString(hash).length()) ;

        System.out.println(sha256hex + " - " + sha256hex.length()) ;
    }
}
