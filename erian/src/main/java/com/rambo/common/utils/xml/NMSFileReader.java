package com.rambo.common.utils.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.rambo.common.utils.FileVisitor;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.db.DAOFactory;
import com.rambo.common.utils.db.NMSRecordDAO;
import com.rambo.erian.entity.NMSRecord;
import com.rambo.erian.entity.NmsKwhImpDay;
import com.rambo.erian.entity.NmsKwhImpHour;
import com.rambo.erian.service.PmWlgHourService;

/**
 * extend the SimpleFileVisitor
 * 
 * @author User1
 *
 * @param <Path>
 */
public class NMSFileReader<Path> extends SimpleFileVisitor<Path> {

	public static final String DATE_FORMAT = "yyyyMMdd";
	public static NMSFileReader reader = new NMSFileReader();
	public static final String path = reader.getClass().getClassLoader()
			.getResource("").getPath();
	// the prefix of target file
	// public static String prefix = "NMS_STD_";

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NMSFileReader.class);
	private static DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	private static NMSRecordDAO dao = df.getNMSRecordDAO();
	static String startTime = "NMS_STD_20150901";
	static String latest = "NMS_STD_20150901";

	/**
	 * trigged when visit the file
	 */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		String fName = file.toString();
		log.error("fName is {}",fName);
		String temp = fName;
		int i = fName.lastIndexOf("/"); // linux 
		i = i != -1? i:fName.lastIndexOf("\\");
		
		fName = fName.substring(i + 1);
		log.error("i is {} fName is {}, ",i,fName);
		if (fName.startsWith("NMS_STD_") && fName.compareTo(startTime) > 0) {
//			System.out.println("avaliable file: " + temp);
			System.out.println(fName + ", " + startTime + "," + latest);
			if (latest.compareTo(fName) < 0) {
				latest = fName.substring(0, 16);
			}
			log.error("file is {}",file);
			execute(file);
//			System.out.println(file);
			// log.error("file is {}",file);
			// return FileVisitResult.TERMINATE;
		}

		return FileVisitResult.CONTINUE;
	};

	public void execute(Path file) {
		XMLReader parser = null;
		try {
			parser = XMLReaderFactory.createXMLReader();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		NMSRecordHandler nMSRecordHandler = new NMSRecordHandler();
		parser.setContentHandler(nMSRecordHandler);

		try {
			log.error("execute the file : " + file.toString());
			parser.parse(file.toString());
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			System.err.println("exception occured!!  file:  " + file);
			e.printStackTrace();
		}

		List<NMSRecord> ls = nMSRecordHandler.records;

		List<NmsKwhImpDay> days = nMSRecordHandler.nmsDays;

		List<NmsKwhImpHour> hours = nMSRecordHandler.nmsHours;

		// System.out.println("size:  " + days.size());
		// ListUtils.output(days);
		// System.out.println("hour size:  " + hours.size());
		// ListUtils.output(hours);
		/**
		 * insert into db in batch
		 */

		/**
		 * insert the raw records
		 */
		dao.createNMSRecords(ls, "" + file);
		/**
		 * insert the daily records
		 */
		dao.createNMSDays(days, "" + file);
		/**
		 * insert the hourly records
		 */
		dao.createNMSHours(hours, "" + file);
	}

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
	 * 
	 * @return
	 */
	public String getPrefix() {

		// last_day_of_nms
		// String path =
		// this.getClass().getClassLoader().getResource("").getPath();
		try {
			File file = new File(path + "last_day_of_nms");
			return FileUtils.readFileToString(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		// return prefix + format.format(new Date());
		return null;
	}

	public void setPrefix(String s) {

		try {
			File file = new File(path + "last_day_of_nms");
			FileUtils.write(file, s);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * getPrefix with current date
	 * 
	 * @return
	 */
	public String getPrefixWithNow() {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return "NMS_STD_" + format.format(new Date());
	}

	public void readFile() {

		log.error("path {}", path);
		startTime = this.getPrefix();
		if (startTime == null || startTime.length() != 16) {
			log.error(
					"startTime is null, the content of file {} maight be some problems ",
					startTime);
		} else {
			String start = startTime.substring(8, 12) + "-"
					+ startTime.substring(12, 14) + "-"
					+ startTime.substring(14, 16);
			log.error("{},{}", startTime, start);
			try {
				// D:\erian-works\database\2014\01
				 Files.walkFileTree(
//				 Paths.get("/home", "/vincent","/test"), reader);
				 Paths.get("/home/vincent/test"), reader);

						 
//				Files.walkFileTree(Paths.get("D:", "erian-works", "database",
//						"2014", "test"), reader);
				dao.hourlyAndDailySynchronized(start);// 'yyyy-mm-dd' are
														// required
				log.error("the latest : {}", latest);
				this.setPrefix(latest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		// showAllFile();
		reader.readFile();
		// log.error("{}","NMS_STD_20150410060728478.xml".compareTo("NMS_STD_20150410")
		// > 0);
	}

}
