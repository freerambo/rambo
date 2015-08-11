package com.rambo.erian.service.spms;

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
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.service.PmWlgHourService;
import com.rambo.raw.entity.PmWlgRaw;
import com.rambo.raw.service.PmWlgRawService;


public class PmWlgRawServiceTest {

	private static PmWlgRawService service;
	private static PmWlgHourService pmWlgHourService;
	// recommend use in this way for slf4j 
	private static Logger log = LoggerFactory.getLogger(PmWlgRawServiceTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		service = (PmWlgRawService) context.getBean("pmWlgRawService");
		pmWlgHourService = (PmWlgHourService) context.getBean("pmWlgHourService");
		test();
//		testAddAll();
	}

	/**
	 * test function
	 */
	static void test() {
//		PmWlg pm = service.getPmWlg(2020765L);
//		log.info(pm.toString());
//		PmWlgHour pmh =  service.checkHourRawData(1,DateUtils.stringToDate("2014-10-29 12:00:00"),TimeConstants.TOTAL_OF_MINUTE);
//
//				log.warn(pmh.toString());
//		pmWlgHourService.savePmWlgHour(pmh);
//		pmWlgHourService.getAllPmWlgHour();
//		pmWlgHourService.createPmWlgHour1,DateUtils.stringToDate("2014-10-29 12:00:00"),TimeConstants.TOTAL_OF_MINUTE);

//		pmWlgHourService.createAllPmWlgHour(MeterConstants.METER_IDS_OF_SPMS, DateUtils.stringToDate("2014-11-04 22:00:00"), DateUtils.stringToDate("2014-11-11 11:00:00"));
		
//		pmWlgHourService.createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, DateUtils.stringToDate("2014-12-06 14:00:00"));

				
//		pmWlgHourService.hourlyJob();	
//		ListUtils.output(pmWlgHourService.checkDayDataFromHoursWithPmNum(1, DateUtils.stringToDate("2014-11-04 19:00:00"));
//		);
	}
	
	/**
	 * test all all hours
	 * @function:
	 * @author: zhuyuanbo    11 Nov, 2014 3:29:25 pm
	 */
	static void testAddAll(){ 
		Date start = DateUtils.stringToDate("2014-12-04 07:05:00");
		Date now = DateUtils.stringToDate("2014-12-04 09:05:00");
//				new Date();
		while (start.before(now)){
			pmWlgHourService.createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
			start = DateUtils.getNextHour(start);
		}		
	}



}
