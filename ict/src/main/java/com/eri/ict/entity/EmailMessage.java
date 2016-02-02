/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;


public class EmailMessage {
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String msg;
	@NotBlank
	private String phone;
	@NotBlank
	private Date createDate;
	public EmailMessage() {
	}

	
	public EmailMessage(String name, String email, String msg, String phone, Date createDate) {
		super();
		this.name = name;
		this.email = email;
		this.msg = msg;
		this.phone = phone;
		this.createDate = createDate;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}