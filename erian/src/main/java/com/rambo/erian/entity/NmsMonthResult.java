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
@Table(name = "nms_month_result")
public class NmsMonthResult implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454155825314635342L;

	public NmsMonthResult() {
	}

	public NmsMonthResult(String dateTime, double nanyang, double nieo,
			double spms) {
		super();
		this.dateTime = dateTime;
		if(nanyang < 0){
			nanyang = 0;
		}
		if(nieo < 0){
			nanyang = 0;
		}
		this.nanyang = nanyang;
		this.nieo = nieo;
		this.spms = spms;
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private String dateTime;

	private double nanyang;
	private double nieo;
	private double spms;
	private String remark;
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public double getNanyang() {
		return nanyang;
	}

	public void setNanyang(double nanyang) {
		this.nanyang = nanyang;
	}

	public double getNieo() {
		return nieo;
	}

	public void setNieo(double nieo) {
		this.nieo = nieo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getSpms() {
		return spms;
	}

	public void setSpms(double spms) {
		this.spms = spms;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
