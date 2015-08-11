/**
 * rambo
 */
package com.rambo.erian.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.Statistics;
import com.rambo.erian.model.Result;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.repository.PmWlgHourDao;
import com.rambo.raw.entity.PmWlgRaw;
import com.rambo.raw.service.PmWlgRawService;
import com.rambo.spms.service.PmWlgService;

/**
 * 
 * class description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 5 Nov, 2014 10:32:14 am
 */

// the identification for Spring Bean
@Service
// all the public function will be transaction associated
@Transactional(value = "defaultEM")
public class PmWlgHourService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237731314981193723L;

	private PmWlgHourDao dao;

	private PmWlgRawService pmWlgService;

	private PmWlgService spmsService;

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(PmWlgHourService.class); // 日志打印

	public PmWlgHour getPmWlgHour(Long id) {
		return dao.findOne(id);
	}

	public void savePmWlgHour(PmWlgHour entity) {
		dao.save(entity);
	}

	/**
	 * create new record for pmwlg Hourly data
	 * 
	 * @param pmNum
	 * @param start
	 * @param nums
	 * @return
	 */
	public Long createPmWlgHour(final int pmNum, Date start, final int nums) {
		PmWlgHour pmh = this.pmWlgService.checkHourRawData(pmNum, start, nums);
		log.info("befor insert : " + pmh.toString());
		this.dao.save(pmh);
		Long id = pmh.getId();
		if (id != null && id > 0)
			return id;
		return -1l;
	}

	/**
	 * create all hourly data
	 * 
	 * @param meters
	 * @param start
	 */
	public void createAllPmWlgHour(List<Integer> meters, Date start, Date end) {
		List<PmWlgHour> pmhs = this.pmWlgService.checkAllHourRawData(meters,
				start, end);
		if (ListUtils.isNotEmpty(pmhs) || pmhs.size() != meters.size()) {
			log.info("in createAllPmWlgHour : " + pmhs.size());
			this.dao.save(pmhs);
		}else{
			log.error("reslts is null or wrong number");
		}
	}
	
	/**
	 * 
	 * @function:checkAndCompleteHourlyDateForDay
	 * @param meters
	 * @param day  yyyy-mm-dd 00:00:00
	 * @author: zhuyuanbo    19 Nov, 2014 3:46:16 pm
	 */
	public void checkAndCompleteHourlyDateForDay(List<Integer> meters, Date day) {
		Date start = DateUtils.getStartHourOftheDay(day); 
		Date end = DateUtils.getNextDay(day);
		while (!start.after(end)){
			log.info(start.toString());
			List<PmWlgHour> pwhs = dao.findByDateTime(start);
			if(ListUtils.isEmpty(pwhs)){
				log.warn("hour results is null  for time {}", start);
				createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
			}
			start = DateUtils.getNextHour(start);
		}	
	}
	
	/**
	 */
	
	/**
	 * create all hourly data for meters With Date
	 * 
	 * @param meters
	 * @param date 
	 */
	public void createAllPmWlgHourWithDate(List<Integer> meters, Date date) {
		
		if(date == null || !date.before(new Date())){
			log.error("improper date in createAllPmWlgHourWithDate");
			return;
		}
		//get previous hour of given date
//		date = DateUtils.getPreviousHour(date);
		Date start = DateUtils.getEndMinuteOfHour(DateUtils.getPreviousHour(date));
		Date end = DateUtils.getNextHour(start);
		log.info("give date {}, start time {} in createAllPmWlgHour : ", date, start);
		
		List<PmWlgHour> pmhs = this.pmWlgService.getHourDatasByPmIdsWithStartAndEnd(meters, start,end);

		if (ListUtils.isNotEmpty(pmhs)) {
			log.info("in createAllPmWlgHour : " + pmhs.size());
			this.dao.save(pmhs);
		}else{
			log.error("results is null or wrong number");
			
		}
	}

	/**
	 * 
	 * @function: From SPMS create new record for pmwlg Hourly data
	 * @param pmNum
	 * @param start
	 * @param nums
	 * @return
	 * @author: zhuyuanbo 5 Nov, 2014 9:43:48 am
	 */
	
	public Long createPmWlgHourFromSPMS(final int pmNum, Date start,
			final int nums) {
		PmWlgHour pmh = this.spmsService.checkHourRawData(pmNum, start, nums);
		log.info("in createPmWlgHourFromSPMS befor insert : " + pmh.toString());
		this.dao.save(pmh);
		Long id = pmh.getId();
		if (id != null && id > 0)
			return id;
		return -1l;
	}

	/**
	 * From SPMS create all hourly data
	 * 
	 * @param meters
	 * @param start
	 */
	public void createAllPmWlgHourFromSPMS(List<Integer> meters, Date start,
			Date end) {
		List<PmWlgHour> pmhs = this.spmsService.checkAllHourRawData(meters,
				start, end);
		if (ListUtils.isNotEmpty(pmhs)) {
			log.info("in createAllPmWlgHourFromSPMS : " + pmhs.size());
			this.dao.save(pmhs);
		}
	}

	/**
	 * 
	 * @function: check Dayly Data From Hours with pmnums
	 * @param pmNum
	 * @param date
	 * @return
	 * @author: zhuyuanbo 5 Nov, 2014 3:33:12 pm
	 */
	public List<PmWlgHour> checkDayDataFromHours(final List<Integer> pmNums,
			Date date) {

		if (date == null)
			return null;
		Date start = DateUtils.getStartHourOftheDay(date);

		Date end = DateUtils.getEndHourOftheDay(DateUtils.getNextDay(date));
		log.info("start {} end {} meter id {}", start, end, pmNums);

		List<PmWlgHour> pmhs = (List<PmWlgHour>) dao.findByPmNums(pmNums,
				start, end);
		log.info("result size is {}", pmhs.size());
		
//		ListUtils.output(pmhs);
		
		return pmhs;
	}

	/**
	 * 
	 * @function: calculate the PmWlgDays for meters
	 * @param pmNums
	 * @param date
	 * @return
	 * @author: zhuyuanbo    5 Nov, 2014 3:54:07 pm
	 */
	public List<PmWlgDay> getPmWlgDayMeters(final List<Integer> pmNums, Date date) {

		List<PmWlgHour> pmhs = this.checkDayDataFromHours(pmNums, date);
		List<PmWlgDay> pmds = new ArrayList<PmWlgDay>();
		if(ListUtils.isNotEmpty(pmhs) && (pmhs.size() % TimeConstants.TOTAL_HOURS_OF_DAY == 0)){
			int count = 1;
			
			List<PmWlgHour> tpwhs = new  ArrayList<PmWlgHour>();
			for(PmWlgHour pmh: pmhs){
				
				tpwhs.add(pmh);
				
				// calculate the 24 hours datas for the day
				if(count % TimeConstants.TOTAL_HOURS_OF_DAY == 0){

					
					PmWlgDay pwd =  getDayDataFromHours(tpwhs,date);
					if(pwd != null)
						pmds.add(pwd);
					// clear
					tpwhs.clear();
				}	
				count++;
			}
			if(pmNums.size() != pmds.size()){
				log.error("the pmNums {} is not equal to the values' size {}", pmNums.size(), pmds.size());
			}else{
				return pmds;
			}
			
		}else{
			log.error("the pmwlghours  was null or the number {} is not proper",pmhs.size());
		}		
		return null;

	}
	
	/**
	 * 
	 * @function: generate the day value from hours
	 * @param tpwhs
	 * @return
	 * @author: zhuyuanbo    5 Nov, 2014 9:56:41 pm
	 */
	private PmWlgDay getDayDataFromHours(List<PmWlgHour> tpwhs, Date date){
		float max = 0, min = 0,temp = 0, value = 0;
		Date dtMax=null, dtMin=null,dtTemp = null;
		int count = 0, pmNum = -1;
		List<Float> values = new ArrayList<Float>();
		log.info("in getDayDataFromHours hours size {}",tpwhs.size());
		for(PmWlgHour pmh: tpwhs){
			
			temp = pmh.getValue();
			dtTemp = pmh.getDateTime();
			value += temp;
			values.add(temp);
			//the first time 
			if(count == 0){
				dtMin = dtMax = dtTemp;
				max = min = temp;
				pmNum = pmh.getPmId();
				log.info("in getDayDataFromHours pmID {}",pmNum);
//				log.warn(pmh.toString());
//				date = DateUtils.getDateWithFormat(dtTemp, DateUtils.END_DATE_FORMAT_OF_DAY);
//				log.warn(date.toString());
			}else{
				//set the max and min value
				if(max < temp){max = temp; dtMax = dtTemp;}
				if(min > temp){min = temp; dtMin = dtTemp;}
			}
			count++;			
		}
//		value /= TimeConstants.TOTAL_HOURS_OF_DAY;
		Statistics st = new Statistics(values, value/TimeConstants.TOTAL_HOURS_OF_DAY);
		log.info("for meter {} time {} total rows is {}, value is {} max is {} time {}, min is {} time {} stand variance {}", pmNum,date,count,value,max,DateUtils.dateToString(dtMax),min,DateUtils.dateToString(dtMin),st.getStdDev());
	
		PmWlgDay pmDay = new PmWlgDay(date,value,st.getStdDev(),max,dtMax,min,dtMin,pmNum);
		
		

		return pmDay;
	}
	
	

	/**
	 * 
	 * @function: check Dayly Data From Hours with certain pmnum
	 * @param pmNum
	 * @param date
	 * @return
	 * @author: zhuyuanbo 5 Nov, 2014 3:33:12 pm
	 */
	public List<PmWlgHour> checkDayDataFromHoursWithPmNum(int pmNum, Date date) {


		Date start = DateUtils.getStartHourOftheDay(date);

		Date end = DateUtils.getEndHourOftheDay(DateUtils.getNextDay(date));

		log.info("start {} end {} meter id {}", start, end, pmNum);

		
		
		List<PmWlgHour> pmhs = (List<PmWlgHour>) dao.findByPmNum(pmNum, start,
				end);

		return pmhs;
	}

	public void deletePmWlgHour(Long id) {
		dao.delete(id);
	}

	public List<PmWlgHour> getAllPmWlgHour() {
		return (List<PmWlgHour>) dao.findAll();
	}

	
	/**
	 * hourly job check the hourly data and completed the missing 
	 * @function:
	 * @author: zhuyuanbo    21 Nov, 2014 4:07:22 pm
	 */
	public void hourlyJob(){
		Date now = new Date();
		Date start =  DateUtils.getStartHourOftheDay(DateUtils.getPreviousWeek(now)); 
		log.info("in hourlyJob {}", now);
		while (!start.after(now)){
			log.info(start.toString());
			List<PmWlgHour> pwhs = dao.findByDateTime(start);
			if(ListUtils.isEmpty(pwhs)){
				log.error("results is null or wrong number for time {}", start);
				createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, start);
			}
			start = DateUtils.getNextHour(start);
		}	
	}

	/**
	 * 
	 * @function: get Values By Start And End for charts
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    21 Nov, 2014 6:03:38 pm
	 */
	public List<Object[]> getValuesByStartAndEnd(Date start,Date end){
		
		return dao.findByStartAndEnd(start, end);
		
	}
	
	
	/**
	 * 
	 * @function: get Values By Start And End for charts
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    21 Nov, 2014 6:03:38 pm
	 */
	public List<Result> getResultsByStartAndEnd(Date start,Date end){
		
//		pmWlg.date_time,pmWlg.value,pmWlg.school,pmWlg.build
		List<Object[]> objs = dao.findByStartAndEnd(start, end);
		List<Result> results = Lists.newArrayList();
		for(Object[] obj: objs){
			double value = (double)obj[1];
			String school = (String)obj[2];
			String build = (String)obj[3];
			Date dateTime = (Date)obj[0];
			Result result = new Result(value,school,build,dateTime);
			results.add(result);
		}
		
		return results;
		
	}
	
	@Autowired
	public void setPmWlgHourDao(PmWlgHourDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setPmWlgRawService(PmWlgRawService pmWlgService) {
		this.pmWlgService = pmWlgService;
	}

	@Autowired
	public void setPmWlgService(PmWlgService spmsService) {
		this.spmsService = spmsService;
	}


	public Page<PmWlgHour> getUserPmWlgHour(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<PmWlgHour> spec = buildSpecification(searchParams);

		return dao.findAll(spec, pageRequest);
	}

	/**
	 * create pagenation request
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "dateTime","pmId");
		} else if ("pmId".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pmId");
			sort = sort.and(new Sort(Direction.DESC, "dateTime"));
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * create the dynamic query 
	 */
	private Specification<PmWlgHour> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PmWlgHour> spec = DynamicSpecifications.bySearchFilter(filters.values(), PmWlgHour.class);
		return spec;
	}

}
