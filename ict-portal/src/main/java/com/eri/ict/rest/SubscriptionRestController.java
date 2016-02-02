/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.rest;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.eri.ict.entity.Subscription;
import com.eri.ict.service.subscription.ISubscriptionService;
import com.google.common.collect.Lists;
import com.rambo.common.utils.ListUtils;

import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

/**
 * Subscription-Restful API-Controller.
 * 
 * @author rambo
 */
@RestController
@RequestMapping(value = "/api/v1/subscription")
public class SubscriptionRestController {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionRestController.class);

	@Autowired
	private ISubscriptionService subscriptionService;

	@Autowired
	private Validator validator;

	/**
	 * 
	 * @param email
	 * @return
	 * @description register the news
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Dec 22, 2015 4:33:07 PM
	 */
	@RequestMapping(value = "register")
	public String register(@Email @RequestParam(value = "email", defaultValue = "-1") String email,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, email);

		Subscription subscription = new Subscription();
		subscription.setStatus("active");
		subscription.setType("external");
		subscription.setEmail(email);
		subscriptionService.saveSubscription(subscription);
		return "Subscripted successfully for <Strong> " + email
				+ " </Strong>; Unsubscibe please click <a href='unsubscribe/" + subscription.getId() + "'>link</a>";
	}

	@RequestMapping(value = "unsubscribe/{id}")
	public String unsubscribe(@NotNull @PathVariable Long id) {
		subscriptionService.deleteSubscription(id);
		return "Unsubscripted successfully!";
	}
}
