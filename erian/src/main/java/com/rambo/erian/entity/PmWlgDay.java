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

@Entity
@Table(name = "pm_wlg_day")
public class PmWlgDay extends IdEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454155825314635342L;

	public PmWlgDay() {
	}

	public PmWlgDay(Date dateTime, float value, double std, float max,
			Date dtMax, float min, Date dtMin, Integer pmId) {

		this.dateTime = dateTime;
		this.value = value;
		this.std = std;
		this.max = max;
		this.dtMax = dtMax;
		this.min = min;
		this.dtMin = dtMin;
		this.pmId = pmId;
	}

	private java.util.Date dateTime;

	private float value;

	private double std;

	private float max;

	private java.util.Date dtMax;

	private float min;

	private java.util.Date dtMin;

	private java.lang.Integer pmId;

	@Length(max = 45)
	private java.lang.String remark;

	// columns END
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.util.Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(java.util.Date dateTime) {
		this.dateTime = dateTime;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public double getStd() {
		return std;
	}

	public void setStd(double std) {
		this.std = std;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.util.Date getDtMax() {
		return dtMax;
	}

	public void setDtMax(java.util.Date dtMax) {
		this.dtMax = dtMax;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.util.Date getDtMin() {
		return dtMin;
	}

	public void setDtMin(java.util.Date dtMin) {
		this.dtMin = dtMin;
	}

	public java.lang.Integer getPmId() {
		return pmId;
	}

	public void setPmId(java.lang.Integer pmId) {
		this.pmId = pmId;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
