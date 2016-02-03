package org.fiap.storage2;

import org.fiap.common.*;

import org.w3c.dom.Element;

public class TestFETCHCopyThread {

	public static void main(String[] args) throws Exception {

		Storage2Admin admin=Storage2Admin.getInstance();
		admin.parseConfigFile("config/storage/storage.xml");
		
		Element[] fetchs=admin.getFETCHs();
		FETCHCopyThread thread=new FETCHCopyThread(fetchs[0],null);
		
		while(true){
			try{
				Thread.sleep(10000);
			}catch(Exception e){}
		}
	}

}
