package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.common.utils.excel.ExportModel;
import com.rambo.erian.entity.NmsWeekResult;

/**
 * 
 * function description： NmsWeekDao
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 11 Dec, 2014 4:42:32 meter
 */
public interface NmsWeekResultDao extends PagingAndSortingRepository<NmsWeekResult, String>,
		JpaSpecificationExecutor<NmsWeekResult> {

	

	
	@Query(value=" from NmsWeekResult obj where obj.dateTime between ?1 and ?2 order by obj.dateTime")
	List<ExportModel> findByStartAndEnd(String start, String end);
	

}
