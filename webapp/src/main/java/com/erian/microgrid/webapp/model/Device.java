package com.erian.microgrid.webapp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Device {
	private int ID;
	private int TypeID;
	private String TypeName;
	private int ClassID;
	private String ClassName;
	private String Name;
	private String Description;
	private int MicrogridID;
	private String MicrogridName;
	private String Vendor;
	private String Model;
	private String Location;
	private String IPAdress;
	private String PortNumber;
	private int BusID;
	private int IsProgrammable;
	private int IsConnected;
	
	public Device(){
		
	}
	
	public Device(int ID,int TypeID,String TypeName,int ClassID,String ClassName,String Name,String Description,int MicrogridID,String MicrogridName,String Vendor,String Model,String Location,String IPAdress,String PortNumber,int BusID,int IsProgrammable,int IsConnected){
		this.ID=ID;
		this.TypeID=TypeID;
		this.TypeName=TypeName;
		this.ClassID=ClassID;
		this.ClassName=ClassName;
		this.Name=Name;
		this.Description=Description;
		this.MicrogridID=MicrogridID;
		this.MicrogridName=MicrogridName;
		this.Vendor=Vendor;
		this.Model=Model;
		this.Location=Location;
		this.IPAdress=IPAdress;
		this.PortNumber=PortNumber;
		this.BusID=BusID;
		this.IsProgrammable=IsProgrammable;
		this.IsConnected=IsConnected;
		
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getTypeID() {
		return TypeID;
	}
	public void setTypeID(int typeID) {
		TypeID = typeID;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public int getClassID() {
		return ClassID;
	}
	public void setClassID(int classID) {
		ClassID = classID;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getMicrogridID() {
		return MicrogridID;
	}
	public void setMicrogridID(int microgridID) {
		MicrogridID = microgridID;
	}
	public String getMicrogridName() {
		return MicrogridName;
	}
	public void setMicrogridName(String microgridName) {
		MicrogridName = microgridName;
	}
	public String getVendor() {
		return Vendor;
	}
	public void setVendor(String vendor) {
		Vendor = vendor;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getIPAdress() {
		return IPAdress;
	}
	public void setIPAdress(String iPAdress) {
		IPAdress = iPAdress;
	}
	public String getPortNumber() {
		return PortNumber;
	}
	public void setPortNumber(String portNumber) {
		PortNumber = portNumber;
	}
	public int getBusID() {
		return BusID;
	}
	public void setBusID(int busID) {
		BusID = busID;
	}
	public int getIsProgrammable() {
		return IsProgrammable;
	}
	public void setIsProgrammable(int isProgrammable) {
		IsProgrammable = isProgrammable;
	}
	public int getIsConnected() {
		return IsConnected;
	}
	public void setIsConnected(int isConnected) {
		IsConnected = isConnected;
	}

	@Override
	public String toString() {
		return "Device [ID=" + ID + ", TypeID=" + TypeID + ", TypeName=" + TypeName + ", ClassID=" + ClassID
				+ ", ClassName=" + ClassName + ", Name=" + Name + ", Description=" + Description + ", MicrogridID="
				+ MicrogridID + ", MicrogridName=" + MicrogridName + ", Vendor=" + Vendor + ", Model=" + Model
				+ ", Location=" + Location + ", IPAdress=" + IPAdress + ", PortNumber=" + PortNumber + ", BusID="
				+ BusID + ", IsProgrammable=" + IsProgrammable + ", IsConnected=" + IsConnected + "]";
	}
	
	
	
	
}
