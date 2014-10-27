package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//author:���³�
public class RegExpTest {
	/**
	 * ˵����args[0]��Ҫ�滻�Ĵ�����java args[1]��Ҫ���ĵĴ�����javajavadocjavadocdoc
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			String str = null;
			Pattern ptn = Pattern.compile("(" + args[0] + ")+",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = ptn.matcher(args[1]);
			str = matcher.replaceAll(args[0]);
			System.out.println(str);
		} else {
			System.out.println("�����ʽ���󣡣�");
		}
		System.out.println(findByReg("abedefg", "9"));
	}

	/**
	 * @param s
	 *            Ҫƥ���Դ�ַ���
	 * @param reg
	 *            ��ƥ���������ʽ
	 * @return ��һ��ƥ���λ�� -1 ��ʾδ�ҵ�
	 * @description
	 * @version currentVersion
	 * @author Rambo
	 * @createtime 2013��11��25�� ����10:28:23
	 */
	public static int findByReg(String s, String reg) {
		Pattern ptn = Pattern.compile(reg);
		Matcher matcher = ptn.matcher(s);
		if (matcher.find()) {
			return (matcher.start() + 1);
		}
		return -1;
	}
}
