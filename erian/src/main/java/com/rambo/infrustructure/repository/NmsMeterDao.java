package com.rambo.infrustructure.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rambo.infrustructure.entity.NmsMeter;


@Repository("nmsMeterDao")
public interface NmsMeterDao extends
		PagingAndSortingRepository<NmsMeter, Long>,
		JpaSpecificationExecutor<NmsMeter> {

	/**
	 * 
	 * @function: find By MeterId
	 * @param meterId
	 * @return
	 * @author: zhuyuanbo 9 Dec, 2014 2:38:18 pm
	 */
	NmsMeter findByMeterId(String meterId);


	

	@Query("from NmsMeter obj where obj.meterId in ?1")
	List<NmsMeter> findByMeterIds(List<String> meterIds);


	@Query("select obj.meterId from NmsMeter obj where obj.location = ?1")
	List<String> findByLocation(String location);
	
}
