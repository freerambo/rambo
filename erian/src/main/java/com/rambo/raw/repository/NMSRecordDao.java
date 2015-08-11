
package com.rambo.raw.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rambo.raw.entity.NMSRecord;
@Repository("nMSRecordDao")
public interface NMSRecordDao extends PagingAndSortingRepository<NMSRecord, Long>, JpaSpecificationExecutor<NMSRecord> {
	
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

	
	
	
	@Query("SELECT distinct (obj.meterId) FROM NMSRecord obj where obj.startTime = ?1")
	List<String> findMeterIdsforTime(String start);
	
	@Query("SELECT distinct (obj.meterId) FROM NMSRecord obj where obj.startTime = ?1 and obj.unit in ?2")
	List<String> findMeterIdsforTimeAndUnits(String start, List<String> meterIds);
	
	
	@Query("from NMSRecord obj where obj.meterId in ?1 order by obj.startTime, obj.no")
	List<NMSRecord> findByMeterIds(List<String> meterIds);
//	@Query("from NMSRecord obj where obj.meterId in ?1 and obj.startTime = ?2 order by obj.startTime, obj.no")
	
	@Query("from NMSRecord obj where obj.meterId in ?1 and obj.startTime = ?2 order by obj.no" )
	List<NMSRecord> findByMeterIdsAndStartTime(List<String> meterIds, String startTime);
	
	
	@Query("from NMSRecord obj where obj.meterId in ?1 and obj.startTime = ?2 and obj.unit = ?3 order by obj.no")
	List<NMSRecord> findByMeterIdsAndStartTimeAndUnit(List<String> meterIds, String startTime,String unit);
	
	
	
	
}
