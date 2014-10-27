package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//author:方勇程
public class RegExpTest {
	/**
	 * 说明：args[0]是要替换的串，如java args[1]是要更改的串，如javajavadocjavadocdoc
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
			System.out.println("输入格式错误！！");
		}
		System.out.println(findByReg("abedefg", "9"));
	}

	/**
	 * @param s
	 *            要匹配的源字符串
	 * @param reg
	 *            所匹配的正则表达式
	 * @return 第一次匹配的位置 -1 表示未找到
	 * @description
	 * @version currentVersion
	 * @author Rambo
	 * @createtime 2013年11月25日 上午10:28:23
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
