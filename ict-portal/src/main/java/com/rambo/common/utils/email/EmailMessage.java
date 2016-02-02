package com.rambo.common.utils.email;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @description EmailMessage
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 30, 2015 11:19:00 PM
 */
public class EmailMessage {
	// @NotBlank
	// private String name;
	@Email
	private String email;
	@NotBlank
	private String msg;
	@NotBlank
	private String subject;
	// @NotBlank
	// private String phone;
	@NotBlank
	private Date createDate = Clock.DEFAULT.getCurrentDate();

	public EmailMessage() {
	}

	/*
	 * public EmailMessage(String name, String email, String msg, String phone,
	 * Date createDate) { super(); this.name = name; this.email = email;
	 * this.msg = msg; this.phone = phone; this.createDate = createDate; }
	 */

	// // 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}