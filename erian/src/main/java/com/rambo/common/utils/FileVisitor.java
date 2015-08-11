package com.rambo.common.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.rambo.common.utils.db.DAOFactory;
import com.rambo.common.utils.db.NMSRecordDAO;
import com.rambo.common.utils.xml.NMSRecordHandler;
import com.rambo.erian.entity.NMSRecord;
import com.rambo.erian.entity.NmsKwhImpDay;
import com.rambo.erian.entity.NmsKwhImpHour;

/**
 * extends the SimpleFileVisitor
 * **/
public class FileVisitor<Path> extends SimpleFileVisitor<Path> {

	/**
	 * when visit the file this function will works
	 * */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

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
			parser.parse(file.toString());
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			System.err.println("file:  " + file);
			e.printStackTrace();
		}

		List<NMSRecord> ls = nMSRecordHandler.records;

		List<NmsKwhImpDay> days = nMSRecordHandler.nmsDays;

		List<NmsKwhImpHour> hours = nMSRecordHandler.nmsHours;

		// System.out.println("size:  "+days.size());
		// ListUtils.output(days);
		//
		// System.out.println("hour size:  "+hours.size());
		// ListUtils.output(hours);
		/**
		 * insert into db in batch
		 */
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		NMSRecordDAO dao = df.getNMSRecordDAO();

		/**
		 * insert the raw records
		 */
		 dao.createNMSRecords(ls,""+file);
		/**
		 * insert the daily records
		 */
		dao.createNMSDays(days, "" + file);
		/**
		 * insert the hourly records
		 */
		 dao.createNMSHours(hours,""+file);

		return FileVisitResult.CONTINUE;
	};

	/**
	 * before visit the directory will trigger this function
	 * 
	 * **/
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		// System.out.println("directory:  "+dir +" start");
		return FileVisitResult.CONTINUE;
	};

	/**
	 * after visite the directory will trigger this function
	 * 
	 * **/
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		Objects.requireNonNull(dir);
		if (exc != null)
			throw exc;
		// System.out.println("directory:  "+dir +" end");
		return FileVisitResult.CONTINUE;
	}

	/**
	 * 
	 * 
	 * @description will visit the directory input
	 * @version currentVersion
	 * @author Rambo
	 * @createtime  9 Feb, 2015 4:20:36 pm
	 */
	public static void showAllFile() {
		/**
		 * input the directory to be calculated
		 */
		try {
			Files.walkFileTree(
					Paths.get("D:", "erian-works", "database", "current"),
					new FileVisitor());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @function: main function
	 * @param args
	 * @author: zhuyuanbo 6 Feb, 2015 11:46:12 am
	 */
	public static void main(String[] args) {
		long ts = System.currentTimeMillis();
		showAllFile();
		long te = System.currentTimeMillis();
		System.out.println("exec time " + (te - ts));
	}

}
