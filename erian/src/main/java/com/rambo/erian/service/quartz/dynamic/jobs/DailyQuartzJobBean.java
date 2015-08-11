package com.rambo.erian.service.quartz.dynamic.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.erian.service.*;



public class DailyQuartzJobBean extends QuartzJobBean {

	private PmWlgDayService pmWlgDayService;
	private PmWlgHourService pmWlgHourService;

	@Autowired
	public void setPmWlgDayService(PmWlgDayService pmWlgDayService) {
		this.pmWlgDayService = pmWlgDayService;
	}
	
	
	@Autowired
	public void setPmWlgHourService(PmWlgHourService pmWlgHourService) {
		this.pmWlgHourService = pmWlgHourService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {

		pmWlgDayService.dailyJob();
		
//    	Date end = DateUtils.getEndHourOftheDay(new Date());
//    	pmWlgHourService.checkAndCompleteHourlyDateForDay(MeterConstants.METER_IDS_OF_SPMS, end);
//		pmWlgDayService.createAllPmWlgHourWithDate(MeterConstants.METER_IDS_OF_SPMS, new Date());
//		pmWlgDayService.createPmWlgDayMeters(MeterConstants.METER_IDS_OF_SPMS, end);
	}

}
