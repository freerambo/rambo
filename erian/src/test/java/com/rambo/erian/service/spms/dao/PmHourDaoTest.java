package com.rambo.erian.service.spms.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springside.modules.test.spring.Profiles;

import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.*;
import com.rambo.erian.repository.*;
import com.rambo.erian.service.*;
import com.rambo.raw.entity.PmWlgRaw;
import com.rambo.raw.repository.*;


public class PmHourDaoTest {
	private static PmWlgRawDao rawDao;
	private static PmWlgHourDao dao;
	private static PmWlgDayDao dayDao;
	
	private static PmWlgHourService pmWlgHourService;
	private static PmWlgDayService pmWlgDayService;

	// recommend use in this way for slf4j 
	private static Logger log = LoggerFactory.getLogger(PmHourDaoTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		dao = (PmWlgHourDao) context.getBean("pmWlgHourDao");
		dayDao  = (PmWlgDayDao) context.getBean("pmWlgDayDao");
		rawDao = (PmWlgRawDao) context.getBean("pmWlgRawDao");
		
		pmWlgHourService = (PmWlgHourService) context.getBean("pmWlgHourService");
		pmWlgDayService = (PmWlgDayService) context.getBean("pmWlgDayService");

		testHour();
		testDay();
//		testAddAll();
//		testSumHour();
//		testRaw();
	}

	/**
	 * test function
	 */
	static void testHour() {
		Date start = DateUtils.stringToDate("2015-01-20 00:00:00"); 
		Date now = new Date();
		while (!DateUtils.getNextHour(start).after(now)){
			log.info(start.toString());
			List<PmWlgHour> pwhs = dao.findByDateTime(start);
			if(ListUtils.isEmpty(pwhs)){
				log.error("results is null or wrong number for time {}", start);
				pmWlgHourService.createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
//				break;
			}
			start = DateUtils.getNextHour(start);
		}	
	}
	/**
	 * 
	 * @function: check the hour data if missing then create 
	 * @author: zhuyuanbo    21 Nov, 2014 2:11:46 pm
	 */
	static void testDay() {
		Date start = DateUtils.stringToDate("2015-01-20 00:00:00"); 
		Date now = DateUtils.getEndHourOftheDay(new Date());
		log.info("{},{},{}", start.equals(now),start.before(now),start.after(now));
		while (start.before(now)){
			List<PmWlgDay> pwhs = dayDao.findByDateTime(start);
			if(ListUtils.isEmpty(pwhs) ){
				log.error("results is null or wrong number for time {}", start);
				pmWlgDayService.createPmWlgDayMeters(MeterConstants.METER_IDS_OF_SPMS, start);
			}
			start = DateUtils.getNextDay(start);
		}	
	}
	
	static void testRaw(){ 
		Sort sort = new Sort(Direction.DESC,"dateTime");
		List<PmWlgRaw> ls =  (List<PmWlgRaw>) rawDao.findAll(sort);
		ListUtils.output(ls);
	}
	/**
	 * test all all hours
	 * @function:
	 * @author: zhuyuanbo    11 Nov, 2014 3:29:25 pm
	 */
	static void testAddAll(){ 
//		Date start = DateUtils.stringToDate("2014-11-17 10:05:00");
//		Date now = new Date();
//		while (start.before(now)){
//			pmWlgHourService.createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
//			start = DateUtils.getNextHour(start);
//		}		
	}
	
	static void testSumHour(){
		Date start = DateUtils.stringToDate("2014-11-17 10:00:00");
		Date end = DateUtils.stringToDate("2014-11-17 11:00:00");
		ListUtils.output(dao.findByStartAndEnd(start, end));
	}
	
	
	
}
