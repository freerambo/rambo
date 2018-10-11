package v;

import java.math.BigInteger;

public class BigIntTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s1 = (long)Math.pow(2, 64) + "";
		BigInteger bi = getBigInteger(s1);
		System.out.println(s1+"\n"+bi);
	}

	private static BigInteger getBigInteger(String s) {
		return new BigInteger(s);
	}

}
