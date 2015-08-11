package com.rambo.infrustructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.rambo.erian.entity.IdEntity;

@Entity
@Table(name = "nms_meters_info")
public class NmsMeter implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	String meterId;
	String location;
	String blk;
	String postalCode;

	public NmsMeter() {
	}

	@Id
	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBlk() {
		return blk;
	}

	public void setBlk(String blk) {
		this.blk = blk;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
