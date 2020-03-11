package com.rambo.modules.practice;
// Java Code:

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

public class CBPUnlimitedEncryptionAndBCChecker {
   public static void main(String... args) throws NoSuchAlgorithmException {
      System.out.println("Java version = " + System.getProperty("java.version"));
      System.out.println("MAX key length = " + Cipher.getMaxAllowedKeyLength("AES")); // if > 128 => unlimited encryption
      System.out.println("Unlimited Encryption : " + (Cipher.getMaxAllowedKeyLength("AES") > 128 ? "Enabled" : "Disabled"));
      System.out.println("Bouncy Castle info = " + new BouncyCastleProvider());
   }
   /**
    * Java version = 1.8.0_161
MAX key length = 2147483647
Unlimited Encryption : Enabled
Bouncy Castle info = BC version 1.59
    */
}