package com.rambo.erian.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * create for present the results
 * 
 * @author rambo
 *
 */
public class Result {

	double value;
	String school;
	String build;
	Date dateTime;

	public Result() {
		super();
	}

	public Result(double value, String school, String build, Date dateTime) {
		super();
		this.value = value;
		this.school = school;
		this.build = build;
		this.dateTime = dateTime;
	}

	public double getValue() {
//keep the second Digits
		return Math.round(value*100)/100.0;

	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
