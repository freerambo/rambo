package com.rambo.erian.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rambo.erian.entity.NmsHour;

/**
 * 
 * function description： NmsHourDao
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 11 Dec, 2014 4:42:32 meter
 */
public interface NmsHourDao extends PagingAndSortingRepository<NmsHour, Long>,
		JpaSpecificationExecutor<NmsHour> {

	public NmsHour findByMeterIdAndUnitAndDateTime(final String meterId,
			String unit, String time);

	public List<NmsHour> findByMeterId(final String meterId);

	public List<NmsHour> findByMeterIdAndUnit(final String meterId, String unit);

	public List<NmsHour> findByMeterIdAndDateTime(final String meterId,
			String time);

	@Query("select sum (obj.value) FROM NmsHour obj where obj.meterId in ?1 and obj.unit = ?2 and obj.dateTime = ?3")
	public Double findByMeterIdsAndUnitAndDateTime(List<String> meterIds, String unit, String time);
	
	@Query(value="select hour.date_time,hour.nanyang,hour.nieo,hour.spms from v_export_hour_view hour where hour.date_time between ?1 and ?2 order by hour.date_time",nativeQuery=true)
	List<Object[]> findByStartAndEnd(String start, String end);
}
