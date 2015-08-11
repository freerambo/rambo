package com.rambo.erian.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.Statistics;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgWeek;
import com.rambo.erian.repository.PmWlgWeekDao;
import com.rambo.erian.service.PmWlgDayService;

/**
 * 
 * function description：  management PmWlgWeek
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 
 * Create:  6 Nov, 2014 4:52:52 pm
 */
@Service
//all the public function will be transaction associated
@Transactional(value = "defaultEM")
public class PmWlgWeekService {

	private PmWlgWeekDao dao;
	
	private PmWlgDayService pmWlgDayService;

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(PmWlgWeekService.class); // 日志打印

	
	@Autowired
	public void setDao(PmWlgWeekDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setPmWlgDayService(PmWlgDayService pmWlgDayService) {
		this.pmWlgDayService = pmWlgDayService;
	}
	
	
	/**
	 * 
	 * @function: In PmWlgWeekService getFromDayByPmIdWithStartAndEnd
	 * @param pmId
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    9 Nov, 2014 10:38:39 pm
	 */
	public PmWlgWeek getFromDayByPmIdWithStartAndEnd(final int pmId, Date start, Date end){

		List<PmWlgDay> pwds = pmWlgDayService.getByPmIdWithStartAndEnd(pmId, start, end);
		
		return caculatePmWlgWeekFromDay(pwds, end);
	}
	
	public PmWlgWeek getFromDayByPmIdWithDate(final int pmId, Date date){
		if(date == null || date.after(new Date())){
			log.warn("the date cannot be null");
			return null;
		}
		
		date = DateUtils.getEndHourOftheDay(date);
		Date start = DateUtils.getFirstDayOfWeek(date);
		Date end = DateUtils.getLastDayOfWeek(date);
		
		if(end.after(new Date())){
			log.warn("the end date of the week has not yet come");
			return null;
		}
		return this.getFromDayByPmIdWithStartAndEnd(pmId, start, end);
	}
	
	/**
	 * 
	 * @function:  caculatePmWlgWeekFromDay
	 * @param pwds 
	 * @return
	 * @author: zhuyuanbo    9 Nov, 2014 10:38:59 pm
	 */
	public PmWlgWeek  caculatePmWlgWeekFromDay(List<PmWlgDay> pwds, Date date){
		if  (ListUtils.isEmpty(pwds))
			return null;
		
		float max = 0, min = 0,temp = 0, value = 0;
		Date dtMax=null, dtMin=null,dtTemp = null;
		int count = 0, pmNum = -1;
		List<Float> values = new ArrayList<Float>();
		log.info("in caculatePmWlgWeekFromDay hours size {}",pwds.size());
		for(PmWlgDay pmd: pwds){
			
			temp = pmd.getValue();
			dtTemp = pmd.getDateTime();
			value += temp;
			values.add(temp);
			//the first time 
			if(count == 0){
				dtMin = dtMax = dtTemp;
				max = min = temp;
				pmNum = pmd.getPmId();
				log.info("in caculatePmWlgWeekFromDay pmID {}",pmNum);
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
	
		PmWlgWeek pmWeek = new PmWlgWeek(date,value,st.getStdDev(),max,dtMax,min,dtMin,pmNum);
		
		return pmWeek;
				
	}
	
	/**
	 * 
	 * @function: get Values By Start And End for charts
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    24 Nov, 2014 4:57:14 pm
	 */
	public List<Object[]> getValuesByStartAndEnd(Date start,Date end){
		
		return dao.findByStartAndEnd(start, end);
		
	}
	

	/**
	 * 
	 * @function: In PmWlgWeekService getFromDayByPmIdsWithStartAndEnd
	 * @param pmId
	 * @param start yyyy-mm-dd 00:00:00
	 * @param end  yyyy-mm-dd 00:00:00
	 * @return
	 * @author: zhuyuanbo    9 Nov, 2014 10:38:39 pm
	 */
	public List<PmWlgWeek> getFromDayByPmIdsWithStartAndEnd(final List<Integer> pmIds, Date start, Date end){


		
		List<PmWlgWeek> pwws = new ArrayList<PmWlgWeek>();
		for(Integer pmId : pmIds){
			PmWlgWeek temp =  this.getFromDayByPmIdWithStartAndEnd(pmId, start, end);
			if(temp != null)
				pwws.add(temp);			
		}
		return pwws;		
	}
	
	
	
	
	
	/**
	 * 
	 * @function: createPmWlgWeekByPmIdWithDate
	 * @param pmIds
	 * @param start
	 * @param end
	 * @author: zhuyuanbo    9 Nov, 2014 11:04:26 pm
	 */
	public void createPmWlgWeekByPmIdWithDate(final Integer pmId, Date date){
		

		PmWlgWeek pww = this.getFromDayByPmIdWithDate(pmId, date);
		if(pww != null)
			this.dao.save(pww);
			
	}
	
	
	/**
	 * 
	 * @function: In PmWlgWeekService getFromDayByPmIdsWithDate
	 * @param pmId
	 * @param date yyyy-mm-dd 00:00:00
	 * @return
	 * @author: zhuyuanbo    9 Nov, 2014 10:38:39 pm
	 */
	public List<PmWlgWeek> getFromDayByPmIdsWithDate(final List<Integer> pmIds, Date date){

		if(date == null || date.after(new Date())){
			log.warn("the date cannot be null");
			return null;
		}
		
		date = DateUtils.getEndHourOftheDay(date);

		Date start = DateUtils.getFirstDayOfWeek(date);
		Date end = DateUtils.getLastDayOfWeek(date);
		log.warn("{},{},{}",date,start,end);
		if(end.after(new Date())){
			log.warn("the end date of the week has not yet come");
			return null;
		}
		//precess the start and end time
		return this.getFromDayByPmIdsWithStartAndEnd(pmIds, start, end);
		
	}
	

	
	/**
	 * 
	 * @function: createPmWlgWeeksWithDate
	 * @param pmIds
	 * @param date
	 * @author: zhuyuanbo    10 Nov, 2014 4:12:41 pm
	 */
	public void createPmWlgWeeksByPmIdsWithDate(final List<Integer> pmIds, Date date){
		
		List<PmWlgWeek> pwws = this.getFromDayByPmIdsWithDate(pmIds, date);
		if(ListUtils.isNotEmpty(pwws)){
			this.dao.save(pwws);
		}
	}
	
	
	
	/**
	 * 
	 * @function: createPmWlgWeeks
	 * @param pwms
	 * @author: zhuyuanbo    9 Nov, 2014 11:02:49 pm
	 */
	public void createPmWlgWeeks(List<PmWlgWeek> pwws){
		this.dao.save(pwws);
	}
	
	/**
	 * 
	 * @function: createPmWlgWeek
	 * @param pwms
	 * @author: zhuyuanbo    9 Nov, 2014 11:02:49 pm
	 */
	public void createPmWlgWeek(PmWlgWeek pww){
		this.dao.save(pww);
	}

	
	
	/**
	 * 
	 * @function: find all
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 3:56:44 pm
	 */
	public List<PmWlgWeek> getAll(){
		return (List<PmWlgWeek>) dao.findAll();
	}
	
	

	public Page<PmWlgWeek> getUserPmWlgWeek(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<PmWlgWeek> spec = buildSpecification(searchParams);

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
	private Specification<PmWlgWeek> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PmWlgWeek> spec = DynamicSpecifications.bySearchFilter(filters.values(), PmWlgWeek.class);
		return spec;
	}
	
}
