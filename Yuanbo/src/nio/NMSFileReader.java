package nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * extend the SimpleFileVisitor
 * 
 * @author User1
 *
 * @param <Path>
 */
public class NMSFileReader<Path> extends SimpleFileVisitor<Path> {

	public static final String DATE_FORMAT = "yyyyMMdd";

	// the prefix of target file
	public static String prefix = "NMS_STD_";

	/**
	 * trigged when visit the file
	 */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		String fName = file.toString();
		fName = fName.substring(fName.lastIndexOf("\\") + 1);
		// System.out.println(fName + ", " + this.getPrefix());

		if (fName.startsWith(this.getPrefix())) {

			System.out.println(file);
			// return FileVisitResult.TERMINATE;
		}

		return FileVisitResult.CONTINUE;
	};

	/**
	 * trigged before visit the Directory
	 */
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		// System.out.println("Directory:  " + dir + " start");

		return FileVisitResult.CONTINUE;
	};

	/**
	 * trigged after visit the Directory
	 */
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		Objects.requireNonNull(dir);
		if (exc != null)
			throw exc;
		// System.out.println("Directory:  " + dir + " end");
		return FileVisitResult.CONTINUE;
	}

	/**
	 * getPrefix with current date
	 * @return
	 */
	public String getPrefix() {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return prefix + format.format(new Date());
	}

	/**
	 * getPrefix with current date
	 * @return
	 */
	public String getPrefix(String s) {
		return prefix + s;
	}

	public void readFile() {

		try {
			// D:\erian-works\database\2014\01
			Files.walkFileTree(
					Paths.get("D:", "erian-works", "database", "2014", "new"),
					new NMSFileReader());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	  * 
	  */
	public static void showAllFile() {

		try {
			// D:\erian-works\database\2014\01
			Files.walkFileTree(
					Paths.get("D:", "erian-works", "database", "2014", "01"),
					new NMSFileReader());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// showAllFile();
		new NMSFileReader().readFile();
	}

}
