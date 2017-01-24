package xml;

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

public class NMSSAXParser {

	class NMSRecordHandler extends DefaultHandler {



		private List<NMSRecord> NMSRecords;

		private NMSRecord currentNMSRecord;
		
		private NMSRecord tempNMSRecord;

		private String preTag;



		@Override
		public void startDocument() throws SAXException {

			NMSRecords = new ArrayList<NMSRecord>();

			currentNMSRecord = null;
			tempNMSRecord = null;
			preTag = null;

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

				//currentNMSRecord.setId(attributes.getValue("id"));

			}

			preTag = qName;

		}

		public void endElement(String uri, String localName, String qName)

		throws SAXException {

			if ("ml".equals(qName)) {

				NMSRecords.add(tempNMSRecord);

				tempNMSRecord = null;

			}else if("D1-IntialLoadIMD".equals(qName)){
				currentNMSRecord = null;
			}

			preTag = null;

		}

		public void characters(char ch[], int start, int length)

		throws SAXException {

			if (preTag != null && currentNMSRecord != null) {

				String value = new String(ch, start, length);

				if ("dvcIdN".equals(preTag)) {

					currentNMSRecord.setName(value);

				}else if ("ml".equals(preTag)) {
					tempNMSRecord = new NMSRecord(currentNMSRecord);

				}
				else if ("q".equals(preTag)) {
					tempNMSRecord.setPrice(Double.parseDouble(value));

				}

			}

		}

	}

	public static void main(String[] args) throws SAXException, IOException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		NMSRecordHandler NMSRecordHandler = (new NMSSAXParser()).new NMSRecordHandler();
		parser.setContentHandler(NMSRecordHandler);

		parser.parse("D:\\NMS_STD_20150703060241764.xml");
		System.out.println(NMSRecordHandler.NMSRecords);

	}
	
	
	/**
	 * 
	 * @function:  clone
	 * @param obj
	 * @return
	 * @author: zhuyuanbo    3 Dec, 2014 10:39:50 am
	 */
	public NMSRecord clone(NMSRecord obj) {
		try {
			NMSRecord clone = obj.getClass().newInstance();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				field.set(clone, field.get(obj));
			}
			return clone;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	class NMSRecord {

		NMSRecord(){}
		
		NMSRecord(NMSRecord obj){
			this.name = obj.name;
		}
		
		private String name;

		private double price;

		

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

	
		
		@Override
		public String toString() {
			return "NMSRecord [ name=" + name + ", price=" + price
					+ "]\n";
		}
		
	

	}
}


