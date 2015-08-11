package com.rambo.erian.service.quartz;

import java.io.Serializable;
import java.util.Date;

import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.xml.NMSFileReader;

/**
 * 
 * @description job
 * @version currentVersion(1.0)
 * @author Rambo
 * @createtime Aug 22, 2013 5:18:12 PM
 */
public class SystemJobCommon  {


/**
 * daily update 
 */
	public void daily() {
//		System.out.println("daily SystemjobCommon executed...."
//				+ DateUtils.dateToString(new Date()));
		new NMSFileReader().readFile();
		
	}

	public void weekly() {
		System.out.println("weekly SystemjobCommon executed...."
				+ DateUtils.dateToString(new Date()));
	}
	public void monthly() {
		System.out.println("monthly SystemjobCommon executed...."
				+ DateUtils.dateToString(new Date()));
	}
	
}
