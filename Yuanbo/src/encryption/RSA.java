package encryption;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;


public class RSA {
	
	public static String ALGORITHM = "RSA";
	public static String SIGN_ALGORITHMS = "SHA1WithRSA";// hash encryption algorithm for signature
	public static String CHAR_SET = "UTF-8";

	/**
	 * sign the data with Signature
	 * 
	 * @param content
	 * @param privateKey
	 * @return signature
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.getDecoder().decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(CHAR_SET));
			byte[] signed = signature.sign();
			return new String(Base64.getEncoder().encode(signed), CHAR_SET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Verify Signature
	 *
	 * @param content
	 * @param sign
	 * @param public_key
	 * @return
	 */
	public static boolean verify(String content, String sign,
			String public_key) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.getDecoder().decode(public_key);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(CHAR_SET));
			boolean bverify = signature.verify(Base64.getDecoder().decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Decrypt with public key 
	 *
	 * @param content to be decrpted
	 * @param pk public key 
	 * @return decrypted data
	 */
	protected static byte[] decryptByPublicKey(String content, PublicKey pk) {

		try {
			Cipher ch = Cipher.getInstance(ALGORITHM);
			ch.init(Cipher.DECRYPT_MODE, pk);
			InputStream ins = new ByteArrayInputStream(Base64.getDecoder().decode(content));
			ByteArrayOutputStream writer = new ByteArrayOutputStream();
			// Maximium bytes is 128 for rsa decryption
			byte[] buf = new byte[128];
			int bufl;
			while ((bufl = ins.read(buf)) != -1) {
				byte[] block = null;

				if (buf.length == bufl) {
					block = buf;
				} else {
					block = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						block[i] = buf[i];
					}
				}
				writer.write(ch.doFinal(block));
			}

			return writer.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * encrpt with private key
	 *
	 * @param content
	 * @param pk
	 * @return encrypted data without Base64 decode
	 */
	protected static byte[] encryptByPrivateKey(String content, PrivateKey pk) {
		try {
			Cipher ch = Cipher.getInstance(ALGORITHM);
			ch.init(Cipher.ENCRYPT_MODE, pk);
			return ch.doFinal(content.getBytes(CHAR_SET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * decrypt data with public key
	 *
	 * @param content
	 * @param publicKey
	 * @return
	 */
	public static String decrypt(String content, String publicKey) {
		if (null == publicKey || "".equals(publicKey)) {
			System.err.println("the publick key can not be null");
			return null;
		}
		PublicKey pk = getPublicKey(publicKey);
		byte[] data = decryptByPublicKey(content, pk);
		String res = null;
		try {
			res = new String(data, CHAR_SET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * encrypt By PrivateKey
	 *
	 * @param content
	 * @param privateKey 
	 * @return
	 */
	public static String encrypt(String content, String privateKey) {
		PrivateKey pk = getPrivateKey(privateKey);
		byte[] data = encryptByPrivateKey(content, pk);
		String res = null;
		try {
			res = new String(Base64.getEncoder().encode(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	/**
	 * get Private Key
	 * @param privateKey decoded by base64
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String privateKey)  {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(privateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privatekey = keyFactory.generatePrivate(keySpec);
			return privatekey;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * getPublicKey
	 * @param publicKey
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String publicKey) {

		try {
			byte[] keyBytes = Base64.getDecoder().decode(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publickey = keyFactory.generatePublic(keySpec);
			return publickey;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}
	
	public static void main(String[] args) {
		
		// generate the RSA key pair 
		Map<String,String> keyPair = RSAKeyGenerateUtil.genKey();
		String privateKey = keyPair.get(RSAKeyGenerateUtil.PRIVATE_KEY);
		String publicKey = keyPair.get(RSAKeyGenerateUtil.PUBLIC_KEY);

		// prepare data to be encrpted 
		String data = "abcd";
		System.out.println("\nOriginal data -->" + data);
		
		// encrypt the data with publicKey
		String encrypt = encrypt(data,privateKey);
		System.out.println("Encrypted data by privatekey-->" + encrypt);
		
		// sign the data with privateKey
		String sign = sign(data, privateKey);
		System.out.println("Sign by privatekey -->" + sign);
		
		// Decrypt the data with publicKey
		String decrypt = decrypt(encrypt,publicKey);
		System.out.println("Decrypted data by publickey-->" + decrypt);
		
		// verify the signature
		System.out.println("Verify Sign by publickey-->" + verify(decrypt, sign, publicKey));
		
	}

}
