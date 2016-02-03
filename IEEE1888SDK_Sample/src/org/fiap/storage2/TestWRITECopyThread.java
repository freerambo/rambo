package org.fiap.storage2;

import org.fiap.common.*;

import org.w3c.dom.Element;

public class TestWRITECopyThread {

	public static void main(String[] args) throws Exception {

		Storage2Admin admin=Storage2Admin.getInstance();
		admin.parseConfigFile("config/storage/storage.xml");
		Element[] writes=admin.getWRITEs();
		WRITECopyThread thread=new WRITECopyThread(writes[0],null);	
		while(true){
			try{
				Thread.sleep(10000);
			}catch(Exception e){}
		}
		
	}
	
}
