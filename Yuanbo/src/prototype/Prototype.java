package prototype;

public class Prototype {
	public static void main(String s[]) {
		S1 s1 = new S1();
		S2 s2 = new S2();

		s1 = s2;// �������˵ s1��������ʱ���ڴ�ռ���s2���� ������ʲô��˼��
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