
package com.rambo.raw.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.raw.entity.PmWlgRaw;

public interface PmWlgRawDao extends PagingAndSortingRepository<PmWlgRaw, Long>, JpaSpecificationExecutor<PmWlgRaw> {
 	
	
/**
 * 
 * @param description
 * @param id
 */
	
	@Query("select pmWlg.dateTime,pmWlg.realPower from PmWlgRaw pmWlg where pmWlg.pmNum=?1 and pmWlg.dateTime > ?2 and pmWlg.dateTime <= ?3 order by pmWlg.dateTime")
	List<Object[]> findByPmNum(int pmNum, Date start, Date end);
	
	/**
	 * should be very careful bcaz it's native sql, should not be uppercase 
	 * @param pmNum
	 * @param start
	 * @param nums
	 * @return
	 */
	@Query(value="select pmWlg.date_time,pmWlg.real_power from pm_wlg pmWlg where pmWlg.pm_num=?1 and pmWlg.date_time > ?2 order by pmWlg.date_time limit ?3",nativeQuery=true)
	List<Object[]> getHourlyData(int pmNum, Date start, int nums);
	
}
