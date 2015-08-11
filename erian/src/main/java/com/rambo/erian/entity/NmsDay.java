/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.rambo.erian.entity;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;


import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rambo.erian.entity.IdEntity;


/**
 * @author rambo email:rambo(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "nms_day")
public class NmsDay extends IdEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454155825314635342L;

	public NmsDay() {
	}

	public NmsDay(String dateTime, double value, String unit, double std, double max,
			String dtMax, double min, String dtMin, String meterId) {

		this.dateTime = dateTime;
		this.value = value;
		this.std = std;
		this.max = max;
		this.dtMax = dtMax;
		this.min = min;
		this.dtMin = dtMin;
		this.meterId = meterId;
		this.unit = unit;
	}

	private String dateTime;

	private double value;
	
	private String dtMin;

	private double std;

	private double max;

	private String dtMax;

	private double min;

	private String unit;

	private String meterId;

	@Length(max = 45)
	private java.lang.String remark;

	// columns END
	
	public double getValue() {
		return value;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getStd() {
		return std;
	}

	public void setStd(double std) {
		this.std = std;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getDtMax() {
		return dtMax;
	}

	public void setDtMax(String dtMax) {
		this.dtMax = dtMax;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public String getDtMin() {
		return dtMin;
	}

	public void setDtMin(String dtMin) {
		this.dtMin = dtMin;
	}



	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
