package com.rambo.erian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rambo.common.utils.EncryptUtils;
import com.rambo.erian.entity.IdEntity;


@Entity
//@Table(name = "nms_raw_rest")
@Table(name = "nms_hour_kwh_imp_2015")
public class NMSRecord implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	String id;
	String provider;
	String meterId;
	String unit;
	String startTime;
	String endTime;
	String tz;
	int interval;
	int no;
	double value;
	String st;

	public NMSRecord() {
	}

	public NMSRecord(String provider, String meterId, String unit, String startTime,
			String endTime, String tz) {
		this.provider = provider;
		this.meterId = meterId;
		this.unit = unit;
		this.startTime = startTime;
		this.tz = tz;
	}

	public NMSRecord(NMSRecord obj) {
		this.provider = obj.provider;
		this.meterId = obj.meterId;
		this.unit = obj.unit;
		this.startTime = obj.startTime;
		this.endTime = obj.endTime;
		this.tz = obj.tz;
		this.interval = obj.interval;
	}

	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId() {
		String source = this.startTime + this.meterId + this.unit + this.no;
		this.id = EncryptUtils.encryptMD5(source);
	}
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	@Column(name = "meter_id")
	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String date) {
		this.startTime = date;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	@Column(name = "spi")
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	@Override
	public String toString() {
		return "NMSRecord [id=" + id +", provider=" + provider + ", meterId=" + meterId
				+ ", unit=" + unit + ", startTime=" + startTime + ", endTime="
				+ endTime + ", tz=" + tz + ", interval=" + interval + ", no="
				+ no + ", value=" + value + ", st=" + st + "]";
	}

	
	
}
