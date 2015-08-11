package com.rambo.erian.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.rambo.common.utils.ListUtils;

public class ExportModel {
	String dateTime;
	double nanyang= 0.0;
	double nieo= 0.0;
	double spms= 0.0;

	public ExportModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportModel(String dateTime, double nanyang, double nieo, double spms) {
		super();
		this.dateTime = dateTime;
		
		if(nanyang < 0 && nanyang != -1){
			nanyang = 0;
		}
		if(nieo < 0 && nieo != -1){
			nieo = 0;
		}
		this.nanyang = nanyang;
		this.nieo = nieo;
		this.spms = spms;
	}

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
	
	
	public static List<ExportModel> convert(List<Object[]> ls){
		List<ExportModel> result = null;
		if (ListUtils.isNotEmpty(ls)){
			result = Lists.newArrayList();
			for (Object[] obj : ls) {
				ExportModel em = new ExportModel((String)obj[0],(Double)obj[1],(Double)obj[2],(Double)obj[3]);
				result.add(em);
			}
		}
		return result;
	}
		
}
