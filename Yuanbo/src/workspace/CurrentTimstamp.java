package com.rambo.modules.practice;

import org.springframework.util.StopWatch;

import java.util.Calendar;
import java.util.Date;

/**
 * System.currentTimeMillis() is obviously the most efficient since it does not even create an object, but new Date() is really just a thin wrapper about a long, so it is not far behind. Calendar, on the other hand, is relatively slow and very complex, since it has to deal with the considerably complexity and all the oddities that are inherent to dates and times (leap years, daylight savings, timezones, etc.).
 *
 * It's generally a good idea to deal only with long timestamps or Date objects within your application, and only use Calendar when you actually need to perform date/time calculations, or to format dates for displaying them to the user. If you have to do a lot of this, using Joda Time is probably a good idea, for the cleaner interface and better performance.
 */
public class CurrentTimstamp {

    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start("t1");
        //creating Calendar instance
        Calendar calendar = Calendar.getInstance();
        //Returns current time in millis
        long timeMilli2 = calendar.getTimeInMillis();
        sw.stop();
        System.out.println(timeMilli2 + "\n" + sw.getTotalTimeMillis());
        StopWatch sw2 = new StopWatch();
        sw2.start("t2");
        long timeMilli1 = System.currentTimeMillis(); // native

        sw2.stop();
        System.out.println(timeMilli1 + "\n" + sw2.getTotalTimeMillis());

        new Date();
    }
}

