package com.rambo.erian.rest;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.model.Result;
import com.rambo.erian.service.*;

/**
 * Task的Restful API的Controller.
 * 
 * @author yuanbo
 */
@RestController
@RequestMapping(value = "/api/v1/pmWlg")
public class PmWlgRestController {

	private static Logger logger = LoggerFactory
			.getLogger(PmWlgRestController.class);

	@Autowired
	private PmWlgHourService pmWlgHourService;

	@Autowired
	private PmWlgDayService pmWlgDayService;
	@Autowired
	private PmWlgWeekService pmWlgWeekService;
	@Autowired
	private PmWlgMonthService pmWlgMonthService;

	@RequestMapping(value = "days", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<PmWlgDay> getDaily(
			@NotNull @RequestParam(value = "from", defaultValue = "2014-11-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
			@NotNull @RequestParam(value = "end", defaultValue = "2014-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,

			@NotNull @RequestParam(value = "mIds", defaultValue = "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12") String meterIds) {

		String[] smIds = meterIds.split(",");

		if (smIds == null || smIds.length <= 0) {
			String message = "the meters can not be empty!";
			logger.warn(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}

		List<PmWlgDay> pwds = pmWlgDayService.getByPmIdsWithStartAndEnd(
				ListUtils.stringArrayToInt(smIds), fromDate, endDate);

		// ListUtils.output(pwhs);
		return pwds;
	}

	@RequestMapping(value = "hours", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Result> getHourly(
			@RequestParam(value = "from", defaultValue = "2014-11-01 01:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@RequestParam(value = "end", defaultValue = "2014-12-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {

		List<Result> pwhs = pmWlgHourService.getResultsByStartAndEnd(fromDate,
				endDate);
		ListUtils.output(pwhs);
		return pwhs;
	}

}
