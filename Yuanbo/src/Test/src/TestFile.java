package Test.src;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TestFile {

	public static void main(String[] args) {

		File f = new File("D:\\公司文档");
		String s = "";
		File[] files = f.listFiles();
		for (File fi : files) {
			if (fi.isFile())
				System.out.println(s + "文件" + fi + "," + fi.length() / 1024 + "KB,"
						+ "，" + f.getAbsolutePath());
			else if (fi.isDirectory()) {
				
				System.out.println(s + "目录" + fi + ","
						+ new Date(fi.lastModified()));
				s += "\t";
			}
		}
	}
}
