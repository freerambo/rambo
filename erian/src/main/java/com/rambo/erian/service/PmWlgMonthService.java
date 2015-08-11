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
import com.rambo.erian.entity.PmWlgMonth;
import com.rambo.erian.entity.PmWlgMonth;
import com.rambo.erian.entity.PmWlgWeek;
import com.rambo.erian.repository.PmWlgMonthDao;
import com.rambo.erian.service.PmWlgDayService;

/**
 * 
 * function description： management PmWlgMonth
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 6 Nov, 2014 4:52:52 pm
 */
@Service
// all the public function will be transaction associated
@Transactional(value = "defaultEM")
public class PmWlgMonthService {

	private PmWlgMonthDao dao;

	private PmWlgDayService pmWlgDayService;

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory
			.getLogger(PmWlgMonthService.class); // 日志打印

	@Autowired
	public void setDao(PmWlgMonthDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setPmWlgDayService(PmWlgDayService pmWlgDayService) {
		this.pmWlgDayService = pmWlgDayService;
	}

	/**
	 * 
	 * @function: In PmWlgMonthService getFromDayByPmIdWithStartAndEnd
	 * @param pmId
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo 9 Nov, 2014 10:38:39 pm
	 */
	public PmWlgMonth getFromDayByPmIdWithStartAndEnd(final int pmId,
			Date start, Date end) {

		List<PmWlgDay> pwds = pmWlgDayService.getByPmIdWithStartAndEnd(pmId,
				start, end);

		return caculatePmWlgMonthFromDay(pwds, end);
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
	 * @function:
	 * @param pmId
	 * @param date
	 * @return
	 * @author: zhuyuanbo 10 Nov, 2014 3:44:47 pm
	 */
	public PmWlgMonth getFromDayByPmIdWithDate(final int pmId, Date date) {
		if (date == null || date.after(new Date())) {
			log.warn("the date cannot be null");
			return null;
		}

		date = DateUtils.getEndHourOftheDay(date);

		Date start = DateUtils.getFirstDayOfMonth(date);
		Date end = DateUtils.getLastDayOfMonth(date);
		if(end.after(new Date())){
			log.warn("the end date of the month has not yet come");
			return null;
		}
		log.warn("{},{},{}", date,start,end);
		return this.getFromDayByPmIdWithStartAndEnd(pmId, start, end);
	}

	/**
	 * 
	 * @function: get From Day By PmIds With Date
	 * @param pmIds
	 * @param date
	 * @return
	 * @author: zhuyuanbo 10 Nov, 2014 3:44:53 pm
	 */
	public List<PmWlgMonth> getFromDayByPmIdsWithDate(
			final List<Integer> pmIds, Date date) {

		if (date == null || date.after(new Date())) {
			log.warn("the date cannot be null");
			return null;
		}

		date = DateUtils.getEndHourOftheDay(date);


		Date start = DateUtils.getFirstDayOfMonth(date);
		Date end = DateUtils.getLastDayOfMonth(date);
		if(end.after(new Date())){
			log.warn("the end date of the month has not yet come");
			return null;
		}
		// precess the start and end time
		return this.getFromDayByPmIdsWithStartAndEnd(pmIds, start, end);

	}

	/**
	 * 
	 * @function: caculatePmWlgMonthFromDay
	 * @param pwds
	 * @return
	 * @author: zhuyuanbo 9 Nov, 2014 10:38:59 pm
	 */
	public PmWlgMonth caculatePmWlgMonthFromDay(List<PmWlgDay> pwds, Date date) {
		if (ListUtils.isEmpty(pwds))
			return null;

		float max = 0, min = 0, temp = 0, value = 0;
		Date dtMax = null, dtMin = null, dtTemp = null;
		int count = 0, pmNum = -1;
		List<Float> values = new ArrayList<Float>();
		log.info("in caculatePmWlgMonthFromDay hours size {}", pwds.size());
		for (PmWlgDay pmd : pwds) {

			temp = pmd.getValue();
			dtTemp = pmd.getDateTime();
			value += temp;
			values.add(temp);
			// the first time
			if (count == 0) {
				dtMin = dtMax = dtTemp;
				max = min = temp;
				pmNum = pmd.getPmId();
				log.info("in caculatePmWlgMonthFromDay pmID {}", pmNum);
				// log.warn(pmh.toString());
				// date = DateUtils.getDateWithFormat(dtTemp,
				// DateUtils.END_DATE_FORMAT_OF_DAY);
				// log.warn(date.toString());
			} else {
				// set the max and min value
				if (max < temp) {
					max = temp;
					dtMax = dtTemp;
				}
				if (min > temp) {
					min = temp;
					dtMin = dtTemp;
				}
			}
			count++;
		}
		// value /= TimeConstants.TOTAL_HOURS_OF_DAY;
		Statistics st = new Statistics(values, value
				/ TimeConstants.TOTAL_HOURS_OF_DAY);
		log.info(
				"for meter {} time {} total rows is {}, value is {} max is {} time {}, min is {} time {} stand variance {}",
				pmNum, date, count, value, max, DateUtils.dateToString(dtMax),
				min, DateUtils.dateToString(dtMin), st.getStdDev());

		PmWlgMonth pmMonth = new PmWlgMonth(date, value, st.getStdDev(), max,
				dtMax, min, dtMin, pmNum);

		return pmMonth;

	}

	/**
	 * 
	 * @function: In PmWlgMonthService getFromDayByPmIdsWithStartAndEnd
	 * @param pmId
	 * @param start
	 *            yyyy-mm-dd 00:00:00
	 * @param end
	 *            yyyy-mm-dd 00:00:00
	 * @return
	 * @author: zhuyuanbo 9 Nov, 2014 10:38:39 pm
	 */
	public List<PmWlgMonth> getFromDayByPmIdsWithStartAndEnd(
			final List<Integer> pmIds, Date start, Date end) {

		List<PmWlgMonth> pwds = new ArrayList<PmWlgMonth>();
		for (Integer pmId : pmIds) {
			PmWlgMonth temp = this.getFromDayByPmIdWithStartAndEnd(pmId, start,
					end);
			if (temp != null)
				pwds.add(temp);
		}
		return pwds;
	}

	/**
	 * 
	 * @function: createPmWlgMonthsByPmIdsWithStartAndEnd
	 * @param pmIds
	 * @param start
	 * @param end
	 * @author: zhuyuanbo 9 Nov, 2014 11:04:26 pm
	 */
	public void createPmWlgMonthsByPmIdsWithDate(final List<Integer> pmIds,
			Date date) {

		List<PmWlgMonth> pwms = this.getFromDayByPmIdsWithDate(pmIds, date);
		if (ListUtils.isNotEmpty(pwms)) {
			this.dao.save(pwms);
		}
	}

	/**
	 * 
	 * @function: createPmWlgMonthByPmIdWithDate
	 * @param pmId
	 * @param date
	 * @author: zhuyuanbo 10 Nov, 2014 3:54:43 pm
	 */
	public void createPmWlgMonthByPmIdWithDate(final Integer pmId, Date date) {

		PmWlgMonth pwm = this.getFromDayByPmIdWithDate(pmId, date);
		if (pwm != null) {
			this.dao.save(pwm);
		}
	}

	/**
	 * 
	 * @function: createPmWlgMonths
	 * @param pwms
	 * @author: zhuyuanbo 9 Nov, 2014 11:02:49 pm
	 */
	public void createPmWlgMonths(List<PmWlgMonth> pwms) {
		this.dao.save(pwms);
	}

	/**
	 * 
	 * @function: createPmWlgMonth
	 * @param pwms
	 * @author: zhuyuanbo 9 Nov, 2014 11:02:49 pm
	 */
	public void createPmWlgMonth(PmWlgMonth pwm) {
		this.dao.save(pwm);
	}
	
	/**
	 * 
	 * @function: find all
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 3:56:44 pm
	 */
	public List<PmWlgMonth> getAll(){
		return (List<PmWlgMonth>) dao.findAll();
	}
	

	public Page<PmWlgMonth> getUserPmWlgMonth(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<PmWlgMonth> spec = buildSpecification(searchParams);

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
	private Specification<PmWlgMonth> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PmWlgMonth> spec = DynamicSpecifications.bySearchFilter(filters.values(), PmWlgMonth.class);
		return spec;
	}
	
}
