
package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rambo.erian.entity.NMSRecord;
@Repository
public interface NMSRecordKWHDao extends PagingAndSortingRepository<NMSRecord, Long>, JpaSpecificationExecutor<NMSRecord> {
	
	/**
	 * 
	 * @function: find By MeterId
	 * @param meterId
	 * @return
	 * @author: zhuyuanbo    9 Dec, 2014 2:38:18 pm
	 */
	List<NMSRecord> findByMeterId(String meterId);
	
	List<NMSRecord> findByMeterIdAndStartTime(String meterId,String startTime);

	List<NMSRecord> findByMeterIdAndStartTimeAndUnit(String meterId,String startTime,String unit);

	
}
