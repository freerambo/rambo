package com.rambo.encrypt.security;


import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.List;

/**
 * Created by yuzhu on 7/12/18.
 */
public class ValidateKeyChain {


    public static void main(String[] args) throws CertificateException, NoSuchAlgorithmException, FileNotFoundException {

//        getCertFromFile("/Users/yuzhu/Downloads/GTS1O1.crt");
//        getCertsFromFile("/Users/yuzhu/Downloads/roots.pem");
        getCertFromFile("/Users/yuzhu/Downloads/GSR2.crt");

    }


    private static String getThumbprint(X509Certificate cert)
            throws NoSuchAlgorithmException, CertificateEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] der = cert.getEncoded();
        md.update(der);
        byte[] digest = md.digest();
        String digestHex = DatatypeConverter.printHexBinary(digest);
        return digestHex.toLowerCase();
    }

    private static String getCertFromFile(String path)
            throws NoSuchAlgorithmException, CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(path));
        String certString = cert.toString();
        System.out.println(getThumbprint(cert));
        System.out.println("subjectDN " + cert.getSubjectDN().getName());
        System.out.println("IssuerDN "+cert.getIssuerDN().getName());
//        System.out.println(new String(cert.getExtensionValue("2.5.29.14")));
//cert.getNonCriticalExtensionOIDs().forEach(System.out::println);
        System.out.println(DatatypeConverter.printBase64Binary(cert.getEncoded()).replaceAll("(.{64})", "$1\n"));
        return certString;
    }

    private static void getCertsFromFile(String path)
            throws NoSuchAlgorithmException, CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        List<X509Certificate> certs = (List<X509Certificate>) cf.generateCertificates(new FileInputStream(path));

        certs.forEach(cert -> {
            try {
                System.out.println(DatatypeConverter.printBase64Binary(cert.getEncoded()).replaceAll("(.{64})", "$1\n"));
                System.out.println("subjectDN " + cert.getSubjectDN().getName());
                System.out.println("IssuerDN "+cert.getIssuerDN().getName());
            } catch (CertificateEncodingException e) {
                e.printStackTrace();
            }
        }
);
    }
}