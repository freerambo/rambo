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
@Table(name = "nms_day_results")
public class DayExport  {

	public DayExport() {
	}

	public DayExport(String dateTime, double nanyang,double nieo ) {

		this.dateTime = dateTime;
		this.nanyang = nanyang;
		this.nieo = nieo;
	}

	private String dateTime;


	private double nanyang;

	private double nieo;


	// columns END
	@Id
	public String getDateTime() {
		return this.dateTime;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
