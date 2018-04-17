package v;

import java.math.BigInteger;

public class BigIntTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = (long)Math.pow(2, 64) + "";
		BigInteger bi = new BigInteger(s);
		System.out.println(s+"\n"+bi);
	}

}
