/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.rambo.erian.web.nms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;


import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.*;
import com.rambo.erian.service.*;
import com.rambo.erian.service.nms.NmsService;

import org.springside.modules.web.Servlets;
















import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
@RequestMapping(value = "/nms")
public class NmsController {

	private static final String PAGE_SIZE = "12";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "date");
		sortTypes.put("pmId", "pmId");
	}

	@Autowired
	private NmsService service;



	// recommend to use logger in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NmsController.class); // 日志打印

	@RequestMapping(value="day",method = RequestMethod.GET)
	public String listDays(
			@RequestParam(value = "unit", defaultValue = "KWH_IMP_INT") String unit,
			@RequestParam(value = "start", defaultValue = "2014-10-31 00:00:00") String start,
			@RequestParam(value = "end", defaultValue = "2014-11-07 00:00:00") String end,
			Model model, ServletRequest request) {

		
			
			List<NmsDayResult> results =  service.findResultWithStartAndUnit(start, end, unit);
			Set<String> date = Sets.newTreeSet();
			
			List<Double> data1 = Lists.newArrayList();
			List<Double> data2 = Lists.newArrayList();
			List<Double> data3 = Lists.newArrayList();
			List<Double> data4 = Lists.newArrayList();
			List<Double> data5 = Lists.newArrayList();
			List<Double> data6 = Lists.newArrayList();

			List<String> caption = MeterConstants.LOCATIONS_OF_METERs_OF_NMS;
			for(NmsDayResult result: results ){
				date.add(result.getDateTime().substring(0, 10));

				switch(result.getLocation()){
				case "NTU Laboratory":
					data1.add(result.getValue());
					break;
				case "Nanyang Crescent":
					data2.add(result.getValue());
					break;
				case "Nanyang Terrace":
					data3.add(result.getValue());
					break;
				case "Nanyang Meadow":
					data4.add(result.getValue());
					break;
				case "Nanyang Valley":
					data5.add(result.getValue());
					break;
				case "NIEO":
					data6.add(result.getValue());
					break;
				}				
			}
			
			
			model.addAttribute("date", date);
			model.addAttribute("data1", data1);
			model.addAttribute("data2", data2);
			model.addAttribute("data3", data3);
			model.addAttribute("data4", data4);
			model.addAttribute("data5", data5);
			model.addAttribute("data6", data6);
			model.addAttribute("caption", caption);
			model.addAttribute("results", results);
			model.addAttribute("unit", unit);
		 return "nms/day";
	}
	
	 public static Double formatDouble(Double valueToFormat) {
		    long rounded = Math.round(valueToFormat*100);
		    return rounded/100.0;
		 }

	 
	 
	 @RequestMapping(value="days",method = RequestMethod.GET)
		public String listDays(Model model) {
			Date date = new Date();
			

			List<DayExport>  ls = service.getAllDayResults();
//			ListUtils.output(pmWlgs);
			List<Double> values = new ArrayList<Double>(); 
			List<Double> nieos = new ArrayList<Double>(); 

			for(DayExport obj:ls){
				
				values.add(obj.getNanyang());
				nieos.add(obj.getNieo());
			}		
			
			model.addAttribute("date", DateUtils.dateToString(date));
			
			model.addAttribute("type", "Daily");
			model.addAttribute("values", values);
			model.addAttribute("nieos", nieos);

			return "nms/plots/days";
		}
	 
	 @RequestMapping(value="hours",method = RequestMethod.GET)
		public String listHours(Model model) {
			Date date = new Date();
			
			List<HourExport>  ls = service.getAllHourResults();
			List<Double> values = new ArrayList<Double>(); 
			List<Double> nieos = new ArrayList<Double>(); 

			for(HourExport obj:ls){
				
				values.add(obj.getNanyang());
				nieos.add(obj.getNieo());
			}		
			
			model.addAttribute("date", DateUtils.dateToString(date));
			
			model.addAttribute("type", "Daily");
			model.addAttribute("values", values);
			model.addAttribute("nieos", nieos);

			return "nms/plots/hours";
		}
	
}
