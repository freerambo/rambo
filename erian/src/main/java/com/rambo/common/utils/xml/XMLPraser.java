package com.rambo.common.utils.xml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.db.DAOFactory;
import com.rambo.common.utils.db.NMSRecordDAO;
import com.rambo.erian.entity.NMSRecord;

public class XMLPraser {
	public static void main(String[] args) throws SAXException, IOException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		NMSRecordHandler NMSRecordHandler = new NMSRecordHandler();
		
		NmsXmlHandler nmsXmlHandler = new NmsXmlHandler();
		
		parser.setContentHandler(nmsXmlHandler);
//		parser.setContentHandler(NMSRecordHandler);

//		parser.parse("C:\\Users\\User1\\Desktop\\NMS_STD_20141105060713973.xml");
//		File f = new File("D:\\erian-works\\raw_data\\selected");
		long ts = System.currentTimeMillis();
		parser.parse("D:\\erian-works\\database\\2014\\01\\NMS_STD_20140101060615545.xml");
		long te = System.currentTimeMillis();
		
		List<NMSRecord> ls = nmsXmlHandler.records;
		
		ListUtils.output(ls);
//		
		System.out.println(ls.size() + " "+(te - ts)+"ms");
		/**
		 * insert into db in batch
		 */
	/*	DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		NMSRecordDAO dao  = df.getNMSRecordDAO();
		
		dao.createNMSRecords(ls);
		
		System.out.print("inserted total size of records " + ls.size());*/
/*		NMSRecord record = new NMSRecord();
		record.setMeterId("123");
		record.setUnit("rrr");
		record.setProvider("hello");
		dao.createNMSRecord(record);*/
		
		
		
//		boolean flag = dao.isExists(null);
//		System.out.print(flag);
	}
	
	

}

