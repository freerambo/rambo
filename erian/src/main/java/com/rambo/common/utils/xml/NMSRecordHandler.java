package com.rambo.common.utils.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.StatisticsDouble;
import com.rambo.erian.entity.NMSRecord;
import com.rambo.erian.entity.NmsDay;
import com.rambo.erian.entity.NmsKwhImpDay;
import com.rambo.erian.entity.NmsKwhImpHour;


public class NMSRecordHandler extends DefaultHandler {

	public List<NMSRecord> records;
	
	public List<NmsKwhImpDay> nmsDays;
	public List<NmsKwhImpHour> nmsHours;

	private NMSRecord currentNMSRecord;

	private NMSRecord tempNMSRecord;

	private String tempTag;
	private String preTag;
	
	private int count = 0;
	private boolean flag = false;
	
	private double daySum = 0.0,max = Double.MIN_VALUE, min = Double.MAX_VALUE;
	
	private double hourSum = 0.0, temp = 0.0;
	List<Double> values = new ArrayList<Double>();
	Date dtMax = null, dtMin = null;
	String startTime = null;
	@Override
	public void startDocument() throws SAXException {

		records = new ArrayList<NMSRecord>();
		nmsDays = new ArrayList<NmsKwhImpDay>();
		nmsHours = new ArrayList<NmsKwhImpHour>();
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

	public void endElement(String uri, String localName, String qName)

	throws SAXException {

		if ("ml".equals(qName)) {
			if(flag){
				
				
				tempNMSRecord.setId();//set the id 
				records.add(tempNMSRecord);
				
				double value = tempNMSRecord.getValue();
				
				
				
				//calculate the daily
				daySum += value;
				values.add(value);
				
				String dt = tempNMSRecord.getStartTime();

				//initialize
				if(count == 0){
					startTime = dt;
					max = min = value;
					dtMax = dtMin = DateUtils.stringToDate(dt);
				}
				
//				long no = tempNMSRecord.getNo();
				if(max < value){
					max = value;
					dtMax = DateUtils.getDateByStringAddTime(dt, tempNMSRecord.getInterval()
							* (tempNMSRecord.getNo()) * 1000);
				}
				if(min < value){
					min = value;
					dtMin = DateUtils.getDateByStringAddTime(dt, tempNMSRecord.getInterval()
							* (tempNMSRecord.getNo()) * 1000);
				}
				
				count ++;
				
				if(count % 2 ==0){
					
					
					String datetime = DateUtils.dateToString(DateUtils.getEndMinuteOfHour(DateUtils.getDateByStringAddTime(dt, tempNMSRecord.getInterval()
							* (tempNMSRecord.getNo()) * 1000)));

					NmsKwhImpHour nmsHour = new NmsKwhImpHour(datetime,temp + value,tempNMSRecord.getUnit(),tempNMSRecord.getMeterId());
					nmsHours.add(nmsHour);
				}
				
				temp = value;
				
			}
			
//			tempNMSRecord = null;

		} else if ("D1-IntialLoadIMD".equals(qName)) {
			
		
			if(flag){
				StatisticsDouble st = new StatisticsDouble(values, daySum
						/ count);
//				System.out.println(startTime +","+dtMax+","+dtMin+","+ currentNMSRecord.getUnit());
				startTime = DateUtils.dateToString(DateUtils.getEndHourOftheDay(DateUtils.stringToDate(startTime)));
				NmsKwhImpDay nmsDay = new NmsKwhImpDay(startTime, daySum, currentNMSRecord.getUnit(), st.getStdDev(), max,
						DateUtils.dateToString(dtMax), min, DateUtils.dateToString(dtMin), currentNMSRecord.getMeterId());
//				System.out.println(nmsDay);	
				
				nmsDays.add(nmsDay);
				
				
				if(count <= 48 && count % 2 == 1){
					
					
					String datetime = DateUtils.dateToString(DateUtils.getEndMinuteOfHour(DateUtils.getDateByStringAddTime(tempNMSRecord.getStartTime(), tempNMSRecord.getInterval()
							* (tempNMSRecord.getNo() + 1) * 1000)));
					NmsKwhImpHour nmsHour = new NmsKwhImpHour(datetime,tempNMSRecord.getValue(),tempNMSRecord.getUnit(),tempNMSRecord.getMeterId());
					
					nmsHours.add(nmsHour);
				}
				
			}
			
			currentNMSRecord = null;
			flag = false;
			count = 0;
			
			temp = 0;
			tempNMSRecord = null;
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
				
				if ("KWH_IMP_INT".equals(value)) {
					
					flag = true;
					currentNMSRecord.setUnit(value);
				}

			} else if ("stDt".equals(preTag)) {
				DateTime dt = new DateTime(value);
				currentNMSRecord.setStartTime(DateUtils.dateToString(dt.toDate()));

			} else if ("enDt".equals(preTag)) {
				DateTime dt = new DateTime(value);
				currentNMSRecord.setEndTime(DateUtils.dateToString(dt.toDate()));

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