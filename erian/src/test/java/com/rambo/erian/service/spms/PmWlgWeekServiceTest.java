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
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.entity.PmWlgWeek;
import com.rambo.erian.service.PmWlgDayService;
import com.rambo.erian.service.PmWlgHourService;
import com.rambo.erian.service.PmWlgWeekService;
import com.rambo.spms.entity.PmWlg;
import com.rambo.spms.service.PmWlgService;


public class PmWlgWeekServiceTest {

	private static PmWlgWeekService service;
	// recommend use in this way for slf4j 
	private static Logger log = LoggerFactory.getLogger(PmWlgWeekServiceTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		service = (PmWlgWeekService) context.getBean("pmWlgWeekService");
//		test();
		testAddAll();
	}

	/**
	 * test function
	 */
	static void test() {
//		PmWlg pm = service.getPmWlg(1L);
//		log.info(pm.toString());
//		PmWlgHour pmh =  service.checkHourRawData(1,DateUtils.stringToDate("2014-10-28 12:00:00"),TimeConstants.TOTAL_OF_MINUTE);
//		log.info(pmh.toString());
//		pmWlgHourService.savePmWlgHour(pmh);
//		pmWlgHourService.getAllPmWlgHour();
		
//		pmWlgHourService.createPmWlgHourFromSPMS(1,DateUtils.stringToDate("2014-10-28 12:00:00"),TimeConstants.TOTAL_OF_MINUTE);

//		boolean flag = service.getPmWlgDayMeters(MeterConstants.METER_IDS_OF_SPMS, DateUtils.stringToDate("2014-11-03 13:00:00"));
//		System.out.println(flag);
//		
//		service.generatePmWlgDaysByMeters(MeterConstants.METER_IDS_OF_SPMS, DateUtils.stringToDate("2014-07-01 13:00:00"), DateUtils.stringToDate("2014-11-03 13:00:00"));
		List<Integer> pmIds = Lists.newArrayList(1);
//		List<PmWlgDay> pwds = service.getByPmIdsWithStartAndEnd(pmIds, DateUtils.stringToDate("2014-07-01 13:00:00"), DateUtils.stringToDate("2014-7-03 13:00:00"));	
//		System.out.println(pwds.size());
		
//		ListUtils.output(pwds);
		
//		service.getFromDayByPmIdWithDate(1, DateUtils.stringToDate("2014-07-08 13:00:00"));
//		log.warn(service.getFromDayByPmIdWithDate(1, DateUtils.stringToDate("2014-10-10 13:00:00")).toString());

		
//		List<PmWlgWeek> pwws = service.getFromDayByPmIdsWithDate(pmIds, DateUtils.stringToDate("2014-09-08 13:00:00"));	
//		System.out.println(pwws.size());
//		
//		ListUtils.output(pwws);
//	
//		
//		service.createPmWlgWeekByPmIdWithDate(1, DateUtils.stringToDate("2014-07-14 13:00:00"));
		
		service.createPmWlgWeeksByPmIdsWithDate(MeterConstants.METER_IDS_OF_SPMS, DateUtils.stringToDate("2014-12-05 13:00:00"));	
//		Date start = DateUtils.stringToDate("2014-07-01 13:00:00");
//		Date now = new Date();
//		while (start.before(now)){
//			service.createPmWlgWeeksByPmIdsWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
//			start = DateUtils.getNextWeek(start);
//		}
		
	}
	
	/**
	 * test all all week
	 * @function:
	 * @author: zhuyuanbo    11 Nov, 2014 3:29:25 pm
	 */
	static void testAddAll(){
		Date start = DateUtils.stringToDate("2015-02-23 00:00:00");
		Date now = new Date();
		while (DateUtils.getLastDayOfWeek(start).before(now)){
			service.createPmWlgWeeksByPmIdsWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
			start = DateUtils.getNextWeek(start);
		}
		
	}
	
	

}
