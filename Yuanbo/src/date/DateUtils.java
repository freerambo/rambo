package date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @param args
 * @description 处理时间的类
 * @version currentVersion
 * @author Rambo
 * @createtime Aug 26, 2013 1:46:46 PM
 */
public class DateUtils {

	/**
	 * default time format yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEAFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_WITH_T = "yyyy-MM-ddTHH:mm:ss";
	public static final String DATE_FORMAT_WITHOUT_TIME = "yyyy-MM-dd";
	
	public static final String DATE_FORMAT_WITH_HOUR = "yyyy-MM-dd HH:00:00";
//	public static final String START_DATE_FORMAT_OF_DAY = "yyyy-MM-dd 01:00:00";
	public static final String END_DATE_FORMAT_OF_DAY = "yyyy-MM-dd 00:00:00";


	
	/**
	 * 
	 * @function: convert Timestamp to Date
	 * @param time
	 * @return
	 * @author: zhuyuanbo    16 Jan, 2015 5:19:15 pm
	 */
	public static Date timeStampToDate(Timestamp time){
		return new Date(time.getTime());
	}
	/**
	 * 
	 * @function: get Date from Long
	 * @param i
	 * @return
	 * @author: zhuyuanbo    14 Nov, 2014 2:59:14 pm
	 */
	public static Date getDatefromLong(Long i){
		return new Date(i);	
	}
	
	/**
	 * 
	 * @function: 时间转换字符串 default yyyy-mm-dd hh:mm
	 * @param date
	 * @return 返回字符型日期
	 * @author: Rambo Zhu 2010-3-24 上午11:38:21
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEAFAULT_DATE_FORMAT);
	}

	/**
	 * 
	 * @param date
	 * @param formatStr
	 * @return 返回字符型日期
	 * @description 时间转换字符串
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 27, 2013 11:21:58 AM
	 */
	public static String dateToString(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}
	

	/**
	 * 
	 * @param s
	 * @return 格式化输出日期
	 * @description 字符串转换时间
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 27, 2013 11:25:38 AM
	 */
	public static Date stringToDate(String s) {
		return stringToDate(s, DEAFAULT_DATE_FORMAT);
	}

	/**
	 * 
	 * @function: getDateByStringAddTime
	 * @param s
	 * @param time
	 * @return
	 * @author: zhuyuanbo    12 Dec, 2014 1:47:59 pm
	 */
	public static Date getDateByStringAddTime(String s, long time) {
		Date dt = new Date(DateUtils.stringToDate(s).getTime() + time);
		return dt;
	}
	
	/**
	 * 
	 * @function: get Date String ByStringAddTime
	 * @param s
	 * @param time
	 * @return
	 * @author: zhuyuanbo    12 Dec, 2014 1:48:07 pm
	 */
	public static String getByStringAddTime(String s, long time) {
		return DateUtils.dateToString(getDateByStringAddTime(s,time));
	}
	
	/**
	 * 
	 * @function: get full time string with 00:00:00
	 * @param s
	 * @return
	 * @author: zhuyuanbo    12 Jan, 2015 11:31:23 am
	 */
	public static String getFullTimeString(String s){
		return s.trim() + " 00:00:00";
	}
	
	/**
	 * 
	 * @param s
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 * @description 字符串转换时间
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 27, 2013 11:21:27 AM
	 */
	public static Date stringToDate(String s, String formatStr) {
//		s = StringUtils.trimToNull(s);
		if (s == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		// "yyyy-MM-dd");
		// "dd/MM/yyyy");
		try {
			return format.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	
	/**
	 * 
	 * @function: get Date With only Hour
	 * @param currentTime
	 * @return
	 * @author: zhuyuanbo    5 Nov, 2014 3:39:58 pm
	 */
	public static Date getDateWithHour(Date currentTime) {

		return stringToDate(dateToString(currentTime, DATE_FORMAT_WITH_HOUR));
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(java.util.Date date) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		// int year = date.getYear();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		// int year = gc.get( Calendar.YEAR );
		return isLeapYear(gc);
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(java.util.Calendar gc) {

		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 判断指定日期的年份是否是闰年 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
	 * 3.能被4整除同时能被100整除则不是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(int year) {
		/**
		 * 
		 */
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 得到指定日期的后一个工作日
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的后一个工作日
	 */
	public static java.util.Date getNextWeekDay(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期五，则加3天 2.如果date是星期六，则加2天 3.否则加1天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月的最后一天
	 */
	public static java.util.Date getLastDayOfNextMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getLastDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextMonth(gc.getTime()));
		gc.setTime(getLastDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的最后一天
	 */
	public static java.util.Date getLastDayOfNextWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getLastDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextWeek(gc.getTime()));
		gc.setTime(getLastDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的最后一天
	 */
	public static java.util.Date getLastDayOfWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天
		 * 4.如果date是星期三，则加3天 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天
		 * 7.如果date是星期六，则加0天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}
	
	/**
	 * 取得指定日期的所处星期的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的最后一天
	 */
	public static java.util.Date getLastDayOfWeek_old(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则加6天 2.如果date是星期一，则加5天 3.如果date是星期二，则加4天
		 * 4.如果date是星期三，则加3天 5.如果date是星期四，则加2天 6.如果date是星期五，则加1天
		 * 7.如果date是星期六，则加0天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 1);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 0);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期
	 */
	public static java.util.Date getNextWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加7天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	/**
	 * 
	 * @function: getPreviousWeek
	 * @param date
	 * @return
	 * @author: zhuyuanbo    21 Nov, 2014 4:12:23 pm
	 */
	public static java.util.Date getPreviousWeek(java.util.Date date) {

		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -7);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月的第一天
	 */
	public static java.util.Date getFirstDayOfNextMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextMonth设置当前时间 2.以1为基础，调用getFirstDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextMonth(gc.getTime()));
		gc.setTime(getFirstDayOfMonth(gc.getTime()));
		return gc.getTime();
	}
	
	/**
	 * 
	 * @function: getStartofHour
	 * @param date
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 5:02:54 pm
	 */
	public static Date getStartMinuteOfHour(Date date){
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.MINUTE, 1);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND,0);
		
		return gc.getTime();
	}
	
	
	/**
	 * 
	 * @function: getEndMinuteofHour
	 * @param date
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 5:02:54 pm
	 */
	public static Date getEndMinuteOfHour(Date date){
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND,0);
		
		return gc.getTime();
	}
	
	
	/**
	 * 
	 * @function: getStartHourOftheDay
	 * @param date
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 5:16:40 pm
	 */
	public static Date getStartHourOftheDay(Date date){
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 1);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND,0);
		return gc.getTime();
	}
	
	/**
	 * 
	 * @function: getEndHourOftheDay
	 * @param date
	 * @return
	 * @author: zhuyuanbo    10 Nov, 2014 5:15:38 pm
	 */
	public static Date getEndHourOftheDay(Date date){
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND,0);
		return gc.getTime();
	}


	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的第一天
	 */
	public static java.util.Date getFirstDayOfMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.设置为1号
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个星期的第一天
	 */
	public static java.util.Date getFirstDayOfNextWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.调用getNextWeek设置当前时间 2.以1为基础，调用getFirstDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(getNextWeek(gc.getTime()));
		gc.setTime(getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * 取得指定日期的下一个月
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一个月
	 */
	public static java.util.Date getNextMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期的月份加1
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}
	
	public static java.util.Date getPreviousMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期的月份加1
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -1);
		return gc.getTime();
	}

	/**
	 * get datetime of next hour for given datetime
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的next hour
	 */
	public static java.util.Date getNextHour(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加1next hour
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.HOUR_OF_DAY, 1);
		return gc.getTime();
	}
	
	/**
	 * 
	 * @function: getPreviousHour 
	 * @param date
	 * @return PreviousHour
	 * @author: zhuyuanbo    10 Nov, 2014 8:20:18 pm
	 */
	public static java.util.Date getPreviousHour(java.util.Date date) {

		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.HOUR_OF_DAY, -1);
		return gc.getTime();
	}
	
	/**
	 * 
	 * @function: getPreviousDay 
	 * @param date
	 * @return PreviousHour
	 * @author: zhuyuanbo    10 Nov, 2014 8:20:18 pm
	 */
	public static java.util.Date getPreviousDay(java.util.Date date) {

		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}
	
	
	

	/**
	 * 取得指定日期的下一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的下一天
	 */
	public static java.util.Date getNextDay(java.util.Date date) {
		/**
		 * 详细设计： 1.指定日期加1天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	/**
	 * get the first day of the week for given date
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的第一天
	 */
	public static java.util.Date getFirstDayOfWeek(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则减6天 2.如果date是星期一，则减0天 3.如果date是星期二，则减1天
		 * 4.如果date是星期三，则减2天 5.如果date是星期四，则减3天 6.如果date是星期五，则减4天
		 * 7.如果date是星期六，则减5天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, -6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -5);
			break;
		}
		return gc.getTime();
	}
	
	/**
	 * 取得指定日期的所处星期的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处星期的第一天
	 */
	public static java.util.Date getFirstDayOfWeek_old(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则减0天 2.如果date是星期一，则减1天 3.如果date是星期二，则减2天
		 * 4.如果date是星期三，则减3天 5.如果date是星期四，则减4天 6.如果date是星期五，则减5天
		 * 7.如果date是星期六，则减6天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc.getTime();
	}

	/**
	 * 取得指定日期的所处月份的最后一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的最后一天
	 */
	public static java.util.Date getLastDayOfMonth(java.util.Date date) {
		/**
		 * 详细设计： 1.如果date在1月，则为31日 2.如果date在2月，则为28日 3.如果date在3月，则为31日
		 * 4.如果date在4月，则为30日 5.如果date在5月，则为31日 6.如果date在6月，则为30日
		 * 7.如果date在7月，则为31日 8.如果date在8月，则为31日 9.如果date在9月，则为30日
		 * 10.如果date在10月，则为31日 11.如果date在11月，则为30日 12.如果date在12月，则为31日
		 * 1.如果date在闰年的2月，则为29日
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// 检查闰年
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc.getTime();
	}

	/**
	 * 
	 * @param args
	 * @description 用于测试时间方法
	 * @version currentVersion
	 * @author Rambo
	 * @createtime Aug 28, 2013 11:08:16 AM
	 */
	public static void main(String[] args) {
/*		System.out.println(isLeapYear(2000));
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		System.out.println(gc);

		System.out.println(new Date().after(getNextHour(DateUtils
				.stringToDate("2014-10-29 15:00:00"))));
		System.out.println(getDateWithHour(new Date()));*/
//		System.out.println(stringToDate("2014-10-29",DATE_FORMAT_WITHOUT_TIME));
//		System.out.println(dateToString(stringToDate("2014-10-29 10:00:00",START_DATE_FORMAT_OF_DAY)));
//		
//		System.out.println(stringToDate("2014-10-29 10:11:12",DATE_FORMAT_WITHOUT_TIME));
//		System.out.println(DateUtils.getNextHour(stringToDate("2014-10-29 10:11:12",DATE_FORMAT_WITHOUT_TIME)));
//		
//		System.out.println(DateUtils.getNextDay(stringToDate("2014-10-29 10:11:12",DATE_FORMAT_WITHOUT_TIME)));
//		
		
		System.out.println(
		DateUtils.getFirstDayOfWeek(DateUtils.stringToDate("2014-10-29 00:00:00")));
		
		System.out.println(
				DateUtils.getLastDayOfWeek(DateUtils.stringToDate("2014-10-29 00:00:00")));
		
		
		System.out.println(getPreviousHour(new Date()));
//		jodaTest();
	}

/*	static void jodaTest() {

		DateTime dt = new DateTime(
				DateUtils.stringToDate("2014-10-29 15:00:00"));

		System.out.println(dt + " , "
				+ dt.minusHours(-1).toString(DEAFAULT_DATE_FORMAT));

	}*/

	// 获取属性 年月日时分秒
	Calendar now = Calendar.getInstance();
	int year = now.get(Calendar.YEAR);
	int date = now.get(Calendar.DAY_OF_MONTH);
	int month = now.get(Calendar.MONTH) + 1; // 月份需加1
	int hour = now.get(Calendar.HOUR);
	int min = now.get(Calendar.MINUTE);
	int sec = now.get(Calendar.SECOND);

}
