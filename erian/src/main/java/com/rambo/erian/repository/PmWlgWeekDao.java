
package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;



import com.rambo.erian.entity.PmWlgWeek;
/**
 * 
 * function description： pmwlg -- daily dao operation
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 
 * Create:  5 Nov, 2014 9:43:02 am
 */
public interface PmWlgWeekDao extends PagingAndSortingRepository<PmWlgWeek, Long>, JpaSpecificationExecutor<PmWlgWeek> {
 
	
	@Query(value="select pmWlg.date_time,pmWlg.value,pmWlg.school,pmWlg.build from v_spms_pmwlg_week pmWlg where pmWlg.date_time between ?1 and ?2 order by pmWlg.date_time",nativeQuery=true)
	List<Object[]> findByStartAndEnd(Date start, Date end);
	
	@Query(value="select round(pmWlg.value,2) from v_spms_pmwlg_week pmWlg where pmWlg.date_time = ?1",nativeQuery=true)
	Object findByDateTime(Date start);
	
}
