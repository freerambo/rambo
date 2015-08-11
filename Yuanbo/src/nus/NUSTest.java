package nus;

public class NUSTest {

	static int x = 7;
	static String s = null;

	public static void getWeight(Passenger p) {
		int y = 0 / x;
		System.out.print(s + " ");
	}

	public static void main(String[] args) {
		Passenger[] pa = { new Passenger(), new Member() };
		for (Object o : pa)
			getWeight((Passenger) o);
	}

}

class Passenger {
}

class Member extends Passenger {
}

