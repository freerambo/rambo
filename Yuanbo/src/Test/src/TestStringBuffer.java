package Test.src;

public class TestStringBuffer {

	public static void main(String[] args) {
		StringBuffer buf = new StringBuffer("today");
		buf.append("  Monday");
		int index = 6;

		buf.insert(index, "is");
		buf.setCharAt(0, 'T');
		buf.replace(9, 12, "satur");
		buf.setCharAt(9, 'S');
		String s = buf.toString();
		System.out.println(s);
		System.out.println((int)(Math.pow(8, 5)));
		
	}
}
