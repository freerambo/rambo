package com.rambo.common.utils.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.raw.entity.NMSRecord;

public class NMSRecordHandler_update extends DefaultHandler {

	public List<NMSRecord> records;

	private NMSRecord currentNMSRecord;

	private NMSRecord tempNMSRecord;

	private String tempTag;
	private String preTag;

	private String meterId;
	private boolean flag = false;

	@Override
	public void startDocument() throws SAXException {

		records = new ArrayList<NMSRecord>();

		currentNMSRecord = null;
		tempNMSRecord = null;
		preTag = null;
		tempTag = null;
	}

	/**
	 * Start processing of an element.
	 * 
	 * @param namespaceURI
	 *            Namespace URI
	 * @param localName
	 *            The local name, without prefix
	 * @param qName
	 *            The qualified name, with prefix
	 * @param atts
	 *            The attributes of the element
	 */
	@Override
	public void startElement(String uri, String localName, String qName,

	Attributes attributes) throws SAXException {

			
			tempTag = preTag;
			preTag = qName;

	}

	public void endElement(String uri, String localName, String qName)

	throws SAXException {

		if (flag && "m1".equals(qName)) {

			// currentNMSRecord = new NMSRecord();

			// currentNMSRecord.setId(attributes.getValue("id"));

			records.add(tempNMSRecord);

			tempNMSRecord = null;
			preTag = null;
			tempTag = null;
			
			flag = false;

		}


	}

	public void characters(char ch[], int start, int length)

	throws SAXException {

//		if (preTag != null && currentNMSRecord != null) {

			/*
			 * if ("serviceProvider".equals(preTag)) {
			 * 
			 * currentNMSRecord.setProvider(value);
			 * 
			 * } else
			 */
		if (preTag != null) {

			String value = new String(ch, start, length);	

		
			if ("dvcIdN".equals(preTag)) {
//				System.out.println("start label " + qName);
				meterId = new String(ch, start, length);

			} else if ("mcIdN".equals(preTag)) {
//				String value = new String(ch, start, length);
				if ("KWH_IMP_INT".equals(value)) {

					flag = true;
					currentNMSRecord = new NMSRecord();
					currentNMSRecord.setUnit(value);
					currentNMSRecord.setMeterId(meterId);
				}

			}

			else if (flag) {

				if ("stDt".equals(preTag)) {
//					String value = new String(ch, start, length);
//					System.out.println("start label " + value);
//					DateTime dt = new DateTime(value);
					value = value.replaceAll("T", " ");
					currentNMSRecord.setStartTime(value);
//					currentNMSRecord.setStartTime(DateUtils.dateToString(dt
//							.toDate()));

				} else if ("enDt".equals(preTag)) {
//					String value = new String(ch, start, length);
//					DateTime dt = new DateTime(value);
//					currentNMSRecord.setEndTime(DateUtils.dateToString(dt
//							.toDate()));
					value = value.replaceAll("T", " ");
					currentNMSRecord.setStartTime(value);
					
				} else if ("tz".equals(preTag)) {
//					String value = new String(ch, start, length);
					currentNMSRecord.setTz(value);

				} else if ("spi".equals(preTag)) {
					currentNMSRecord.setInterval(Integer.parseInt(value));

				}

				else if ("ml".equals(preTag)) {
					tempNMSRecord = new NMSRecord(currentNMSRecord);

				} else if ( "s".equals(preTag) && "ml".equals(tempTag) ) {
//					String value = new String(ch, start, length);
					tempNMSRecord.setNo(Integer.parseInt(value));

				}

				else if ("q".equals(preTag)) {
//					String value = new String(ch, start, length);
					tempNMSRecord.setValue(Double.parseDouble(value));

				} else if ("st".equals(preTag)) {
//					String value = new String(ch, start, length);
					tempNMSRecord.setSt(value);

				}
			}
		}

	}

	
	/**
	 * Test method main fucntion 
	 * @param args
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SAXException, IOException {
		// new XML Reader
		XMLReader parser = XMLReaderFactory.createXMLReader();
		// new XML a handler for xml reader
		NMSRecordHandler_update nMSRecordHandler = new NMSRecordHandler_update();
		//bind the handler to the reader
		parser.setContentHandler(nMSRecordHandler);

		// System.currentTimeMillis used for calculate the exec time
		long ts = System.currentTimeMillis();
		//parse the target xml file 
		parser.parse("D:\\erian-works\\database\\2015\\NMS_STD_20150103060302336.xml");
		long te = System.currentTimeMillis();
		
		List<NMSRecord> ls = nMSRecordHandler.records;
		
		ListUtils.output(ls);
//		
		System.out.println(ls.size() + " "+(te - ts)+"ms");

	}

}