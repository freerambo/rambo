/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.rambo.spms.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.Statistics;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.repository.PmWlgHourDao;
import com.rambo.erian.service.PmWlgHourService;
import com.rambo.spms.entity.PmWlg;
import com.rambo.spms.repository.PmWlgDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

// Spring Bean的标识.

@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional(value="spmsEM")
//@TransactionConfiguration(transactionManager="rawEntityManagerFactory")
public class PmWlgService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237731314981193723L;

	private PmWlgDao pmWlgDao;

	
	// recommend use in this way for slf4j 
	private static Logger log = LoggerFactory.getLogger(PmWlgService.class); // 日志打印

	
	public PmWlg getPmWlg(Long id) {
		return pmWlgDao.findOne(id);
	}

	public void savePmWlg(PmWlg entity) {
		pmWlgDao.save(entity);
	}

	public void deletePmWlg(Long id) {
		pmWlgDao.delete(id);
	}

	public List<PmWlg> getAllPmWlg() {
		return (List<PmWlg>) pmWlgDao.findAll();
	}

	public Page<PmWlg> getUserPmWlg(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<PmWlg> spec = buildSpecification(searchParams);
//		checkHourRawData(1,"2014-10-29 12:01:00","2014-10-29 13:00:00");
//		PmWlgHour pmHour =  checkHourRawData(1,DateUtils.stringToDate("2014-10-29 12:00:00"),TimeConstants.TOTAL_OF_MINUTE);
//		log.info(pmHour.toString());
//		pmWlgHourDao.findAll();
//		
//		pmWlgHourDao.save(pmHour);
//		pmWlgHourService.savePmWlgHour(pmHour);
		return pmWlgDao.findAll(spec, pageRequest);
	}
	//test
	public static void main(String[] arg){
		DateTime dt = new DateTime(DateUtils.stringToDate("2014-10-29 12:00:00"));
		System.out.println(dt.toString(DateUtils.DEAFAULT_DATE_FORMAT));
		System.out.println(dt.minusHours(-1).isBeforeNow());

	}
	
	
	/**
	 * generate the hourly data
	 * @param pmNum
	 * @param start exclude
	 * @param nums
	 */
	public PmWlgHour checkHourRawData(final int pmNum, Date start, final int nums){
		if(start == null)
			return null;
		// get the date only with hour
//		start = DateUtils.getDateWithHour(start); 
		DateTime begin = new DateTime(start);
		//  the start date should be  an hour earlier than current time 
		if(!begin.plusHours(1).isAfterNow()){
			
			List<Object[]> pms =  pmWlgDao.getHourlyData(pmNum, start, nums);
			
			if(ListUtils.isNotEmpty(pms)){
//				System.out.println(pms.size());
				Date dtMax=start, dtMin=start,dtTemp = null;
				float max = 0, min = 0,temp = 0, value = 0; 
				DateTime end = null, over = begin.plusHours(1);
				int interval = 1,count = 0;
				boolean flag = false;
				List<Float> values = new ArrayList<Float>();
				Statistics st = null;
				for(Object[] pm : pms){					
					temp = (float) pm[1];
					dtTemp = (Date)pm[0];
					end =  new DateTime(dtTemp); 
					//if the end time not before the end of the hour, set to the end of hour
					if(!end.isBefore(over)){
						end = over;
						flag = true;									
					}				
					count++;					
					Period p = new Period(begin,end,PeriodType.minutes());//the last param is crucial
					interval = p.getMinutes();
					value += interval * temp;
					
					while(interval > 0){
						values.add(temp);
						interval--;
					}
					
					begin = end; // set the begin to end
					//set the max and min value
					if(max < temp ){max = temp; dtMax = dtTemp;}
					if(min > temp || min == 0 ){min = temp; dtMin = dtTemp;}
					//if the time over the end of the hour, finish the loop
					if(flag){
						break;
					}					
				}	
				value /= TimeConstants.TOTAL_OF_MINUTE;
				st = new Statistics(values, value);
				log.info("for meter {} time {} total rows is {}, value is {} max is {} time {}, min is {} time {} stand variance {}", pmNum,over.toString("Y-MM-dd HH:mm:ss"),count,value,max,DateUtils.dateToString(dtMax),min,DateUtils.dateToString(dtMin),st.getStdDev());
			
				PmWlgHour pmHour = new PmWlgHour(over.toDate(),value,st.getStdDev(),max,dtMax,min,dtMin,pmNum);
				return pmHour;
			}else{
				//error
				log.error("no data for meter {} ", pmNum);				
			}
			
		}else{
//			System.out.println("the start date should be one hour earlier than current time");
			log.warn("the start date should be one hour earlier than current time");
		}
		return null;	
	}
	
	
	/**
	 * get all the hourly data for pm wlg
	 * @param meters
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PmWlgHour> checkAllHourRawData(List<Integer> meters, Date start, Date end){
		if(start == null)
			return null;
		// get the date only with hour
		start = DateUtils.getEndMinuteOfHour(start);
		DateTime sd =  new DateTime(start);
		DateTime ed =  null;
		if(end == null || end.after(new Date())){
			ed = new DateTime();
		}else{
			ed = new  DateTime(end);
		}
		List<PmWlgHour> ls = new ArrayList<PmWlgHour>();
		
		while(sd.isBefore(ed)){
			for(Integer pmNum : meters){
				PmWlgHour pmh = this.checkHourRawData(pmNum, sd.toDate(), TimeConstants.TOTAL_OF_MINUTE);
				if(pmh != null)
					ls.add(pmh);
			}
			sd = sd.plusHours(1);
		}
		
		return ls;
	}
	

	/**
	 * create pagenation request
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("NO".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pmNum");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * create the dynamic query 
	 */
	private Specification<PmWlg> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PmWlg> spec = DynamicSpecifications.bySearchFilter(filters.values(), PmWlg.class);
		return spec;
	}

	@Autowired
	public void setPmWlgDao(PmWlgDao pmWlgDao) {
		this.pmWlgDao = pmWlgDao;
	}


	
}
