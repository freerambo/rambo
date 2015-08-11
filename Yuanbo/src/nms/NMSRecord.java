package nms;

import java.lang.reflect.Field;

public class NMSRecord {
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

	NMSRecord() {
	}

	NMSRecord(String provider, String meterId, String unit, String startTime,
			String endTime, String tz) {
		this.provider = provider;
		this.meterId = meterId;
		this.unit = unit;
		this.startTime = startTime;
		this.tz = tz;
	}

	NMSRecord(NMSRecord obj) {
		this.provider = obj.provider;
		this.meterId = obj.meterId;
		this.unit = obj.unit;
		this.startTime = obj.startTime;
		this.tz = obj.tz;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

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

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
		return "NMSRecord [provider=" + provider + ", meterId=" + meterId
				+ ", unit=" + unit + ", startTime=" + startTime + ", endTime="
				+ endTime + ", tz=" + tz + ", interval=" + interval + ", no="
				+ no + ", value=" + value + ", st=" + st + "]";
	}

	
	
}
