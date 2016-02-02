/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.rest;

import java.net.URI;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.eri.ict.entity.EmailMessage;
import com.eri.ict.entity.Task;
import com.eri.ict.service.task.TaskService;
import com.eri.ict.utilities.email.MimeMailService;

import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

/**
 * Task的Restful API的Controller.
 * 
 * @author Rambo
 */
@RestController
@RequestMapping(value = "/api/v1/email")
public class EmailController {

	private static Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private MimeMailService mimeMailService;

	@Autowired
	private Validator validator;
	
//	Content-type: application/json; charset=utf-8 
	/* {
		 "name":"hello",
		 "msg ":"I love you", 
		 "createDate":"2015-11-20 08:09:11",
		  "email":"zhuyb@ntu.edu.sg",
		  "phone":"86-812389123"
		 }*/	
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public String create(@RequestBody EmailMessage email) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, email);

		logger.info(email.toString());
		// 保存任务
		String msg = mimeMailService.sendNotificationMail(email);
		

		return msg;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String send(@RequestParam String name,@RequestParam String email,@RequestParam String phone 
			,@RequestParam String msg) {

		logger.info(name.toString());
		
		EmailMessage content =  new EmailMessage(name, email, msg, phone, new Date());

		// 保存任务
		String message = mimeMailService.sendNotificationMail(content);
		

		return message;
//		return "hello";

	}
	
}

/*
<div class="alert alert-info">
<button type="button" class="close" data-dismiss="alert">×</button>
<h4>
	提示!
</h4> <strong>警告!</strong> 请注意你的个人隐私安全.
</div>

*/


/*{ "email.name":"hello",
	 "email.msg ":"I love you", 
	 "email.createDate":"2015-11-20 08:09:11",
	  "email.email":"zhuyb@ntu.edu.sg",
	  "email.phone":"86-812389123"
	 }*/
