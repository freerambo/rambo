package com.rambo.erian.service.nms;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.DayExport;
import com.rambo.erian.entity.NmsDayResult;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.entity.PmWlgWeek;
import com.rambo.erian.service.PmWlgDayService;
import com.rambo.erian.service.PmWlgHourService;
import com.rambo.spms.entity.PmWlg;
import com.rambo.spms.service.PmWlgService;

/**
 * 
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 12 Nov, 2014 10:08:33 pm
 */
public class NmsServiceTest {

	public final static List<String> LIST_OF_METERs_OF_NMS = Lists.newArrayList("KWH_IMP_INT");	
	private static NmsService service;
	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NmsServiceTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		service = (NmsService) context.getBean("nmsService");
		// test();
//		 addAll();
//		testRegin();
//		 creatweek();
//		 creatMonth();
		 
		 getAllDayResults();
	}

	/**
	 * test function
	 */
	static void test() {

		// service.createPmWlgDayMeters(MeterConstants.METER_IDS_OF_SPMS,
		// "KVARH_EXP_INT"
		// DateUtils.stringToDate("2014-11-18 13:00:00"));
		service.createDailyForMeter("DA3510108", "2014-11-01 00:00:00",
				MeterConstants.TYPES_OF_METERs_OF_NMS.KVARH_EXP_INT.toString());
	}

	static void testRegin() {

		// service.createPmWlgDayMeters(MeterConstants.METER_IDS_OF_SPMS,
		// "KVARH_EXP_INT"
		// DateUtils.stringToDate("2014-11-18 13:00:00"));
		// service.generateDataByLocation("Nanyang Terrace",
		// "2014-11-15 00:00:00", "KVARH_EXP_INT");
		// service.generateDataByLocations(MeterConstants.LOCATIONS_OF_METERs_OF_NMS,"2014-11-01 00:00:00",
		// "KVARH_EXP_INT");

		// for(Date s = DateUtils.stringToDate("2014-10-23 00:00:00");
		// !s.after(DateUtils.stringToDate("2014-11-07 00:00:00")); s =
		// DateUtils.getNextDay(s)){
		// service.createNmsDayResultByLocation("Nanyang Terrace",
		// "2014-11-11 00:00:00", "KVARH_EXP_INT");
		// }
//		service.createResultByLocations(
//				MeterConstants.LOCATIONS_OF_METERs_OF_NMS,
//				"2014-11-01 00:00:00", "KVARH_EXP_INT");
//		service.createResultForAll(MeterConstants.LOCATIONS_OF_METERs_OF_NMS,
//				"2014-10-23 00:00:00", "2014-11-07 00:00:00",
//				MeterConstants.LIST_OF_METERs_OF_NMS);
		service.createResultForAll(MeterConstants.LOCATIONS_OF_METERs_OF_NMS,
				"2014-10-23 00:00:00", "2014-11-07 00:00:00",
				MeterConstants.LIST_OF_METERs_OF_NMS);
	}

	static void addAll() {

		Date start = DateUtils.stringToDate("2014-10-23 00:00:00");
		Date end = DateUtils.stringToDate("2014-10-23 00:00:00");
//		
//		service.createAllRecords("2015-01-02 00:00:00", "2015-01-12 00:00:00",
//				LIST_OF_METERs_OF_NMS);
		
//		service.createAllRecords("2014-11-30 00:00:00", "2014-12-18 01:00:00",
//				LIST_OF_METERs_OF_NMS);
//		service.createAllRecords("2014-10-01 00:00:00", "2014-10-22 00:00:00",
//				LIST_OF_METERs_OF_NMS);
		
//		service.createAllRecords("2014-08-31 00:00:00", "2014-09-29 00:00:00",
//				LIST_OF_METERs_OF_NMS);
		
//		service.createAllRecords("2014-07-31 00:00:00", "2014-08-30 00:00:00",
//				LIST_OF_METERs_OF_NMS);
//		
//		service.createAllRecords("2014-03-12 00:00:00", "2014-10-08 00:00:00",
//				LIST_OF_METERs_OF_NMS);
		

//		service.createAllRecords("2014-05-31 00:00:00", "2014-07-30 01:00:00",
//				LIST_OF_METERs_OF_NMS);

		service.createAllRecords("2014-07-31 00:00:00", "2014-08-30 00:00:00",
				LIST_OF_METERs_OF_NMS);
			
			
	}
	
	static void creatweek(){
		
		service.createWeekData("2015-02-05 00:00:00", DateUtils.dateToString(new Date()));
	}
	
	static void creatMonth(){
		
		service.createMonthData("2015-02-05 00:00:00", DateUtils.dateToString(new Date()));
	}

	static void getAllDayResults(){
		
		List<DayExport>  ls = service.getAllDayResults();
		ListUtils.output(ls);
	}
}
