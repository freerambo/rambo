package com.rambo.erian.service.nms;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.google.common.collect.Lists;
import com.rambo.common.params.MeterConstants;
import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;
import com.rambo.common.utils.ListUtils;
import com.rambo.erian.entity.PmWlgDay;
import com.rambo.erian.entity.PmWlgHour;
import com.rambo.erian.entity.PmWlgWeek;
import com.rambo.erian.service.PmWlgDayService;
import com.rambo.erian.service.PmWlgHourService;
import com.rambo.spms.entity.PmWlg;
import com.rambo.spms.service.PmWlgService;

/**
 * 
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 12 Nov, 2014 10:08:33 pm
 */
public class NmsRecordServiceTest {

	private static NmsRecordService service;
	// recommend use in this way for slf4j
	private static Logger log = LoggerFactory.getLogger(NmsRecordServiceTest.class); // 日志打印

	// the entrance of the program
	public static void main(String[] args) {
		// System.setProperty("spring.profiles.active", Profiles.DEVELOPMENT);
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		service = (NmsRecordService) context.getBean("nmsRecordService");
		// test();
//		 addAll();
//		testRegin();
		
		insert();

	}

	/**
	 * test function
	 */
	static void insert() {
		long ts = System.currentTimeMillis();
		service.showAllFile("D:\\erian-works\\database\\2015\\test");
		long te = System.currentTimeMillis();
		System.out.println(te - ts);
	}


}
