/**
 * rambo
 */
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.entity.PmWlgMonth;
import com.rambo.erian.repository.PmWlgDayDao;
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
public class PmWlgDayService {

	private PmWlgDayDao dao;

	private PmWlgHourService pmWlgHourService;

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(PmWlgDayService.class); // 日志打印

	public PmWlgDay getPmWlgDay(Long id) {
		return dao.findOne(id);
	}

	public void savePmWlgDay(PmWlgDay entity) {
		dao.save(entity);
	}

	public boolean createPmWlgDayMeters(final List<Integer> pmNums, Date date) {
		if (date == null) {
			log.error("date can not be null");
			return false;
		}
		date = DateUtils.getEndHourOftheDay(date);

		List<PmWlgDay> pmds = this.pmWlgHourService.getPmWlgDayMeters(pmNums,
				date);
		if (ListUtils.isNotEmpty(pmds)) {
			this.dao.save(pmds);
//			ListUtils.output(pmds);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @function: generate the PmWlgDays By the Meters for date period
	 * @param pmNums
	 * @param start
	 *            include
	 * @param end
	 *            exclude
	 * @author: zhuyuanbo 6 Nov, 2014 4:03:45 pm
	 */
	public void generatePmWlgDaysByMeters(final List<Integer> pmNums,
			Date start, Date end) {

		if (start == null || start.after(end)) {
			log.error("start date can not be null or improper");
		}
		if (end == null || end.after(new Date())) {
			end = new Date();
		}

		end = DateUtils.getEndHourOftheDay(end);

		while (start.before(end)) {
			this.createPmWlgDayMeters(pmNums, start);
			start = DateUtils.getNextDay(start);
		}
	}

	/**
	 * ccreateAllPmWlgDay with start and end 
	 * 
	 * @param meters
	 * @param start
	 */
	public void createAllPmWlgDay(List<Integer> meters, Date start, Date end) {

		List<PmWlgDay> pmhs = this.getByPmIdsWithStartAndEnd(meters,
				start, end);
		if (ListUtils.isNotEmpty(pmhs)) {
			log.info("in createAllPmWlgDay : " + pmhs.size());
			this.dao.save(pmhs);
		}
	}

	public void deletePmWlgDay(Long id) {
		dao.delete(id);
	}

	public List<PmWlgDay> getAllPmWlgDay() {
		return (List<PmWlgDay>) dao.findAll();
	}

	/**
	 * 
	 * @function:getByPmIdWithStartAndEnd
	 * @param pmId
	 * @param start
	 *            yyyy-mm-dd 00:00:00
	 * @param end
	 *            yyyy-mm-dd 00:00:00
	 * @return
	 * @author: zhuyuanbo 6 Nov, 2014 5:09:23 pm
	 */
	public List<PmWlgDay> getByPmIdWithStartAndEnd(final int pmId, Date start,
			Date end) {

		if (start == null || end == null || start.after(end)
				|| end.after(new Date())) {
			log.error("improper start or end time");
			return null;
		}

		List<PmWlgDay> pwds = dao.getByPmIdWithStartAndEnd(pmId, start, end);

		return pwds;
	}

	/**
	 * 
	 * @function: main function: get By PmIds With Start And End
	 * @param pmIds
	 * @param start
	 *            yyyy-mm-dd 00:00:00
	 * @param end
	 *            yyyy-mm-dd 00:00:00
	 * @return
	 * @author: zhuyuanbo 6 Nov, 2014 5:10:24 pm
	 */
	public List<PmWlgDay> getByPmIdsWithStartAndEnd(List<Integer> pmIds,
			Date start, Date end) {

		if (start == null || end == null || start.after(end)
				|| end.after(new Date())) {
			log.error("improper start or end time");
			return null;
		}
		// precess the start and end time
		start = DateUtils.getEndHourOftheDay(start);
		end = DateUtils.getEndHourOftheDay(end);

		List<PmWlgDay> pwds = new ArrayList<PmWlgDay>();
		for (Integer pmId : pmIds) {
			List<PmWlgDay> temp = this.getByPmIdWithStartAndEnd(pmId, start,
					end);
			pwds.addAll(temp);
		}
		return pwds;
	}

	/**
	 * 
	 * @function: find all
	 * @return
	 * @author: zhuyuanbo 10 Nov, 2014 3:56:44 pm
	 */
	public List<PmWlgDay> getAll() {
		return (List<PmWlgDay>) dao.findAll();
	}

	@Autowired
	public void setDao(PmWlgDayDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setPmWlgHourService(PmWlgHourService pmWlgHourService) {
		this.pmWlgHourService = pmWlgHourService;
	}
	
	
	/**
	 * 
	 * @function: check the daily data in a week if missing then create 
	 * @author: zhuyuanbo    21 Nov, 2014 2:11:46 pm
	 */
	public void dailyJob() {
		
		Date now = DateUtils.getEndHourOftheDay(new Date());

		Date start = DateUtils.getPreviousMonth(now); 
		log.info("now {}, start {}", now,start);		
		while (!start.after(now)){
			List<PmWlgDay> pwds = dao.findByDateTime(start);
			log.info("start {}, {}" , start, pwds.size());
			if(ListUtils.isEmpty(pwds) ){
				log.error("daily results is null or wrong number for time {}", start);
				createPmWlgDayMeters(MeterConstants.METER_IDS_OF_SPMS, start);
			}
			start = DateUtils.getNextDay(start);
		}	
	}


	/**
	 * 
	 * @function: get sumption of dailyValues By Start And End for charts
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    24 Nov, 2014 4:57:14 pm
	 */
	public List<Object[]> getValuesByStartAndEnd(Date start,Date end){
		
		return dao.findByStartAndEnd(start, end);
		
	}
	
	public Page<PmWlgDay> getUserPmWlgDay(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<PmWlgDay> spec = buildSpecification(searchParams);

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
	private Specification<PmWlgDay> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<PmWlgDay> spec = DynamicSpecifications.bySearchFilter(filters.values(), PmWlgDay.class);
		return spec;
	}

}
