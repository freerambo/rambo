package com.rambo.erian.service.spms;

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
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.service.PmWlgHourService;
import com.rambo.spms.entity.PmWlg;
import com.rambo.spms.service.PmWlgService;


public class PmWlgServiceTest {

	private static PmWlgService service;
	private static PmWlgHourService pmWlgHourService;
	// recommend use in this way for slf4j 
	private static Logger log = LoggerFactory.getLogger(PmWlgServiceTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		service = (PmWlgService) context.getBean("pmWlgService");
		pmWlgHourService = (PmWlgHourService) context.getBean("pmWlgHourService");
		test();
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
		
//		pmWlgHourService.createPmWlgHourFromSPMS(1,DateUtils.stringToDate("2014-10-28 13:00:00"),TimeConstants.TOTAL_OF_MINUTE);

		pmWlgHourService.createAllPmWlgHourFromSPMS(MeterConstants.METER_IDS_OF_SPMS, DateUtils.stringToDate("2014-07-01 00:00:00"), null);
	}

}
