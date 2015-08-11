package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.erian.entity.NmsDay;
import com.rambo.erian.entity.NmsDayResult;

/**
 * 
 * function description： NmsDayDao
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 11 Dec, 2014 4:42:32 meter
 */
public interface NmsDayResultDao extends PagingAndSortingRepository<NmsDayResult, Long>,
		JpaSpecificationExecutor<NmsDayResult> {

	List<NmsDayResult> findByDateTime(String datetime);
	List<NmsDayResult> findByLocation(String location);
	List<NmsDayResult> findByUnit(String unit);
	
	NmsDayResult findByDateTimeAndLocationAndUnit(String datetime,String location,String unit);

	
	@Query("FROM NmsDayResult obj where obj.dateTime between ?1 and ?2 and obj.unit = ?3")
	public List<NmsDayResult> findByStartAndEndAndUnit(String start, String end, String unit);

	
	
}
