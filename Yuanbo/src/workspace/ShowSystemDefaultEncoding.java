package workspace;

public class ShowSystemDefaultEncoding {

	public static void main(String[] args) {
		String encoding = System.getProperty("file.encoding");
		//UTF-8
		System.out.println(encoding);
	}

}
