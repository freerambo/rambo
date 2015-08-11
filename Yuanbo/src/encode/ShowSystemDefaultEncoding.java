package encode;

public class ShowSystemDefaultEncoding {
	public static void main(String[] args) {
		String encoding = System.getProperty("file.encoding");
		System.out.println(encoding);
	}
}