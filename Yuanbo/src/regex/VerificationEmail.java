package regex;
import java.util.regex.*; 
public class VerificationEmail {

	public static void main(String[] args) {
		String regexStr = "\\w+@\\w+\\.\\p{Alpha}{2,4}(\\.\\p{Alpha}{2})?";
		Pattern ptn = Pattern.compile(regexStr,Pattern.CASE_INSENSITIVE);
		Matcher matcher = ptn.matcher("rambo@123.com");
		System.out.println(matcher.matches());
	}

}
