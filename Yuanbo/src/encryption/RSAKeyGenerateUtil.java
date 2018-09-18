package encryption;

import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class RSAKeyGenerateUtil {
    public static String ALGORITHM="RSA";

    public  static PrivateKey pkey ;

    public	static PublicKey pubkey ;

    public static String PUBLIC_KEY="PublicKey";
    
    public static String PRIVATE_KEY="PrivateKey";

    public static Map<String,String> genKey() {

        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kpg.initialize(1024);
        KeyPair kep = kpg.generateKeyPair();
        Provider p  = kpg.getProvider();
        System.out.println("Keypair Provider : " + p.getName());
        pkey = kep.getPrivate();
        pubkey = kep.getPublic();
        System.out.println("generated Pubic key:"+new String(Base64.getEncoder().encode(pubkey.getEncoded())));
        System.out.println("====================================");
        System.out.println("generated Private key:"+new String(Base64.getEncoder().encode(pkey.getEncoded())));

        Map<String,String> param=new HashMap<String,String>();
        param.put(PUBLIC_KEY, new String(Base64.getEncoder().encode(pubkey.getEncoded())));
        param.put(PRIVATE_KEY, new String(Base64.getEncoder().encode(pkey.getEncoded())));

        return param;
    }


    public static void main(String[] args) throws Exception{
        genKey();
    }
}
