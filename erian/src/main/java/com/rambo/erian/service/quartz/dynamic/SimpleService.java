package com.rambo.erian.service.quartz.dynamic;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("simpleService")
//@Scope("prototype")
public class SimpleService implements Serializable{
	
	private static final long serialVersionUID = 122323233244334343L;
//	private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);
	int k = 1000;
	public void testMethod(String triggerName){
		//这里执行定时调度业务
		System.out.println("k = " + k++ + " in dynamic"+ new Date());
//		logger.info(triggerName);
	}
	
	public void testMethod2(){
//		logger.info("testMethod2");
	}
}
