package xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Alexia
 * 
 *         SAX 解析XML文档
 */
public class SaxDemo implements XmlDocument {

	public void parserXml(String fileName) {
		SAXParserFactory saxfac = SAXParserFactory.newInstance();

		try {
			SAXParser saxparser = saxfac.newSAXParser();
			InputStream is = new FileInputStream(fileName);
			saxparser.parse(is, new MySAXHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class NMSRecord {
	String provider;
	String meterId;
	String unit;
	String startTime;
	String endTime;
	String tz;
	int interval;
	int no;
	double value;
	String st;

	NMSRecord() {
	}

	NMSRecord(String provider, String meterId, String unit, String startTime,
			String endTime, String tz) {
		this.provider = provider;
		this.meterId = meterId;
		this.unit = unit;
		this.startTime = startTime;
		this.tz = tz;
	}

	
	
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	/**
	 * 
	 * @function:  clone
	 * @param obj
	 * @return
	 * @author: zhuyuanbo    3 Dec, 2014 10:39:50 am
	 */
	public static NMSRecord clone(NMSRecord obj) {
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

}

class MySAXHandler extends DefaultHandler {
	boolean hasAttribute = false;
	Attributes attributes = null;

	public void startDocument() throws SAXException {
		System.out.println("文档开始打印了");
	}

	public void endDocument() throws SAXException {
		System.out.println("文档打印结束了");
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("users")) {
			return;
		}
		if (qName.equals("user")) {
			return;
		}
		if (attributes.getLength() > 0) {
			this.attributes = attributes;
			this.hasAttribute = true;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (hasAttribute && (attributes != null)) {
			for (int i = 0; i < attributes.getLength(); i++) {
				System.out.print(attributes.getQName(0) + ":"
						+ attributes.getValue(0));
			}
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.print(new String(ch, start, length));
	}

}