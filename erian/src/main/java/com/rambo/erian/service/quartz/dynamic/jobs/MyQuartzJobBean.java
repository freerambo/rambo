package com.rambo.erian.service.quartz.dynamic.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rambo.erian.service.PmWlgHourService;



public class MyQuartzJobBean extends QuartzJobBean {

	private PmWlgHourService pmWlgHourService;
	@Autowired
	public void setPmWlgHourService(PmWlgHourService pmWlgHourService) {
		this.pmWlgHourService = pmWlgHourService;
	}
	
//	private SimpleService simpleService;
//	public void setSimpleService(SimpleService simpleService) {
//		this.simpleService = simpleService;
//	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		Trigger trigger = jobexecutioncontext.getTrigger();
		String triggerName = trigger.getName();		
//		simpleService.testMethod(triggerName);
		System.err.println(pmWlgHourService.getPmWlgHour(1L));
		System.err.println(new Date()+ " "+  triggerName + this.getClass() + " in MyQuartzJobBean  ");

	}

}
