package com.erian.microgrid.webapp.model;

import com.erian.microgrid.webapp.DTOUtils;
import com.erian.microgrid.webapp.domain.*;
import com.erian.microgrid.webapp.domain.Device.Status;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 *
 */


/*@NamedStoredProcedureQuery(
	    name="getDevices", 
	    procedureName="get_devices", 
	    resultClasses=DeviceDetails.class, parameters={
	        @StoredProcedureParameter(mode=ParameterMode.IN, name = "sys_id_game", type = Integer.class)
	    }
	)*/
public class DeviceForm implements Serializable {

 /**
  * 
  */
    private static final long serialVersionUID = 1L;
//    Device Name, Description, Location, AC/DC, Programmable or not, Connected or not, ON/OFF
    
    private Long id;
    private String name;
    private String description;
        

    private String location;
    private String bus;
    private String isConnected;
    private String isProgrammable;
    
    private Status status = Status.OFF;

    private Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
	}

	public String getIsProgrammable() {
		return isProgrammable;
	}

	public void setIsProgrammable(String isProgrammable) {
		this.isProgrammable = isProgrammable;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "DeviceDetails [id=" + id + ", name=" + name + ", description=" + description + ", location=" + location
				+ ", bus=" + bus + ", isConnected=" + isConnected + ", isProgrammable=" + isProgrammable + ", status="
				+ status + ", createdDate=" + createdDate + "]";
	}

    

}
