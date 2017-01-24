package com.erian.microgrid.webapp.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;


/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 *
 */
@Entity
@Table(name = "device")
public class Device implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public enum Status {

        ON,
        OFF
    }
    
    public enum Bus {

        AC,
        DC
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(max = 255)
    private String name;
    @Column(name = "description")
    @Size(max = 255)
    private String description;
    
    @Column(name = "vendor")
    private String vendor;
    
    @Column(name = "model")
    private String model;

    @Column(name = "location")
    private String location;
    @Column(name = "port")
    private String port;
    
    @Column(name = "bus")
    @Enumerated(value = EnumType.STRING)
    private Bus bus = Bus.AC;
    
    @Column(name = "ip")
    private String ip;
    @Column(name = "is_connected")
    private String isConnected;
    @Column(name = "is_programmable")
    private String isProgrammable;
    
    @Column(name = "comments")
    private String comments;
    
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.OFF;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
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

	
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}



	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", description=" + description + ", vendor=" + vendor + ", model="
				+ model + ", location=" + location + ", port=" + port + ", bus=" + bus + ", ip=" + ip + ", isConnected="
				+ isConnected + ", isProgrammable=" + isProgrammable + ", status=" + status + ", createdDate="
				+ createdDate + "]";
	} 

}
