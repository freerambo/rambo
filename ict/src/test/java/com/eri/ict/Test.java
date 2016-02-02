package com.eri.ict;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.google.common.collect.Lists;

/**
 * 
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 12 Nov, 2014 10:08:33 pm
 */
public class Test {

	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(Test.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");


	}

	
}
