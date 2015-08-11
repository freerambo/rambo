package com.rambo.erian.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.rambo.erian.service.PmWlgHourService;
import com.rambo.erian.service.quartz.dynamic.ISchedulerService;

/**
 * Task的Restful API的Controller.
 * 
 * @author yuanbo
 */
@RestController
@RequestMapping(value = "/api/v1/schedular")
public class SchedularController {

	private static Logger logger = LoggerFactory.getLogger(SchedularController.class);

	@Autowired
	private PmWlgHourService pmWlgHourService;
	
	@Autowired
	private ISchedulerService schedulerService;
	
	/**
	 * 
	 * @function: start the schedular
	 * @return
	 * @author: zhuyuanbo    11 Nov, 2014 3:59:17 pm
	 */
	@RequestMapping(value="start",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String start() {
		
			/*	0 08 0/1 * * ? * 
				Fire at 08min every hour

				0 10 02 * * ? * 
				Fire at 02:10am every day

				0 30 02 ? * MON	
				Fire at 02:30am every Monday


				0 05 02 1 * ?	
				Fire at 02:05am on the 1st day of every month
*/	
		
//		schedulerService.schedule("0/10 * * * * ?");
//		schedulerService.schedule("default", "default", "0/10 * * * * ?");
		
		schedulerService.schedule("hourly", "hour", "0 12 0/1 * * ? * ");
		schedulerService.schedule("daily", "day", "0 50 01 * * ? * ");
		schedulerService.schedule("weekly", "week", "0 05 08 ? * MON");
		schedulerService.schedule("monthly", "month", "0 15 08 1 * ?");
		return "start the schedulars";
	}
	
	@RequestMapping(value="stop",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String stop() {
		return "stop the schedular";
	}
	
	@RequestMapping(value="startHour",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public String startHour() {
		return "start the schedular";
	}
}

