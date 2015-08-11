package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.erian.entity.DayExport;
import com.rambo.erian.entity.HourExport;
import com.rambo.erian.entity.NmsDay;

/**
 * 
 * function description： NmsDayDao
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 11 Dec, 2014 4:42:32 meter
 */
public interface HourExportDao extends PagingAndSortingRepository<HourExport, String>,
		JpaSpecificationExecutor<HourExport> {

	
	
}
