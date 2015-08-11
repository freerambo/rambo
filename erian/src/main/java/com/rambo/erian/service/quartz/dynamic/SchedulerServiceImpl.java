package com.rambo.erian.service.quartz.dynamic;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.rambo.erian.service.PmWlgMonthService;

@Service("schedulerService")
public class SchedulerServiceImpl implements ISchedulerService {

	private Scheduler scheduler;
	private JobDetail jobDetail;

	static ApplicationContext context = new ClassPathXmlApplicationContext(
			"schedule/jobs.xml");

	// @Autowired
	// public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
	// this.jobDetail = jobDetail;
	// }

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void schedule(String cronExpression) {
		System.err.println(this.scheduler.getClass() + " in schedule "
				+ cronExpression);
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, String cronExpression) {
		try {
			schedule(name, new CronExpression(cronExpression));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(CronExpression cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, CronExpression cronExpression) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name,
					Scheduler.DEFAULT_GROUP, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
			scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP, cronTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(Date startTime) {
		System.out.println("in schedule  " + startTime);
		schedule(startTime, null);
	}

	@Override
	public void schedule(String name, Date startTime) {
		schedule(name, startTime, null);
	}

	@Override
	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, 0);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime, 0);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule(null, startTime, endTime, 0);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount) {
		schedule(name, startTime, endTime, 0, 0L);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}
		System.err.println(name + " in simpletrigger");

		try {
			scheduler.addJob(jobDetail, true);

			SimpleTrigger SimpleTrigger = new SimpleTrigger(name,
					Scheduler.DEFAULT_GROUP, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP, startTime, endTime, repeatCount,
					repeatInterval);
			scheduler.scheduleJob(SimpleTrigger);
			scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP,
					SimpleTrigger);

		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(String name, String jobName, String cronExpression) {
		// TODO Auto-generated method stub
		this.setJob(jobName);
		this.schedule(name, cronExpression);
	}
/**
 * set the job Name
 * @function:
 * @param jobName
 * @author: zhuyuanbo    11 Nov, 2014 3:49:51 pm
 */
	public void setJob(String jobName) {
		switch (jobName) {
		case ("hour"):
			jobDetail = (JobDetail) context.getBean("hourjobDetail");
			break;
		case ("day"):
			jobDetail = (JobDetail) context.getBean("dayjobDetail");
			break;
		case ("week"):
			jobDetail = (JobDetail) context.getBean("weekjobDetail");
			break;
		case ("month"):
			jobDetail = (JobDetail) context.getBean("monthjobDetail");
			break;
		default:
			jobDetail = (JobDetail) context.getBean("jobDetail");
		}
	}
}
