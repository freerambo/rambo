package com.rambo.erian.rest;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.ListUtils;
import com.rambo.common.utils.excel.ExportModel;
import com.rambo.erian.entity.DayExport;
import com.rambo.erian.entity.HourExport;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.service.*;
import com.rambo.erian.service.nms.NmsService;

/**
 * Task的Restful API的Controller.
 * 
 * @author yuanbo
 */
@RestController
@RequestMapping(value = "/api/v1/nms")
public class NMSRestController {

	private static Logger logger = LoggerFactory
			.getLogger(NMSRestController.class);

	@Autowired
	private NmsService service;

	@RequestMapping(value = "days", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<ExportModel> getDays(
			@NotNull @RequestParam(value = "from", defaultValue = "2014-11-01") @DateTimeFormat(pattern = "yyyy-MM-dd") String from,
			@NotNull @RequestParam(value = "end", defaultValue = "2014-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") String end) {

		List<ExportModel> ls = service.getDayExport(from, end);
		return ls;
	}

	@RequestMapping(value = "hours", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<ExportModel> getHours(
			@RequestParam(value = "from", defaultValue = "2014-11-01 01:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String from,
			@RequestParam(value = "end", defaultValue = "2014-12-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String end) {
		List<ExportModel> ls = service.getHourExport(from, end);
		return ls;
	}

}
