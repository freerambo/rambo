package nms;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLPraser {
	public static void main(String[] args) throws SAXException, IOException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		NMSRecordHandler NMSRecordHandler = new NMSRecordHandler();
		parser.setContentHandler(NMSRecordHandler);

		parser.parse("C:\\Users\\User1\\Desktop\\NMS_STD_20141105060713973.xml");

		for (NMSRecord obj : NMSRecordHandler.records) {
			System.out.println(obj);
		}

	}

}

class NMSRecordHandler extends DefaultHandler {

	List<NMSRecord> records;

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

			// currentNMSRecord.setId(attributes.getValue("id"));

		}
		tempTag = preTag;
		preTag = qName;

	}

	public void endElement(String uri, String localName, String qName)

	throws SAXException {

		if ("ml".equals(qName)) {

			records.add(tempNMSRecord);

			tempNMSRecord = null;

		} else if ("D1-IntialLoadIMD".equals(qName)) {
			currentNMSRecord = null;
		}

		preTag = null;

	}

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

				currentNMSRecord.setStartTime(value);

			} else if ("enDt".equals(preTag)) {

				currentNMSRecord.setEndTime(value);

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

}
