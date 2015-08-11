package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.erian.entity.NmsDay;

/**
 * 
 * function description： NmsDayDao
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 11 Dec, 2014 4:42:32 meter
 */
public interface NmsDayDao extends PagingAndSortingRepository<NmsDay, Long>,
		JpaSpecificationExecutor<NmsDay> {

	public NmsDay findByMeterIdAndUnitAndDateTime(final String meterId,
			String unit, String time);

	public List<NmsDay> findByMeterId(final String meterId);

	public List<NmsDay> findByMeterIdAndUnit(final String meterId, String unit);

	public List<NmsDay> findByMeterIdAndDateTime(final String meterId,
			String time);

	@Query("select sum (obj.value) FROM NmsDay obj where obj.meterId in ?1 and obj.unit = ?2 and obj.dateTime = ?3")
	public Double findByMeterIdsAndUnitAndDateTime(List<String> meterIds, String unit, String time);

	
	@Query(value="select obj.date_time,obj.nanyang,obj.nieo,obj.spms from v_export_day_view obj where obj.date_time between ?1 and ?2 order by obj.date_time",nativeQuery=true)
	List<Object[]> findByStartAndEnd(String start, String end);
	
	
	@Query(value="select obj.date_time,obj.nanyang,obj.nieo from v_export_nms_day_view obj where obj.date_time between ?1 and ?2 order by obj.date_time",nativeQuery=true)
	List<Object[]> findNMSByStartAndEnd(String start, String end);

//	@Query(value="select sum(obj.nanyang),Sum(obj.nieo) from v_export_nms_day_view obj where obj.date_time between ?1 and ?2 order by obj.date_time",nativeQuery=true)
	@Query(value="select sum(obj.nanyang),Sum(obj.nieo) from nms_day_results obj where obj.date_time between ?1 and ?2 order by obj.date_time",nativeQuery=true)
	Object[] findOneDateByStartAndEnd(String start, String end);
	
}
