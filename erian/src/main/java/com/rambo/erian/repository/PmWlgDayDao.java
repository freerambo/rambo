
package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;







import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
/**
 * 
 * function description： pmwlg -- daily dao operation
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 
 * Create:  5 Nov, 2014 9:43:02 am
 */
public interface PmWlgDayDao extends PagingAndSortingRepository<PmWlgDay, Long>, JpaSpecificationExecutor<PmWlgDay> {
 
	/**
	 * 
	 * @function: getByPmIdWithStartAndEnd
	 * @param pmId
	 * @param start yyyy-mm-dd 00:00:00
	 * @param end  yyyy-mm-dd 00:00:00
	 * @return
	 * @author: zhuyuanbo    6 Nov, 2014 5:05:28 pm
	 */
	@Query("from PmWlgDay pmWlg where pmWlg.pmId = ?1 and pmWlg.dateTime between ?2 and ?3 order by pmWlg.pmId, pmWlg.dateTime")
	public List<PmWlgDay> getByPmIdWithStartAndEnd(final int pmId,Date start, Date end);
	
	/**
	 * 
	 * @function: getByPmIdWithStartAndEnd
	 * @param pmIds
	 * @param start
	 * @param end
	 * @return
	 * @author: zhuyuanbo    6 Nov, 2014 5:05:40 pm
	 */
	@Query("from PmWlgDay pmWlg where pmWlg.pmId in ?1 and pmWlg.dateTime between ?2 and ?3 order by pmWlg.pmId, pmWlg.dateTime")
	public List<PmWlgDay> getByPmIdsWithStartAndEnd(List<Integer> pmIds,Date start, Date end);

	public List<PmWlgDay> findByDateTime(Date start);
	
	
	@Query(value="select pmWlg.date_time,pmWlg.value,pmWlg.school,pmWlg.build from v_spms_pmwlg_day pmWlg where pmWlg.date_time between ?1 and ?2 order by pmWlg.date_time",nativeQuery=true)
	List<Object[]> findByStartAndEnd(Date start, Date end);
	
}
