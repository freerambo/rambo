package regex;

import java.util.regex.*;

public class RegexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "javajavaibmjavajavaigkljava";
		Pattern pt = Pattern.compile("(java)+");
		Matcher matcher = pt.matcher(str);
		
		System.out.println(matcher.replaceAll("java"));
	}

}
