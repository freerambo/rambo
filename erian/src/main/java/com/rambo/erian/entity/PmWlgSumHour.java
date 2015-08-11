/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.rambo.erian.entity;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rambo.erian.entity.IdEntity;

import java.util.*;

/**
 * @author rambo email:rambo(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

//@Entity
//@Table(name = "v_spms_pmwlg_hour")
public class PmWlgSumHour implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454155825314635342L;

	public PmWlgSumHour() {
	}

	public PmWlgSumHour(Date dateTime, double value,String school, String build) {

		this.dateTime = dateTime;
		this.value = value;
		this.school = school;
		this.school = school;

	}

	private java.util.Date dateTime;

	private double value;
	private String school;

	private String build;

	// columns END
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.util.Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(java.util.Date dateTime) {
		this.dateTime = dateTime;
	}

	
	public double getValue() {
		return value;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
