/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.rambo.raw.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.rambo.raw.entity.PmWlgRaw;
import com.rambo.raw.repository.PmWlgRawDao;












import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional(value="rawEM")
public class PmWlgRawService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237731314981193723L;

	private PmWlgRawDao pmWlgDao;

	
	// recommend use in this way for slf4j 
	private static Logger log = LoggerFactory.getLogger(PmWlgRawService.class); // 日志打印

	
	public PmWlgRaw getPmWlg(Long id) {
		return pmWlgDao.findOne(id);
	}

	public void savePmWlg(PmWlgRaw entity) {
		pmWlgDao.save(entity);
	}

	public void deletePmWlg(Long id) {
		pmWlgDao.delete(id);
	}
	
	public List<PmWlgRaw> getAllPmWlg() {
		return (List<PmWlgRaw>) pmWlgDao.findAll();
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
	 * @param start
	 * @param nums
	 * @throws InterruptedException 
	 */
	public PmWlgHour checkHourRawData(final int pmNum, Date start, final int nums){
		DateTime begin = new DateTime(start);
		//  the start date should be an hour earlier than current time 
		if(begin.plusHours(1).isBeforeNow()){
			
			List<Object[]> pms =  pmWlgDao.getHourlyData(pmNum, start, nums);
			if(ListUtils.isNotEmpty(pms)){			
				int size = pms.size();
				//check the end date is not before the the boundary
				if(size < 60){
					Object[] tpm = (Object[])(pms.get(pms.size() -1));
					Date endDate = (Date)tpm[0];
					//error
					log.error("the size of records is {} end date is {}, start is  {} ",size,endDate,start);	
//					log.error("the end date {} of record is not before the the boundary {}",endDate,DateUtils.getNextHour(start));	
					try {
						Thread.sleep(61000*(nums - size));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return checkHourRawData(pmNum, start, nums);
				}
				
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
				log.info("for meter {} time {} total rows is {}, value is {} max is {} time {}, min is {} time {} stand variance {}", pmNum,over,count,value,max,DateUtils.dateToString(dtMax),min,DateUtils.dateToString(dtMin),st.getStdDev());
			
				PmWlgHour pmHour = new PmWlgHour(over.toDate(),value,st.getStdDev(),max,dtMax,min,dtMin,pmNum);
				return pmHour;
			}else{				
				//error
				log.error("at {} no data for meter {} of {} ", new Date(),pmNum, start);
			}
			
		}else{
			log.warn("the start date should be one hour earlier than current time");
		}
		return null;	
	}
	
	
	/**
	 * 
	 * @function: checkHourRawDataWithstartAndEnd
	 * @param pmNum
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    17 Nov, 2014 6:59:27 pm
	 */
	public PmWlgHour checkHourRawDataWithstartAndEnd(final int pmNum, Date start, Date end){
		DateTime begin = new DateTime(start);
		//  the start date should be an hour earlier than current time 
		if(begin.plusHours(1).isBeforeNow()){
			
			List<Object[]> pms =  pmWlgDao.findByPmNum(pmNum, start, end);
			

			if(ListUtils.isNotEmpty(pms)){
				
				Date dtMax=null, dtMin=null,dtTemp = null;
				float max = 0, min = 0,temp = 0, value = 0; 
				int count = 0;
				boolean flag = false;
				List<Float> values = new ArrayList<Float>();
				Statistics st = null;
				for(Object[] pm : pms){					
					temp = (float) pm[1];
					dtTemp = (Date)pm[0];
					
					if(count == 0){
						max  = min = temp;
						dtMax = dtMin = dtTemp;
					}
					count++;
					//set the max and min value
					if(max < temp ){max = temp; dtMax = dtTemp;}
					if(min > temp ){min = temp; dtMin = dtTemp;}
					
					
					value += temp;
					values.add(temp);
				
				}	
				value /= pms.size();
				st = new Statistics(values, value);
				log.info("for meter {} time {} total rows is {}, value is {} max is {} time {}, min is {} time {} stand variance {}", pmNum,end,count,value,max,DateUtils.dateToString(dtMax),min,DateUtils.dateToString(dtMin),st.getStdDev());
			
				PmWlgHour pmHour = new PmWlgHour(end,value,st.getStdDev(),max,dtMax,min,dtMin,pmNum);
				return pmHour;
			}else{				
				//error
				log.warn("at {} no data for meter {} ",  end,pmNum);
				return new PmWlgHour(end,0,0,0,start,0,start,pmNum);
			}
			
		}else{
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
	 * 
	 * @function: used for hourly update
	 * @param meters
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 8:50:55 pm
	 */
	public List<PmWlgHour> getHourDatasByPmIdsWithStartAndEnd(List<Integer> meters, Date start, Date end){
		
		List<PmWlgHour> ls = new ArrayList<PmWlgHour>();

		for(Integer pmNum : meters){
//			PmWlgHour pmh = this.checkHourRawData(pmNum, start, TimeConstants.TOTAL_OF_MINUTE);
			PmWlgHour pmh = this.checkHourRawDataWithstartAndEnd(pmNum, start, end);
			if(pmh != null)
				ls.add(pmh);
		}
		
		return ls;
	}
	
	
	@Transactional
	public Page<PmWlgRaw> getUserPmWlg(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<PmWlgRaw> spec = buildSpecification(searchParams);
//		checkHourRawData(1,"2014-10-29 12:01:00","2014-10-29 13:00:00");
//		PmWlgHour pmHour =  checkHourRawData(1,DateUtils.stringToDate("2014-10-29 12:00:00"),TimeConstants.TOTAL_OF_MINUTE);
//		log.info(pmHour.toString());
//		pmWlgHourDao.findAll();
//		
//		pmWlgHourDao.save(pmHour);
//		pmWlgHourService.savePmWlgHour(pmHour);
		return pmWlgDao.findAll(spec, pageRequest);
	}

	/**
	 * create pagenation request
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "dateTime","pmNum");
		} else if ("NO".equals(sortType)) {
			sort = new Sort(Direction.ASC, "pmNum");
			sort = sort.and(new Sort(Direction.DESC, "dateTime"));
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * create the dynamic query 
	 */
	private Specification<PmWlgRaw> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PmWlgRaw> spec = DynamicSpecifications.bySearchFilter(filters.values(), PmWlgRaw.class);
		return spec;
	}

	@Autowired
	public void setPmWlgDao(PmWlgRawDao pmWlgDao) {
		this.pmWlgDao = pmWlgDao;
	}
	
}
