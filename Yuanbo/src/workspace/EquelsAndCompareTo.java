package workspace;

public class EquelsAndCompareTo {

    public static void main(String[] args) {

        System.out.println("foo".equals(null)); //false

        System.out.println("foo".compareTo(null)); //NullPointerException

    }
    /*
public class ECKeyHelper {
    public static void main(String[] args) throws Exception {

        ECKeyGenerator generator = new ECKeyGenerator(Curve.P_256);
        generator.keyID("device_authenticity_key");
        ECKey ecJWK = generator.generate();
        ECKey ecPublicJWK = ecJWK.toPublicJWK();

        System.out.println("full eckey " + ecJWK);
        System.out.println("public eckey " + ecPublicJWK);

        PrivateKey privateKey = ecJWK.toPrivateKey();
        System.out.println("privatekey " + ecJWK);

        KeyPair keypair = ecJWK.toKeyPair();
        PublicKey publicKey = keypair.getPublic();
        System.out.println("publicKey \n" + DatatypeConverter.printBase64Binary(publicKey.getEncoded()));

        privateKey = keypair.getPrivate();
        System.out.println("PrivateKEY \n" + DatatypeConverter.printBase64Binary(privateKey.getEncoded()));

       *//* CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream pkStream = new ByteArrayInputStream(privateKey.getEncoded());
        X509Certificate pkcert = (X509Certificate)cf.generateCertificate(pkStream);

        System.out.println("pkcert \n" + DatatypeConverter.printBase64Binary(pkcert.getEncoded()));*//*
}
}*/

}