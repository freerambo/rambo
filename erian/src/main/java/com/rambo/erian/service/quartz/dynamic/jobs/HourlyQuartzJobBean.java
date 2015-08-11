package com.rambo.erian.service.quartz.dynamic.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.erian.service.PmWlgHourService;



public class HourlyQuartzJobBean extends QuartzJobBean {

	private PmWlgHourService pmWlgHourService;
	
	@Autowired
	public void setPmWlgHourService(PmWlgHourService pmWlgHourService) {
		this.pmWlgHourService = pmWlgHourService;
	}
	

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {

//		pmWlgHourService.checkDayDataFromHours(MeterConstants.METER_IDS_OF_SPMS, date);
//		pmWlgHourService.createAllPmWlgHour(MeterConstants.METER_IDS_OF_SPMS, start, end);
//		System.err.println(pmWlgHourService.getPmWlgHour(1L));
//    	Date end = new Date();
//    	DateUtils.getEndMinuteOfHour(end);
//		pmWlgHourService.createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, end);
		
		pmWlgHourService.hourlyJob();
	}

}
