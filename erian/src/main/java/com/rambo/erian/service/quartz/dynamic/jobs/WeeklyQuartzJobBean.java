package com.rambo.erian.service.quartz.dynamic.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.erian.service.*;
import com.rambo.erian.service.nms.NmsService;



public class WeeklyQuartzJobBean extends QuartzJobBean {

	private PmWlgWeekService pmWlgWeekService;
	
	@Autowired
	public void setPmWlgWeekService(PmWlgWeekService pmWlgWeekService) {
		this.pmWlgWeekService = pmWlgWeekService;
	}
	

	private NmsService nms;
	
	
	@Autowired
	public void setNms(NmsService nms) {
		this.nms = nms;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		
    	Date end = DateUtils.getPreviousDay(new Date());
    
		pmWlgWeekService.createPmWlgWeeksByPmIdsWithDate(MeterConstants.METER_IDS_OF_SPMS, end);
	
		nms.createWeekData(DateUtils.dateToString(DateUtils.getPreviousMonth(end)), DateUtils.dateToString(new Date()));

	}

}
