package com.rambo.erian.service.quartz;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rambo.common.params.TimeConstants;
import com.rambo.common.utils.DateUtils;

public class Test 
{
    public static void main( String[] args ) throws Exception
    {
//    	new ClassPathXmlApplicationContext("schedule/applicationContext-quartz-common.xml");
//    	.stringToDate("2014-10-29 12:00:00")
//    	System.out.println(DateUtils.getDateWithFormat(new Date(), "yyyy-MM-dd 01:00:00"));
    	
    	System.out.println(DateUtils.getStartMinuteOfHour(new Date()));
    	System.out.println(DateUtils.getEndMinuteOfHour(new Date()));

    	
    	System.out.println(DateUtils.getStartHourOftheDay(new Date()));
    	System.out.println(DateUtils.getEndHourOftheDay(new Date()));
    	
		Date date = new Date();

    	Date end = new Date(date.getTime() - 100);
    	System.out.println(end.before(date));
    	
    	
    	
    }
}