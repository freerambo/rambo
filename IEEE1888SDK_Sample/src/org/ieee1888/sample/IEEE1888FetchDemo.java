package org.ieee1888.sample;

import org.ieee1888.api.FetchDataStruct;
import org.ieee1888.api.FetchPointData;
import org.ieee1888.api.IEEE1888FetchManager;
import org.ieee1888.api.LimitCondinion;
import org.ieee1888.api.LimitType;
import org.ieee1888.api.PointDataStruct;
import org.ieee1888.types.SelectType;

/**
 * 
 * @description IEEE 1888 fetch demo
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Feb 2, 2016 1:08:12 PM
 */
public class IEEE1888FetchDemo {
	String serverUrl = "http://172.21.74.208/axis2/services/FIAPStorage";

	private void fetchOne() {
		// IEEE1888 Fetch Server adress

		// pointId
		String pointId = "http://taisyo.hongo.wide.ad.jp/test";

		try {// SelectType.maximum means fetch the latest data, similarly
				// minimum will retrieve the oldest value
			PointDataStruct[] pd = IEEE1888FetchManager.IEEE1888_clientFetch(serverUrl, pointId, SelectType.maximum);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fetchMany() {
		String pointId = "http://taisyo.hongo.wide.ad.jp/test";
		// add conditions for the fetch
		FetchDataStruct fetchdt = new FetchDataStruct();
		fetchdt.setServerURL(serverUrl);
		FetchPointData e = new FetchPointData();
		e.setPointID(pointId);
		// add the time conditions with ranges 2014-07-10T00:00:00+08:00 to
		// 2016-04-13T13:56:02.085+08:00
		e.getLimitCondition().add(new LimitCondinion(LimitType.LIMITTYPE_GTEQ, "2014-07-10T00:00:00+08:00"));
		e.getLimitCondition().add(new LimitCondinion(LimitType.LIMITTYPE_LTEQ, "2016-02-13T13:56:02.085+08:00"));
		fetchdt.getFetchPointData().add(e);
		try {// invoke the IEEE1888 SDK to fetch the data
			PointDataStruct[] pd1 = IEEE1888FetchManager.IEEE1888_clientFetch(fetchdt);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		IEEE1888FetchDemo ifDemo = new IEEE1888FetchDemo();
		ifDemo.fetchOne();
		// ifDemo.fetchMany();
	}
}
