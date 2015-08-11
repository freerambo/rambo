package com.rambo.erian.service.nms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springside.modules.test.spring.Profiles;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.excel.ExportModel;
import com.rambo.erian.entity.*;
import com.rambo.erian.repository.*;
import com.rambo.erian.service.*;
import com.rambo.infrustructure.entity.NmsMeter;
import com.rambo.infrustructure.repository.NmsMeterDao;
import com.rambo.raw.entity.NMSRecord;
import com.rambo.raw.entity.PmWlgRaw;
import com.rambo.raw.repository.*;

public class NMSDaoTest {
	private static NMSRecordDao rawDao;
	private static NmsMeterDao meterDao;
	private static NmsDayResultDao dayResultDao;
	private static NmsHourDao hourDao;
	private static NmsDayDao dayDao;
	private static NmsWeekResultDao weekDao;
	private static DayExportDao dayExportDao;

	private static HourExportDao hourExportDao;

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NMSDaoTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		rawDao = (NMSRecordDao) context.getBean("nMSRecordDao");

		meterDao = (NmsMeterDao) context.getBean("nmsMeterDao");
		hourDao = (NmsHourDao) context.getBean("nmsHourDao");
		dayDao = (NmsDayDao) context.getBean("nmsDayDao");
		dayResultDao = (NmsDayResultDao) context.getBean("nmsDayResultDao");

		weekDao = (NmsWeekResultDao) context.getBean("nmsWeekResultDao");

		dayExportDao = (DayExportDao) context.getBean("dayExportDao");
		
		hourExportDao = (HourExportDao) context.getBean("hourExportDao");
		// testMeter();
		// testHour();
		// testDao();
//		testWeek();
		
//		testdayExport();
		testhourExport();

	}

	static void testDao() {
		// List<NMSRecord> results = rawDao.findByMeterId("DA3510108");

		ListUtils.output(rawDao.findMeterIdsforTime("2014-10-23 00:00:00"));
		// List<NMSRecord> results =
		// rawDao.findByMeterIdAndStartTime("DA3509930", "2014-10-23 00:00:00");
		// ListUtils.output(results);

	}

	static void testMeter() {
		// List<NmsMeter> nms = (List<NmsMeter>) meterDao.findAll();
		// log.info("results size is {} ", nms.size());
		// NmsMeter m = meterDao.findByMeterId("DA3509757");
		// log.info("{}",m);

		// List<String> ls = meterDao.findByLocation("Nanyang Crescent");
		// ListUtils.output(ls);
		// ListUtils.output(nms);

		// Object value = dayDao.findByMeterIdsAndUnitAndDateTime(
		// Lists.newArrayList("DA3509748","DA3509757"), "KVARH_EXP_INT",
		// "2014-10-25 00:00:00");
		// log.info("{}",value);
		// 2014-11-01 00:00:00,remark=<null>,value=190.31600000000014,location=
		dayResultDao.findByLocation("Nanyang Terrace");
		dayResultDao.findByDateTime("2014-11-01 00:00:00");
		// dayResultDao.findByDateTimeAndLocation("2014-11-01 00:00:00","Nanyang Terrace");
	}

	static void testHour() {
		List<Object[]> results = hourDao.findByStartAndEnd(
				"2014-11-05 00:00:00", "2014-11-05 12:00:00");

		List<ExportModel> ls = ExportModel.convert(results);
		log.info("{}", ls);
	}

	static void testWeek() {

		List<ExportModel> ls = weekDao.findByStartAndEnd("2014-10-01 00:00:00",
				"2014-11-05 12:00:00");

		log.error("{}", ls);
	}

	static void testdayExport() {

		// List<ExportModel> ls =
		// weekDao.findByStartAndEnd("2014-10-01 00:00:00",
		// "2014-11-05 12:00:00");
		//
		// log.error("{}",ls);

		String start = "2013-11-05 00:00:00";
		String end = "2015-03-18 00:00:00";
		String next = null;
		List<DayExport> ls = new ArrayList<DayExport>();
		Date s = DateUtils.stringToDate(start);
		Date now = DateUtils.stringToDate(end);;
		while (s.before(now)) {

			DayExport out = dayExportDao.findOne(start);
			if (out == null) {
				log.error("{} no value",start);
				out = new DayExport(start,-1,-1);
				ls.add(out);
//				break;
//				2014-04-07 00:00:00
//				dayExportDao.save(entity);
			}
			start = DateUtils.dateToString(DateUtils.getNextDay(s));
			
			s = DateUtils.stringToDate(start);
		}
		ListUtils.output(ls);
		dayExportDao.save(ls);
	}

	
	static void testhourExport() {

		// List<ExportModel> ls =
		// weekDao.findByStartAndEnd("2014-10-01 00:00:00",
		// "2014-11-05 12:00:00");
		//
		// log.error("{}",ls);

		String start = "2013-11-05 01:00:00";
		String end = "2015-03-23 06:00:00";
		String next = null;
		List<HourExport> ls = new ArrayList<HourExport>();
		Date s = DateUtils.stringToDate(start);
		Date now = DateUtils.stringToDate(end);;
		while (s.before(now)) {

			HourExport out = hourExportDao.findOne(start);
			if (out == null) {
//				log.error("{} no value",start);
				out = new HourExport(start,-1,-1);
//				log.error("{}",out);
//				break;
				ls.add(out);
//				break;
//				2014-04-07 00:00:00
//				dayExportDao.save(entity);
			}
			start = DateUtils.dateToString(DateUtils.getNextHour(s));
			
			s = DateUtils.stringToDate(start);
		}
		ListUtils.output(ls);
//		hourExportDao.save(ls);
	}
}
