
package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;






import com.rambo.erian.entity.PmWlgHour;

public interface PmWlgHourDao extends PagingAndSortingRepository<PmWlgHour, Long>, JpaSpecificationExecutor<PmWlgHour> {
 
	/**
	 * 
	 * @function: find hourly data by merter ids
	 * @param pmIds
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    5 Nov, 2014 3:31:56 pm
	 */
	@Query("from PmWlgHour pmWlg where pmWlg.pmId in ?1 and pmWlg.dateTime between ?2 and ?3 order by pmWlg.pmId, pmWlg.dateTime")
	List<PmWlgHour> findByPmNums(List<Integer> pmIds, Date start, Date end);
	
	/**
	 * 
	 * @function: find hourly data by merter id
	 * @param pmId
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    5 Nov, 2014 3:32:00 pm
	 */
	@Query("from PmWlgHour pmWlg where pmWlg.pmId = ?1 and pmWlg.dateTime between ?2 and ?3 order by pmWlg.pmId, pmWlg.dateTime")
	List<PmWlgHour> findByPmNum(int pmId, Date start, Date end);

	/**
	 * 
	 * @function: finByDateTime for all meters
	 * @param stringToDate
	 * @return
	 * @author: zhuyuanbo    17 Nov, 2014 11:25:29 am
	 */
	List<PmWlgHour>  findByDateTime(Date stringToDate);
	
	@Query(value="select pmWlg.date_time,pmWlg.value,pmWlg.school,pmWlg.build from v_spms_pmwlg_hour pmWlg where pmWlg.date_time between ?1 and ?2 order by pmWlg.date_time",nativeQuery=true)
	List<Object[]> findByStartAndEnd(Date start, Date end);
	
//	@Query(value="select pmWlg.date_time,pmWlg.real_power from pm_wlg pmWlg where pmWlg.pm_num=?1 and pmWlg.date_time > ?2 order by pmWlg.date_time limit ?3",nativeQuery=true)
//	List<Object[]> getHourlyData(int pmNum, Date start, int nums);
		
}
