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
import com.rambo.erian.entity.NMSRecord;

public class NmsXmlHandler extends DefaultHandler {

	public List<NMSRecord> records;

	private NMSRecord currentNMSRecord;

	private NMSRecord tempNMSRecord;

	private String tempTag;
	private String preTag;

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

		if ("D1-IntialLoadIMD".equals(qName)) {

			currentNMSRecord = new NMSRecord();

		}
		tempTag = preTag;
		preTag = qName;

	}

	/**
	 * end Element
	 */
	public void endElement(String uri, String localName, String qName)

	throws SAXException {

		if ("ml".equals(qName)) {

			tempNMSRecord.setId();// set the id
			records.add(tempNMSRecord);

			// tempNMSRecord = null;

		} else if ("D1-IntialLoadIMD".equals(qName)) {

			currentNMSRecord = null;

			tempNMSRecord = null;
		}

		preTag = null;

	}

	/**
	 * core function,will parse all tags and assign the value
	 */
	public void characters(char ch[], int start, int length)

	throws SAXException {

		if (preTag != null && currentNMSRecord != null) {

			String value = new String(ch, start, length);

			if ("serviceProvider".equals(preTag)) {

				currentNMSRecord.setProvider(value);

			} else if ("dvcIdN".equals(preTag)) {

				currentNMSRecord.setMeterId(value);

			} else if ("mcIdN".equals(preTag)) {

				currentNMSRecord.setUnit(value);

			} else if ("stDt".equals(preTag)) {
				DateTime dt = new DateTime(value);
				currentNMSRecord.setStartTime(DateUtils.dateToString(dt
						.toDate()));

			} else if ("enDt".equals(preTag)) {
				DateTime dt = new DateTime(value);
				currentNMSRecord
						.setEndTime(DateUtils.dateToString(dt.toDate()));

			} else if ("tz".equals(preTag)) {

				currentNMSRecord.setTz(value);
				;

			} else if ("spi".equals(preTag)) {

				currentNMSRecord.setInterval(Integer.parseInt(value));

			}

			else if ("ml".equals(preTag)) {
				tempNMSRecord = new NMSRecord(currentNMSRecord);

			} else if ("ml".equals(tempTag) && "s".equals(preTag)) {
				tempNMSRecord.setNo(Integer.parseInt(value));

			}

			else if ("q".equals(preTag)) {
				tempNMSRecord.setValue(Double.parseDouble(value));

			} else if ("st".equals(preTag)) {
				tempNMSRecord.setSt(value);
			}
		}

	}

	/**
	 * main function, the entrance of program
	 * @param args
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SAXException, IOException {
		XMLReader parser = XMLReaderFactory.createXMLReader();

		NmsXmlHandler nmsXmlHandler = new NmsXmlHandler();

		parser.setContentHandler(nmsXmlHandler);

		long ts = System.currentTimeMillis();
		parser.parse("D:\\erian-works\\database\\2014\\01\\NMS_STD_20140101060615545.xml");
		long te = System.currentTimeMillis();

		List<NMSRecord> ls = nmsXmlHandler.records;

		ListUtils.output(ls);
		System.out.println("results in total : " + ls.size() + ", exec time : "
				+ (te - ts) + "ms");

	}
}