/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.rambo.spms.entity;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rambo.erian.entity.IdEntity;

import java.util.*;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


@Entity
@Table(name = "pm_wlg")
public class PmWlg extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	

	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	@NotNull 
	private java.util.Date dateTime;
	@NotNull 
	private float realpower;
	@NotNull 
	private float reactivepower;
	@NotNull 
	private float apparentpower;
	@NotNull 
	private float energy;
//	@NotNull 
	private float frequency;
//	@NotNull 
	private float powerfactor;
//	@NotNull 
	private Integer pmNum;
	//columns END


	public PmWlg(){
	}


	
	public PmWlg(Date dateTime, float realpower) {
		super();
		this.dateTime = dateTime;
		this.realpower = realpower;
	}



	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.util.Date getDateTime() {
		return dateTime;
	}


	public void setDateTime(java.util.Date dateTime) {
		this.dateTime = dateTime;
	}


	
	public float getRealpower() {
		return realpower;
	}



	public void setRealpower(float realpower) {
		this.realpower = realpower;
	}



	public float getReactivepower() {
		return reactivepower;
	}



	public void setReactivepower(float reactivepower) {
		this.reactivepower = reactivepower;
	}



	public float getApparentpower() {
		return apparentpower;
	}



	public void setApparentpower(float apparentpower) {
		this.apparentpower = apparentpower;
	}



	public float getEnergy() {
		return energy;
	}



	public void setEnergy(float energy) {
		this.energy = energy;
	}



	public float getFrequency() {
		return frequency;
	}



	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}



	public float getPowerfactor() {
		return powerfactor;
	}



	public void setPowerfactor(float powerfactor) {
		this.powerfactor = powerfactor;
	}



	public Integer getPmNum() {
		return pmNum;
	}



	public void setPmNum(Integer pmNum) {
		this.pmNum = pmNum;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

