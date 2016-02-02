package org.ieee1888.sample;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.ieee1888.api.IEEE1888WriteManager;
import org.ieee1888.api.PointDataStruct;
import org.ieee1888.api.PointValueStruct;
import org.ieee1888.api.WriteDataStruct;

/**
 * 
 * @description IEEE 1888 write demo
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Feb 2, 2016 1:07:54 PM
 */
public class IEEE1888WriteDemo

{
	/**
	 * Write one single value to one point
	 */
	private void writeOne() {
		// serverUrl is IEEE1888 storage components address
		String serverUrl = "http://172.21.74.208/axis2/services/FIAPStorage";
		// pointid with uri format string
//		String pointId = "http://taisyo.hongo.wide.ad.jp/test";
		String pointId = "http://ict.erian.ntu.edu.sg/test/gw1/humidity";
//		String pointId = "http://ict.erian.ntu.edu.sg/test/gw1/temperature";
		
		// get current time
		Calendar c = Calendar.getInstance();
		// the value to be uploaded
		String value = "35";
		try {// invoke the IEEE1888 SDK to write the data
			int i = IEEE1888WriteManager.IEEE1888_clientWrite(serverUrl, pointId, value, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * write many value to one given point
	 */
	public void writeManyToOne() {
		// serverUrl is IEEE1888 storage components address
		String serverUrl = "http://172.21.74.208/axis2/services/FIAPStorage";
		// pointid with uri format string
		String pointId = "http://gutp.jp/test/CT08";
		PointDataStruct pointdata = new PointDataStruct();
		pointdata.setPointID(pointId);// add pointid
		pointdata.setServerUrl(serverUrl);// add serverUrl
		// add value1
		PointValueStruct pv1 = new PointValueStruct();
		Calendar c1 = Calendar.getInstance();
		pv1.setTime(c1);// add Time
		pv1.setValue("1");// add Value
		pointdata.getPointValueArray().add(pv1);
		// add value2
		PointValueStruct pv2 = new PointValueStruct();
		Calendar c2 = Calendar.getInstance();
		Date t = new Date();
		c2.setTime(new Date(t.getTime() + 1000));// make it different with PV1 by adding 1 second
		pv2.setTime(c2);// add Time
		pv2.setValue("2");// add Value
		pointdata.getPointValueArray().add(pv2);
		try { // if not set up serverUrl, then will use that of pointdate
			int j = IEEE1888WriteManager.IEEE1888_clientWrite(serverUrl, pointdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * write many values to multiple points
	 */
	public void writeManyToMany() {
		// serverUrl is IEEE1888 storage components address
		String serverUrl = "http://172.21.74.208/axis2/services/FIAPStorage";
		// pointid with uri format string
		String pointId1 = "http://taisyo.hongo.wide.ad.jp/test";
		String pointId2 = "http://gutp.jp/test/CT09";

		// add pointId1
		PointDataStruct pointdata1 = new PointDataStruct();
		pointdata1.setPointID(pointId1);
		pointdata1.setServerUrl(serverUrl);
		// add first value to pointId1
		PointValueStruct pv1 = new PointValueStruct();
		Calendar c1 = Calendar.getInstance();
		pv1.setTime(c1);// add Time
		pv1.setValue("1");// add Value
		pointdata1.getPointValueArray().add(pv1);
		// add second value to pointId1
		PointValueStruct pv2 = new PointValueStruct();
		Calendar c2 = Calendar.getInstance();
		Date t = new Date();
		c2.setTime(new Date(t.getTime() + 1000));// make it different with PV1
													// by adding 1 second
		pv2.setTime(c2); // add time
		pv2.setValue("2");// add Value
		pointdata1.getPointValueArray().add(pv2);

		// add pointId2
		PointDataStruct pointdata2 = new PointDataStruct();
		pointdata2.setPointID(pointId2);
		pointdata2.setServerUrl(serverUrl);
		// add first value to pointId2
		PointValueStruct pv21 = new PointValueStruct();
		Calendar c21 = Calendar.getInstance();
		pv21.setTime(c21);// add Time
		pv21.setValue("21");// add Value
		pointdata2.getPointValueArray().add(pv21);
		// add second value to pointIdw
		PointValueStruct pv22 = new PointValueStruct();
		Calendar c22 = Calendar.getInstance();
		c22.setTime(new Date(t.getTime() + 1000));// make it different with PV1
													// by adding 1 second
		pv22.setTime(c22);
		pv22.setValue("22");// Value
		pointdata2.getPointValueArray().add(pv22);
		// add multiple Points
		WriteDataStruct wDS = new WriteDataStruct();
		ArrayList<PointDataStruct> pointDataArr = new ArrayList<PointDataStruct>();
		pointDataArr.add(pointdata1);
		pointDataArr.add(pointdata2);

		wDS.setPointData(pointDataArr);
		try {
			// write many data to multiple points
			int succFlag = IEEE1888WriteManager.IEEE1888_clientWrite(wDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		IEEE1888WriteDemo iwDemo = new IEEE1888WriteDemo();
		iwDemo.writeOne();
		// iwDemo.writeManyToOne();
		// iwDemo.writeManyToMany();
	}
}
