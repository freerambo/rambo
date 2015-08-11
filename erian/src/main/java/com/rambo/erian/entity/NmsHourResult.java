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
@Table(name = "nms_result")
public class NmsHourResult extends IdEntity {

	public NmsHourResult() {
	}

	public NmsHourResult(String dateTime, double value, String location,String unit ) {

		this.dateTime = dateTime;
		this.value = value;
		this.location = location;
		this.unit = unit;

	}

	private String dateTime;

	private String remark;
	
	private String unit;

	private double value;

	private String location;

	// columns END
	public String getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
