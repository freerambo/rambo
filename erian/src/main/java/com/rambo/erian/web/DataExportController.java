/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.rambo.erian.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
import com.rambo.common.utils.excel.ExportModel;
import com.rambo.common.utils.excel.JxlsUtil;
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
 * List page : GET /pmWlg/tran/ Create page : GET /pmWlg/tran/create Create
 * action : POST /pmWlg/tran/create Update page : GET /pmWlg/tran/update/{id}
 * Update action : POST /pmWlg/tran/update Delete action : GET
 * /pmWlg/tran/delete/{id}
 * 
 * @author yuanbo
 */
@Controller
@RequestMapping(value = "/export")
public class DataExportController {

	@Autowired
	private NmsService service;

	@Autowired
	private PmWlgHourService pmWlgHourService;
	@Autowired
	private PmWlgDayService pmWlgDayService;
	@Autowired
	private PmWlgWeekService pmWlgWeekService;
	@Autowired
	private PmWlgMonthService pmWlgMonthService;

	// recommend to use logger in this way for slf4j
	private static Logger log = LoggerFactory
			.getLogger(DataExportController.class); // 日志打印

	@RequestMapping(method = RequestMethod.GET)
	public String toExport(Model model) {

		model.addAttribute("action", "export");

		return "nms/export";
	}

	public static Double formatDouble(Double valueToFormat) {
		long rounded = Math.round(valueToFormat * 100);
		return rounded / 100.0;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void export(

			@RequestParam(value = "interval", defaultValue = "hourly") String interval,
			@RequestParam(value = "areas") String[] areas,
			@RequestParam(value = "start", defaultValue = "2014-10-31") String start,
			@RequestParam(value = "end", defaultValue = "2014-11-07") String end,
			Model model, HttpServletResponse response) {

		start = DateUtils.getFullTimeString(start);
		end = DateUtils.getFullTimeString(end);
		String fName = null;
		log.info("{},{},{},{}", interval, areas, start, end);

		Date s = DateUtils.stringToDate(start);
		Date e = DateUtils.stringToDate(end);

		List<ExportModel> outs = Lists.newArrayList();

		if ("hourly".equalsIgnoreCase(interval)) {

			fName = "Hourly-";
			outs = service.getHourExport(start, end);

		} else if ("daily".equalsIgnoreCase(interval)) {
			fName = "Daily-";

			outs = service.getDayExport(start, end);
		} else if ("weekly".equalsIgnoreCase(interval)) {
			fName = "Weekly-";

			s = DateUtils.getLastDayOfWeek(s);
			e = DateUtils.getLastDayOfWeek(e);
			start = DateUtils.dateToString(s);
			end = DateUtils.dateToString(e);
			outs = service.getWeekExport(start, end);
			
		} else if ("monthly".equalsIgnoreCase(interval)) {
			fName = "Monthly-";
			s = DateUtils.getLastDayOfMonth(s);
			e = DateUtils.getLastDayOfMonth(e);
			start = DateUtils.dateToString(s);
			end = DateUtils.dateToString(e);
			
			outs = service.getMonthExport(start, end);
		}

		fName += System.currentTimeMillis() + ".xls";

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fName);
		try {
			JxlsUtil.export(outs, response.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 
	 * @function: discard one
	 * @param interval
	 * @param areas
	 * @param start
	 * @param end
	 * @param model
	 * @param response
	 * @author: zhuyuanbo 15 Jan, 2015 8:17:21 pm
	 */
	@RequestMapping(value="old",method = RequestMethod.POST)
	public void exportOld(

			@RequestParam(value = "interval", defaultValue = "hourly") String interval,
			@RequestParam(value = "areas") String[] areas,
			@RequestParam(value = "start", defaultValue = "2014-10-31") String start,
			@RequestParam(value = "end", defaultValue = "2014-11-07") String end,
			Model model, HttpServletResponse response) {

		start = DateUtils.getFullTimeString(start);
		end = DateUtils.getFullTimeString(end);
		String fName = null;
		log.info("{},{},{},{}", interval, areas, start, end);

		Date s = DateUtils.stringToDate(start,
				DateUtils.DATE_FORMAT_WITHOUT_TIME);
		Date e = DateUtils
				.stringToDate(end, DateUtils.DATE_FORMAT_WITHOUT_TIME);

		List<ExportModel> outs = Lists.newArrayList();

		if ("hourly".equalsIgnoreCase(interval)) {

			fName = "Hourly-";
			outs = service.getHourExport(start, end);

		} else if ("daily".equalsIgnoreCase(interval)) {
			fName = "Daily-";
			// get all daily data of NMS
			List<NmsDayResult> results = service.findResultWithStartAndUnit(
					start, end,
					MeterConstants.TYPES_OF_METERs_OF_NMS.KVARH_IMP_INT
							.toString());

			// get all daily data of SPMS

//			List<Object[]> spms = pmWlgDayService.getValuesByStartAndEnd(s, e);
			// List<PmWlgDay> pwds = pmWlgDayService.getByPmIdsWithStartAndEnd(
			// MeterConstants.METER_IDS_OF_SPMS, s, e);

			while (!s.after(e)) {
				String date = DateUtils.dateToString(s);
				ExportModel em = new ExportModel();
				em.setDateTime(date);

				List<NmsDayResult> sub = getSublistByDate(results, date);
				if (sub != null && sub.size() > 0) {

					for (NmsDayResult result : sub) {
						if ("NIEO".equals(result.getLocation())) {
							em.setNieo(result.getValue());
						}
						if ("Nanyang".equals(result.getLocation())) {
							em.setNanyang(result.getValue());
						}
					}

				}
/*				for (Object[] obj : spms) {

					Timestamp stamp = (Timestamp) obj[0];
					Date dt = new Date(stamp.getTime());

					String d = DateUtils.dateToString(dt);
					log.info(d);
					if (d.equals(date)) {
						em.setSpms((Double) obj[1]);
					}
				}*/
				log.info("em {} ", em);
				outs.add(em);

				s = DateUtils.getNextDay(s);
			}

//			log.info("{},{}", spms.size(), results.size());

		} else {
			// other types
		}

		fName += System.currentTimeMillis() + ".xls";

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fName);
		try {
			JxlsUtil.export(outs, response.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 
	 * @function: get sublist from original list
	 * @param list
	 * @param date
	 * @return
	 * @author: zhuyuanbo 12 Jan, 2015 2:39:47 pm
	 */
	List<NmsDayResult> getSublistByDate(List<NmsDayResult> list, String date) {

		List<NmsDayResult> sub = null;
		// check the original list and initialize the sublist
		if (ListUtils.isNotEmpty(list)) {
			sub = new ArrayList<NmsDayResult>();

		} else {
			return null;
		}

		// get the sub list
		for (NmsDayResult result : list) {

			if (result.getDateTime().equals(date)
					&& !"NTU Laboratory".equals(result.getLocation())) {
				sub.add(result);
			}
		}

		if (ListUtils.isNotEmpty(sub)) {
			double data = 0.0;
			for (NmsDayResult result : sub) {
				// deal with the negative value set zero
				double value = result.getValue();
				if (value < 0) {
					value = 0;
				}
				switch (result.getLocation()) {

				case "Nanyang Crescent":
					data += value;
					// sub.remove(result);
					break;
				case "Nanyang Terrace":
					data += value;
					// sub.remove(result);
					break;
				case "Nanyang Meadow":
					data += value;
					// sub.remove(result);
					break;
				case "Nanyang Valley":
					data += value;
					// sub.remove(result);
					break;

				}

			}
			// NmsDayResult(String dateTime, double value, String
			// location,String unit )
			NmsDayResult result = new NmsDayResult(date, data, "Nanyang",
					MeterConstants.TYPES_OF_METERs_OF_NMS.KWH_EXP_INT
							.toString());
			sub.add(result);
			log.info("new result {}", result);
		}

		return sub;
	}

}
