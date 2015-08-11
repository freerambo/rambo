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
import com.rambo.common.utils.EncryptUtils;
import com.rambo.erian.entity.IdEntity;

/**
 * @author rambo email:rambo(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "nms_kwh_imp_hour")
public class NmsKwhImpHour implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454155825314635342L;

	public NmsKwhImpHour() {
	}

	public NmsKwhImpHour(String dateTime, double value, String unit,
			String meterId) {

		this.dateTime = dateTime;
		this.value = value;

		this.meterId = meterId;
		this.unit = unit;
		this.id = EncryptUtils.encryptMD5(this.dateTime + this.meterId
				+ this.unit);
	}

	private String id;

	private String dateTime;

	private double value;

	private String unit;

	private String meterId;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
