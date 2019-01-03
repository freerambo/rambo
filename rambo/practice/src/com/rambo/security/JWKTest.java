package com.rambo.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;


/**
 * Created by yuzhu on 14/12/18.
 */
public class JWKTest {

    public static void main(String[] args) throws Exception {

        // Generate the RSA key pair
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair keyPair = gen.generateKeyPair();

        // Convert to JWK format
    /*    JWK jwk = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey((RSAPrivateKey)keyPair.getPrivate())
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .build();

        System.out.println("JWS signature : " + jwk.toString());*/


//        createSharedSecretJWS("hello world", UUID.randomUUID().toString());
    }
}
