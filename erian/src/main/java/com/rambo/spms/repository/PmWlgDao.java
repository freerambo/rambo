
package com.rambo.spms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.spms.entity.PmWlg;

public interface PmWlgDao extends PagingAndSortingRepository<PmWlg, Long>, JpaSpecificationExecutor<PmWlg> {
 	
	
/**
 * 
 * @param description
 * @param id
 */
	
	@Query("from PmWlg pmWlg where pmWlg.pmNum=?1 and pmWlg.dateTime between ?2 and ?3 order by pmWlg.dateTime")
	List<PmWlg> findByPmNum(int pmnum, Date start, Date end);
	
	/**
	 * should be very careful bcaz it's native sql, should not be uppercase 
	 * @param pmnum
	 * @param start
	 * @param nums
	 * @return
	 */
	@Query(value="select pmWlg.date_time,pmWlg.realpower from pm_wlg pmWlg where pmWlg.pm_num=?1 and pmWlg.date_time > ?2 order by pmWlg.date_time limit ?3",nativeQuery=true)
	List<Object[]> getHourlyData(int pmnum, Date start, int nums);
	
}
