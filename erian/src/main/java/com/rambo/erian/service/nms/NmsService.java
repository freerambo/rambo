package com.rambo.erian.service.nms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.Statistics;
import com.rambo.common.utils.StatisticsDouble;
import com.rambo.common.utils.excel.ExportModel;
import com.rambo.erian.entity.DayExport;
import com.rambo.erian.entity.HourExport;
import com.rambo.erian.entity.NmsDay;
import com.rambo.erian.entity.NmsDayResult;
import com.rambo.erian.entity.NmsHour;
import com.rambo.erian.entity.NmsMonthResult;
import com.rambo.erian.entity.NmsWeekResult;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.repository.DayExportDao;
import com.rambo.erian.repository.HourExportDao;
import com.rambo.erian.repository.NmsDayDao;
import com.rambo.erian.repository.NmsDayResultDao;
import com.rambo.erian.repository.NmsHourDao;
import com.rambo.erian.repository.NmsMonthResultDao;
import com.rambo.erian.repository.NmsWeekResultDao;
import com.rambo.erian.repository.PmWlgMonthDao;
import com.rambo.erian.repository.PmWlgWeekDao;
import com.rambo.erian.service.PmWlgDayService;
import com.rambo.infrustructure.entity.NmsMeter;
import com.rambo.infrustructure.repository.NmsMeterDao;
import com.rambo.raw.entity.NMSRecord;
import com.rambo.raw.repository.NMSRecordDao;

@Service("nmsService")
// all the public function will be transaction associated
@Transactional(value = "defaultEM")
public class NmsService {

	private NMSRecordDao dao;
	private NmsDayDao dayDao;
	private NmsHourDao hourDao;
	private NmsWeekResultDao weekDao;
	private NmsMeterDao meterDao;
	private NmsDayResultDao dayResultDao;

	@Autowired
	private PmWlgWeekDao pmWlgWeekDao;
	@Autowired
	private PmWlgMonthDao pmWlgMonthDao;
	@Autowired
	private NmsMonthResultDao monthDao;

	@Autowired
	DayExportDao dayExportDao;
	
	@Autowired
	HourExportDao hourExportDao;
	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NmsService.class); // 日志打印

	@Autowired
	public void setDao(NMSRecordDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setNmsDayDao(NmsDayDao dayDao) {
		this.dayDao = dayDao;
	}

	@Autowired
	public void setNmsWeekDao(NmsWeekResultDao weekDao) {
		this.weekDao = weekDao;
	}

	@Autowired
	public void setNmsHourDao(NmsHourDao hourDao) {
		this.hourDao = hourDao;
	}

	@Autowired
	public void setNmsDayResultDao(NmsDayResultDao dayResultDao) {
		this.dayResultDao = dayResultDao;
	}

	@Autowired
	public void setMeterDao(NmsMeterDao meterDao) {
		this.meterDao = meterDao;
	}

	public List<NmsDay> findAll() {
		return (List<NmsDay>) dayDao.findAll();
	}

	/**
	 * 
	 * @function: findResultWithStartAndUnit
	 * @param start
	 * @param end
	 * @param unit
	 * @return
	 * @author: zhuyuanbo 22 Dec, 2014 6:48:47 pm
	 */
	public List<NmsDayResult> findResultWithStartAndUnit(String start,
			String end, String unit) {

		return (List<NmsDayResult>) dayResultDao.findByStartAndEndAndUnit(
				start, end, unit);
	}

	/**
	 * 
	 * @function: create NmsDayResult
	 * @author: zhuyuanbo 19 Dec, 2014 8:54:41 pm
	 */
	public void createNmsDayResult(NmsDayResult result) {
		if (result != null) {
			log.info("in createNmsDayResult result {}", result);
			NmsDayResult temp = dayResultDao.findByDateTimeAndLocationAndUnit(
					result.getDateTime(), result.getLocation(),
					result.getUnit());
			if (temp == null) {
				dayResultDao.save(result);
			} else {
				log.info("in createNmsDayResult temp {}", temp);
				temp.setValue(result.getValue());

			}
		}
	}

	/**
	 * 
	 * @function: find Data By Location
	 * @param location
	 * @author: zhuyuanbo 18 Dec, 2014 3:55:10 pm
	 */
	public NmsDayResult generateDataByLocation(String location, String date,
			String unit) {

		List<String> meters = meterDao.findByLocation(location);
		if (ListUtils.isNotEmpty(meters)) {

			Double value = findDateByMetersAndTimeAndUnit(meters, date, unit);

			System.out.println(location + date + unit + value);

			if (value != null)
				return new NmsDayResult(date, value, location, unit);
		}
		return null;
	}

	/**
	 * 
	 * @function: create NmsDay Result ByLocation
	 * @param location
	 * @param date
	 * @param unit
	 * @author: zhuyuanbo 20 Jan, 2015 4:06:22 pm
	 */
	public void createNmsDayResultByLocation(String location, String date,
			String unit) {

		NmsDayResult result = this.generateDataByLocation(location, date, unit);
		log.info("in createNmsDayResultByLocation {}", result);
		if (result != null) {
			createNmsDayResult(result);
		}
	}

	public void generateDataByLocations(List<String> locations, String date,
			String unit) {

		for (String location : locations) {
			this.generateDataByLocation(location, date, unit);
		}
	}

	public void createResultByLocations(List<String> locations, String date,
			String unit) {

		for (String location : locations) {
			this.createNmsDayResultByLocation(location, date, unit);
		}
	}

	public void createResultForAll(List<String> locations, String start,
			String end, List<String> units) {

		for (Date s = DateUtils.stringToDate(start); !s.after(DateUtils
				.stringToDate(end)); s = DateUtils.getNextDay(s)) {
			log.info("{}", s);
			for (String unit : units) {
				log.info("{}", unit);
				createResultByLocations(locations, DateUtils.dateToString(s),
						unit);

			}
		}
	}

	public Double findDateByMetersAndTimeAndUnit(List<String> meters,
			String date, String unit) {
		return dayDao.findByMeterIdsAndUnitAndDateTime(meters, unit, date);
	}

	public void createAllRecords(String start, String end, List<String> units) {

		log.warn("in createAll records {}, {}, {}", start, end, units);

		List<String> ms = dao.findMeterIdsforTimeAndUnits(start, units);
		log.info("meters' size is {}", ms.size());
		Date tEnd = DateUtils.stringToDate(end);
		for (String mId : ms) {
			Date tStart = DateUtils.stringToDate(start);

			while (!tStart.after(tEnd)) {
				for (String unit : units) {
					createDailyForMeter(mId, DateUtils.dateToString(tStart),
							unit);
				}
				tStart = DateUtils.getNextDay(tStart);
			}
		}

	}

	/**
	 * 
	 * @function: create the daily record for each meter with specific Unit
	 * @param meterId
	 * @param startTime
	 * @author: zhuyuanbo 11 Dec, 2014 4:01:58 pm
	 */
	public void createDailyForMeter(String meterId, String startTime,
			String unit) {
		NmsDay result = null;
		/*
		 * dayDao.findByMeterIdAndUnitAndDateTime(meterId, unit, startTime);
		 * Long id = null; if (result != null) { log.info("exists {} ", result);
		 * id = result.getId(); }
		 */
		List<NMSRecord> results = dao.findByMeterIdAndStartTimeAndUnit(meterId,
				startTime, unit);
		// ListUtils.output(results);
		/**
		 * check the result is not null and the size must be 48
		 * */
		if (ListUtils.isNotEmpty(results)
				&& results.size() == TimeConstants.TOTAL_HALF_HOUR_OF_DAY) {
			List<NmsHour> nmshours = Lists.newArrayList();
			result = calcHourlyAndDailyRecord(meterId, startTime, unit,
					results, nmshours);

			// save all hourly data
			saveHourTranData(nmshours);

			// result.setId(id);
			dayDao.save(result);
		} else {
			log.warn(
					"the results is null or wrong number in createDailyForMeter {},{},{}",
					meterId, startTime, unit);
		}
	}

	public void saveHourTranData(List<NmsHour> nmshours) {
		/*
		 * for(NmsHour nh : nmshours){ NmsHour temp =
		 * hourDao.findByMeterIdAndUnitAndDateTime(nh.getMeterId(),
		 * nh.getUnit(), nh.getDateTime()); if(temp != null){
		 * nh.setId(temp.getId()); } }
		 */
		hourDao.save(nmshours);
	}

	/**
	 * 
	 * @function: calcalate the daily record from all records with specific
	 *            meter
	 * @param results
	 * @author: zhuyuanbo 11 Dec, 2014 4:03:15 pm
	 */
	public NmsDay calcHourlyAndDailyRecord(String meterId, String startTime,
			String unit, List<NMSRecord> results, List<NmsHour> nmshours) {

		double max = 0, min = 0, temp = 0, value = 0;
		String dtMax = null, dtMin = null, dtTemp = null;
		int count = 0;
		List<Double> values = new ArrayList<Double>();
		log.info("in calcDailyRecord results size {}", results.size());
		for (int i = 0; i < results.size(); i++) {
			NMSRecord pmh = results.get(i);
			// hourly data
			if (i % 2 == 1) {
				NmsHour hour = new NmsHour();
				hour.setValue(temp + pmh.getValue());
				String date = DateUtils.getByStringAddTime(pmh.getStartTime(),
						pmh.getInterval() * pmh.getNo() * 1000);
				String predate = DateUtils.getByStringAddTime(
						pmh.getStartTime(), pmh.getInterval()
								* (pmh.getNo() - 1) * 1000);
				hour.setDateTime(date);
				hour.setMeterId(meterId);
				hour.setUnit(unit);
				if (pmh.getValue() > temp) {
					hour.setMax(pmh.getValue());
					hour.setDtMax(date);
					hour.setMin(temp);
					hour.setDtMin(predate);
				} else {
					hour.setMax(temp);
					hour.setDtMax(predate);
					hour.setMin(pmh.getValue());
					hour.setDtMin(date);
				}
				StatisticsDouble st = new StatisticsDouble(Lists.newArrayList(
						temp, pmh.getValue()), (temp + pmh.getValue()) / 2.0);
				hour.setStd(st.getStdDev());

				log.info("{}", hour);
				// add the hour data to daily list
				nmshours.add(hour);
			}

			temp = pmh.getValue();
			dtTemp = pmh.getStartTime();
			value += temp;
			values.add(temp);
			// the first time
			if (count == 0) {
				dtMin = dtMax = dtTemp;
				max = min = temp;

			} else {
				// set the max and min value
				if (max < temp) {
					max = temp;
					dtMax = DateUtils.getByStringAddTime(pmh.getStartTime(),
							pmh.getInterval() * pmh.getNo() * 1000);
				}
				if (min > temp) {
					min = temp;
					dtMin = DateUtils.getByStringAddTime(pmh.getStartTime(),
							pmh.getInterval() * pmh.getNo() * 1000);
				}
			}
			count++;
		}
		StatisticsDouble st = new StatisticsDouble(values, value
				/ results.size());

		NmsDay nmsDay = new NmsDay(startTime, value, unit, st.getStdDev(), max,
				dtMax, min, dtMin, meterId);
		log.info("in calcDailyRecord results : {}", nmsDay.toString());
		return nmsDay;
	}

	/**
	 * 
	 * @function: get Hour Export nanyang, nieo, spms
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo 14 Jan, 2015 10:54:54 am
	 */
	public List<ExportModel> getHourExport(String start, String end) {
		return ExportModel.convert(hourDao.findByStartAndEnd(start, end));
	}

	/**
	 * 
	 * @function: get daily Export nanyang, nieo, spms
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo 14 Jan, 2015 10:54:54 am
	 */
	public List<ExportModel> getDayExport(String start, String end) {
		return ExportModel.convert(dayDao.findByStartAndEnd(start, end));
	}

	/**
	 * 
	 * @function: create Week Data for NMS
	 * @param start
	 *            yyyy-MM-dd HH:mm:ss
	 * @param end
	 * @return
	 * @author: zhuyuanbo 16 Jan, 2015 3:42:00 pm
	 */
	public void createWeekData(String start, String end) {
		Date s = DateUtils.stringToDate(start);
		Date e = DateUtils.stringToDate(end);
		s = DateUtils.getFirstDayOfWeek(s);
		e = DateUtils.getLastDayOfWeek(e);

		while (s.before(e)) {
			Date dEnd = DateUtils.getLastDayOfWeek(s);
			String sdEnd = DateUtils.dateToString(dEnd);
			Object[] nms = dayDao.findOneDateByStartAndEnd(
					DateUtils.dateToString(s), sdEnd);
			Object obj = pmWlgWeekDao.findByDateTime(dEnd);

			Double nanyang = 0.0;
			Double nieo = 0.0;
			Double spms = 0.0;

			if (obj != null) {
				spms = (Double) obj;
			}

			if (nms != null) {
				nanyang = (nms[0] == null ? 0.0 : (Double) nms[0]);
				nieo = (nms[1] == null ? 0.0 : (Double) nms[1]);

			}

			log.warn("date {},nanyang {} nieo {} spms {}", sdEnd, nanyang,
					nieo, spms);

			NmsWeekResult result = new NmsWeekResult(sdEnd, nanyang, nieo, spms);
			weekDao.save(result);

			s = DateUtils.getNextWeek(s);

		}

	}

	/**
	 * 
	 * @function: get weekly Export nanyang, nieo, spms
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo 16 Jan, 2015 3:42:00 pm
	 */

	public List<ExportModel> getWeekExport(String start, String end) {
		return weekDao.findByStartAndEnd(start, end);
	}

	/**
	 * 
	 * @function: get Monthly Export nanyang, nieo, spms
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo 16 Jan, 2015 3:42:00 pm
	 */
	public List<ExportModel> getMonthExport(String start, String end) {
		return monthDao.findByStartAndEnd(start, end);
	}

	/**
	 * 
	 * @function: create Month Data for NMS
	 * @param start
	 *            yyyy-MM-dd HH:mm:ss
	 * @param end
	 * @return
	 * @author: zhuyuanbo 16 Jan, 2015 3:42:00 pm
	 */
	public void createMonthData(String start, String end) {
		Date s = DateUtils.stringToDate(start);
		Date e = DateUtils.stringToDate(end);
		s = DateUtils.getFirstDayOfMonth(s);
		e = DateUtils.getLastDayOfMonth(e);

		while (s.before(e)) {
			Date dEnd = DateUtils.getLastDayOfMonth(s);
			String sdEnd = DateUtils.dateToString(dEnd);
			Object[] nms = dayDao.findOneDateByStartAndEnd(
					DateUtils.dateToString(s), sdEnd);
			Object obj = pmWlgMonthDao.findByDateTime(dEnd);

			Double nanyang = 0.0;
			Double nieo = 0.0;
			Double spms = 0.0;

			if (obj != null) {
				spms = (Double) obj;
			}

			if (nms != null) {
				nanyang = (nms[0] == null ? 0.0 : (Double) nms[0]);
				nieo = (nms[1] == null ? 0.0 : (Double) nms[1]);
			}

			log.warn("date {},nanyang {} nieo {} spms {}", sdEnd, nanyang,
					nieo, spms);

			NmsMonthResult result = new NmsMonthResult(sdEnd, nanyang, nieo,
					spms);
			monthDao.save(result);
			s = DateUtils.getNextMonth(s);
		}

	}
	
	/**
	 * get all the day results used for nms daily plot
	 * @return
	 */
	public List<DayExport> getAllDayResults(){
		return (List<DayExport>) dayExportDao.findAll();
	}
	
	/**
	 * get all the day results used for nms daily plot
	 * @return
	 */
	public List<HourExport> getAllHourResults(){
		return (List<HourExport>) hourExportDao.findAll();
	}

}
