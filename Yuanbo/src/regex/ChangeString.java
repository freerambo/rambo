package regex;



/*FileName:ChangeString.java
 * 
 * 开发者:陈璋
 * 
 * 功能:处理用户输入字符串中的连续相同字符，如"aaabbbccc" -->"abc"。
 */

import java.io.*;
import java.util.regex.*;

public class ChangeString {

	public static void main(String[] args) {

		// 正则表达式数组
		String[] s = { "a+", "b+", "c+", "d+", "e+", "f+", "g+", "h+", "i+",
				"j+", "k+", "l+", "m+", "n+", "o+", "p+", "q+", "r+", "s+",
				"t+", "u+", "v+", "w+", "x+", "y+", "z+", "A+", "B+", "C+",
				"D+", "E+", "F+", "G+", "H+", "I+", "J+", "K+", "L+", "M+",
				"N+", "O+", "P+", "Q+", "R+", "S+", "T+", "U+", "V+", "W+",
				"X+", "Y+", "Z+" };
		// 替换字符串数组
		String[] t = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		String str = null;
		try {
			System.out.println("请输入测试字符串:");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			str = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 判断并替换结构
		for (int i = 0; i < s.length; i++) {

			Pattern p = Pattern.compile(s[i]);
			Matcher m = p.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				m.appendReplacement(sb, t[i]);
			}
			m.appendTail(sb);
			if (i == (s.length - 1)) {
				System.out.println("修改后字符串: " + sb.toString());
			}
			str = sb.toString();

		}

	}

}
