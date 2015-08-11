package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.erian.entity.NmsHour;
import com.rambo.erian.entity.NmsHourResult;

/**
 * 
 * function description： NmsHourDao
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 11 Dec, 2014 4:42:32 meter
 */
public interface NmsHourResultDao extends PagingAndSortingRepository<NmsHourResult, Long>,
		JpaSpecificationExecutor<NmsHourResult> {

	List<NmsHourResult> findByDateTime(String datetime);
	List<NmsHourResult> findByLocation(String location);
	List<NmsHourResult> findByUnit(String unit);
	
	NmsHourResult findByDateTimeAndLocationAndUnit(String datetime,String location,String unit);

	
	@Query("FROM NmsHourResult obj where obj.dateTime between ?1 and ?2 and obj.unit = ?3")
	public List<NmsHourResult> findByStartAndEndAndUnit(String start, String end, String unit);


	
}
