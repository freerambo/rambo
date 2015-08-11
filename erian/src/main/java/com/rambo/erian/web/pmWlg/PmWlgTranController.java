/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.rambo.erian.web.pmWlg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;


import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.*;
import com.rambo.erian.service.*;

import org.springside.modules.web.Servlets;








import com.google.common.collect.Maps;

/**
 * Controller for pmWlg/tran management, we utilize the Restful Apis:
 * 
 * List page : GET /pmWlg/tran/ Create page : GET /pmWlg/tran/create Create action : POST
 * /pmWlg/tran/create Update page : GET /pmWlg/tran/update/{id} Update action : POST
 * /pmWlg/tran/update Delete action : GET /pmWlg/tran/delete/{id}
 * 
 * @author yuanbo
 */
@Controller
@RequestMapping(value = "/pmWlg/tran")
public class PmWlgTranController {

	private static final String PAGE_SIZE = "12";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "date");
		sortTypes.put("pmId", "pmId");
	}

	@Autowired
	private PmWlgHourService pmWlgHourService;
	@Autowired
	private PmWlgDayService pmWlgDayService;
	@Autowired
	private PmWlgWeekService pmWlgWeekService;
	@Autowired
	private PmWlgMonthService pmWlgMonthService;


	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(PmWlgTranController.class); // 日志打印

	@RequestMapping(value="hour",method = RequestMethod.GET)
	public String listHours(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "school", defaultValue = "spms") String school,
			@RequestParam(value = "build", defaultValue = "wlg") String build,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");

		Page<PmWlgHour> pmWlgs = pmWlgHourService.getUserPmWlgHour(searchParams,
				pageNumber, pageSize, sortType);

		Date date = new Date();
		Date start = DateUtils.getStartHourOftheDay(date);
		Date end = DateUtils.getEndHourOftheDay(DateUtils.getNextDay(date));
//		log.info("in listHoursChart {},{},{}",date,start,end);

		List<Object[]> curent = this.pmWlgHourService.getValuesByStartAndEnd(start, end);
		start = DateUtils.getPreviousWeek(start);
		end = DateUtils.getPreviousWeek(end);
		List<Object[]> pre = this.pmWlgHourService.getValuesByStartAndEnd(start, end);
//		ListUtils.output(pmWlgs);
		List values = new ArrayList(); 
		for(Object[] obj:curent){
			
			values.add(Math.round((double)obj[1]));
		}
		List<Integer> times = new ArrayList<Integer> (); 
		List pres = new ArrayList(); 

		for(Object[] obj:pre){
			int i = DateUtils.getHour((Date)obj[0]);
			times.add( i != 0 ? i : 24 );
//			
			pres.add(Math.round((double)obj[1]));
		}
		
//		ListUtils.output(values);
		model.addAttribute("school", school);
		model.addAttribute("build", build);
		model.addAttribute("pres", pres);
		model.addAttribute("times", times);
		model.addAttribute("date", DateUtils.dateToString(date));
		
		model.addAttribute("type", "Hourly");
		model.addAttribute("values", values);
		
		
		model.addAttribute("pmWlgs", pmWlgs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));
		 return "pmWlg/pmWlgHourList";
	}
	
	 public static Double formatDouble(Double valueToFormat) {
		    long rounded = Math.round(valueToFormat*100);
		    return rounded/100.0;
		 }
//	
//	@RequestParam(value = "from") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date,
	
	@RequestMapping(value="hourChart",method = RequestMethod.GET)
	public String listHoursChart(Model model) {
		Date date = new Date();
		Date start = DateUtils.getStartHourOftheDay(date);
		Date end = DateUtils.getEndHourOftheDay(DateUtils.getNextDay(date));
		log.info("in listHoursChart {},{},{}",date,start,end);

		List<Object[]> curent = this.pmWlgHourService.getValuesByStartAndEnd(start, end);
		
		start = DateUtils.getPreviousWeek(start);
		end = DateUtils.getPreviousWeek(end);
		List<Object[]> pre = this.pmWlgHourService.getValuesByStartAndEnd(start, end);
//		ListUtils.output(pmWlgs);
		List values = new ArrayList(); 
		for(Object[] obj:curent){
			
			values.add(Math.round((double)obj[1]));
		}
		List<Integer> times = new ArrayList<Integer> (); 
		List pres = new ArrayList(); 

		for(Object[] obj:pre){
			int i = DateUtils.getHour((Date)obj[0]);
			times.add( i != 0 ? i : 24 );
//			
			pres.add(Math.round((double)obj[1]));
		}
		
//		ListUtils.output(values);
		model.addAttribute("pres", pres);
		model.addAttribute("times", times);
		model.addAttribute("date", DateUtils.dateToString(date));
		
		model.addAttribute("type", "Hourly");
		model.addAttribute("values", values);

		return "pmWlg/charts/pmWlgHourChart";
	}
	
	
	@RequestMapping(value="hours",method = RequestMethod.GET)
	public String listHours(Model model) {
		Date date = new Date();
		Date start = DateUtils.stringToDate("2014-07-01 00:00:00");
//		Date end = DateUtils.stringToDate("2014-11-21 00:00:00");

		Date end = DateUtils.getEndHourOftheDay(DateUtils.getNextDay(date));
		log.info("in listHoursChart {},{},{}",date,start,end);

		List<Object[]> curent = this.pmWlgHourService.getValuesByStartAndEnd(start, end);
		
	
//		ListUtils.output(pmWlgs);
		List values = new ArrayList(); 
		for(Object[] obj:curent){
			
			values.add(Math.round((double)obj[1]));
		}

//		ListUtils.output(values);
	
		
		model.addAttribute("date", DateUtils.dateToString(date));
		
		model.addAttribute("type", "Hourly");
		model.addAttribute("values", values);

		return "pmWlg/charts/hours";
	}

	@RequestMapping(value="day",method = RequestMethod.GET)
	public String listDays(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "school", defaultValue = "spms") String school,
			@RequestParam(value = "build", defaultValue = "wlg") String build,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");

		Page<PmWlgDay> pmWlgs = pmWlgDayService.getUserPmWlgDay(searchParams,
				pageNumber, pageSize, sortType);

		
		Date now = new Date();
		Date date = DateUtils.getEndHourOftheDay(now);
		
		Date start = DateUtils.getFirstDayOfWeek(date);
		Date end =  DateUtils.getPreviousDay(date);
		//deal with the first day of the week 
//		if(date.equals(start)){
//			 start = DateUtils.getPreviousWeek(start);
//			 end =  DateUtils.getLastDayOfWeek(start);
//		}
		
//		log.info("in listHoursChart {},{},{}",date,start,end);

		List<Object[]> curent = this.pmWlgDayService.getValuesByStartAndEnd(start, end);
	
		//got the same week of previous month
		start = DateUtils.getPreviousMonth(date);
		
		end = DateUtils.getLastDayOfWeek(start);
		start = DateUtils.getFirstDayOfWeek(start);
		List<Object[]> pre = this.pmWlgDayService.getValuesByStartAndEnd(start, end);
//		ListUtils.output(pmWlgs);
		List values = new ArrayList(); 
		for(Object[] obj:curent){
			
			values.add(Math.round((double)obj[1]));
		}
		List pres = new ArrayList(); 

		for(Object[] obj:pre){
			pres.add(Math.round((double)obj[1]));
		}
		
//		ListUtils.output(values);
		model.addAttribute("school", school);
		model.addAttribute("build", build);
		model.addAttribute("pres", pres);
		model.addAttribute("date", DateUtils.dateToString(now));
		
		model.addAttribute("type", "Daily");
		model.addAttribute("values", values);
		
		
		model.addAttribute("pmWlgs", pmWlgs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));
		 return "pmWlg/pmWlgDayList";
	}
	
	
	@RequestMapping(value="week",method = RequestMethod.GET)
	public String listWeeks(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "school", defaultValue = "spms") String school,
			@RequestParam(value = "build", defaultValue = "wlg") String build,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");

		Page<PmWlgWeek> pmWlgs = pmWlgWeekService.getUserPmWlgWeek(searchParams,
				pageNumber, pageSize, sortType);

		
		
		Date now = new Date();
		Date date = DateUtils.getEndHourOftheDay(now);
		
		Date end = DateUtils.getLastDayOfWeek(DateUtils.getPreviousWeek(date));
		Date start =DateUtils.getPreviousWeek(end);
		for(int i = 0; i < 6 ; i++){
			start =  DateUtils.getPreviousWeek(start);
		}

		
//		log.info("in listHoursChart {},{},{}",date,start,end);

		log.info("{},{}",start,end);
		List<Object[]> curent = this.pmWlgWeekService.getValuesByStartAndEnd(start, end);
		
		
//		ListUtils.output(pmWlgs);
		List values = new ArrayList(); 
		for(Object[] obj:curent){
			values.add(Math.round((double)obj[1]));
		}


		
//		ListUtils.output(values);
		model.addAttribute("school", school);
		model.addAttribute("build", build);
		model.addAttribute("date", DateUtils.dateToString(now));
		
		model.addAttribute("type", "Weekly");
		model.addAttribute("values", values);
		
		
		model.addAttribute("pmWlgs", pmWlgs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));
		 return "pmWlg/pmWlgWeekList";
	}
	
	@RequestMapping(value="month",method = RequestMethod.GET)
	public String listMonths(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "school", defaultValue = "spms") String school,
			@RequestParam(value = "build", defaultValue = "wlg") String build,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");

		Page<PmWlgMonth> pmWlgs = pmWlgMonthService.getUserPmWlgMonth(searchParams,
				pageNumber, pageSize, sortType);

		Date now = new Date();
		Date date = DateUtils.getEndHourOftheDay(now);
		
		Date start =DateUtils.getPreviousMonth(DateUtils.getFirstDayOfMonth(date));
		for(int i = 0; i < 3 ; i++){
			start =  DateUtils.getPreviousMonth(start);
		}

		
//		log.info("in listHoursChart {},{},{}",date,start,end);

		log.info("{},{}",start,date);
		List<Object[]> curent = this.pmWlgMonthService.getValuesByStartAndEnd(start, date);
		
		
//		ListUtils.output(pmWlgs);
		List values = new ArrayList(); 
		List<String> times = new ArrayList<String>(); 
		for(Object[] obj:curent){
			values.add(Math.round((double)obj[1]));
			times.add(DateUtils.dateToString((Date)obj[0]));
		}


		
//		ListUtils.output(values);
		model.addAttribute("times", times);

		model.addAttribute("school", school);
		model.addAttribute("build", build);
		model.addAttribute("date", DateUtils.dateToString(now));
		
		model.addAttribute("type", "Monthly");
		model.addAttribute("values", values);
		
		
		model.addAttribute("pmWlgs", pmWlgs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));
		 return "pmWlg/pmWlgMonthList";
	}
	
	
}
