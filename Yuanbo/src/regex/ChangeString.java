package regex;



/*FileName:ChangeString.java
 * 
 * ������:���
 * 
 * ����:�����û������ַ����е�������ͬ�ַ�����"aaabbbccc" -->"abc"��
 */

import java.io.*;
import java.util.regex.*;

public class ChangeString {

	public static void main(String[] args) {

		// ������ʽ����
		String[] s = { "a+", "b+", "c+", "d+", "e+", "f+", "g+", "h+", "i+",
				"j+", "k+", "l+", "m+", "n+", "o+", "p+", "q+", "r+", "s+",
				"t+", "u+", "v+", "w+", "x+", "y+", "z+", "A+", "B+", "C+",
				"D+", "E+", "F+", "G+", "H+", "I+", "J+", "K+", "L+", "M+",
				"N+", "O+", "P+", "Q+", "R+", "S+", "T+", "U+", "V+", "W+",
				"X+", "Y+", "Z+" };
		// �滻�ַ�������
		String[] t = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		String str = null;
		try {
			System.out.println("����������ַ���:");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			str = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// �жϲ��滻�ṹ
		for (int i = 0; i < s.length; i++) {

			Pattern p = Pattern.compile(s[i]);
			Matcher m = p.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				m.appendReplacement(sb, t[i]);
			}
			m.appendTail(sb);
			if (i == (s.length - 1)) {
				System.out.println("�޸ĺ��ַ���: " + sb.toString());
			}
			str = sb.toString();

		}

	}

}
