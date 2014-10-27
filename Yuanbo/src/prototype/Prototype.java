package prototype;

public class Prototype {
	public static void main(String s[]) {
		S1 s1 = new S1();
		S2 s2 = new S2();

		s1 = s2;// 这个书上说 s1对象运行时的内存空间变成s2类型 ，这是什么意思？
		System.out.println(s1.s);
		System.out.println(s1.getS());
	}
}

class S1 {
	public String s = "S1";

	public String getS() {
		return s;
	}
}

class S2 extends S1 {
	public String s = "S2";

	public String getS() {
		return s;
	}
}