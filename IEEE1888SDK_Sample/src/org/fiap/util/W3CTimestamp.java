/*
 * Copyright (c) 2013 Hideya Ochiai, the University of Tokyo,  All rights reserved.
 * 
 * Permission of redistribution and use in source and binary forms, 
 * with or without modification, are granted, free of charge, to any person 
 * obtaining the copy of this software under the following conditions:
 * 
 *  1. Any copies of this source code must include the above copyright notice,
 *  this permission notice and the following statement without modification 
 *  except possible additions of other copyright notices. 
 * 
 *  2. Redistributions of the binary code must involve the copy of the above 
 *  copyright notice, this permission notice and the following statement 
 *  in documents and/or materials provided with the distribution.  
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.fiap.util;/*

 * 
 * Permission of redistribution and use in source and binary forms, 
 * with or without modification, are granted, free of charge, to any person 
 * obtaining the copy of this software under the following conditions:
 * 
 *  1. Any copies of this source code must include the above copyright notice,
 *  this permission notice and the following statement without modification 
 *  except possible additions of other copyright notices. 
 * 
 *  2. Redistributions of the binary code must involve the copy of the above 
 *  copyright notice, this permission notice and the following statement 
 *  in documents and/or materials provided with the distribution.  
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Calendar;

/**
 * @author Hideya Ochiai, The University of Tokyo
 * Created: before 2008-01-01
 */
public class W3CTimestamp {

	public static java.util.Calendar parse(String w3cTimestamp){
		
		int year=Integer.parseInt(w3cTimestamp.substring(0,4));
		int month=Integer.parseInt(w3cTimestamp.substring(5,7));
		int day=Integer.parseInt(w3cTimestamp.substring(8,10));
		int hour=Integer.parseInt(w3cTimestamp.substring(11,13));
		int minute=Integer.parseInt(w3cTimestamp.substring(14,16));
		int second=Integer.parseInt(w3cTimestamp.substring(17,19));
		int milisec=Integer.parseInt(w3cTimestamp.substring(20,23));
		int tz=0;
		String offset=w3cTimestamp.substring(27);
		
		String[] offsets=offset.split("\\-|\\+|:");
		
		if(offsets.length==3){
			
			if(offset.charAt(0)=='+'){
				tz=Integer.parseInt(offsets[1])*3600000+Integer.parseInt(offsets[2])*60000;
			}else if(offset.charAt(0)=='-'){
				tz=-Integer.parseInt(offsets[1])*3600000-Integer.parseInt(offsets[2])*60000;
			}

		}else if(offsets.length==2){

			if(offset.charAt(0)=='+'){
				tz=Integer.parseInt(offsets[1])*3600000;
			}else if(offset.charAt(0)=='-'){
				tz=-Integer.parseInt(offsets[1])*3600000;
			}
			
		}else if(offsets.length==1){
			tz=Integer.parseInt(offsets[0])*3600000;
		}
		
		Calendar cal=Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, milisec);
		cal.set(Calendar.ZONE_OFFSET, tz);
		
		return cal;
	}
	
	public static String parseToDateString(String w3cTimestamp){
		return w3cTimestamp.replace('T', ' ');
	}
	
	public static String toString(java.sql.Timestamp timestamp, java.util.TimeZone tz){
		
		try{
			java.util.Calendar cal=java.util.Calendar.getInstance();
			cal.setTimeZone(tz);
			cal.setTime(timestamp);
			//cal.setTimeInMillis(timestamp.getTime());
		
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			int day=cal.get(Calendar.DAY_OF_MONTH);
			int hour=cal.get(Calendar.HOUR_OF_DAY);
			int minute=cal.get(Calendar.MINUTE);
			int second=cal.get(Calendar.SECOND);
			int nanosec=timestamp.getNanos();
			int tzOffset=cal.get(Calendar.ZONE_OFFSET);
		
			return privateToString(year,month,day,hour,minute,second,nanosec,tzOffset);
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return null;
	}
	
	public static String toString(java.util.Calendar timestamp){

		try{
			Calendar cal=timestamp;
		
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			int day=cal.get(Calendar.DAY_OF_MONTH);
			int	hour=cal.get(Calendar.HOUR_OF_DAY);
			int minute=cal.get(Calendar.MINUTE);
			int second=cal.get(Calendar.SECOND);
			int milisec=cal.get(Calendar.MILLISECOND);
			int tzOffset=cal.get(Calendar.ZONE_OFFSET);
		
			return privateToString(year,month,day,hour,minute,second,milisec*1000000,tzOffset);
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return null;

	}
	
	public static String toString(long time,java.util.TimeZone tz){

		try{
			Calendar cal=Calendar.getInstance(tz);
			cal.setTimeInMillis(time);
			
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			int day=cal.get(Calendar.DAY_OF_MONTH);
			int hour=cal.get(Calendar.HOUR_OF_DAY);
			int minute=cal.get(Calendar.MINUTE);
			int second=cal.get(Calendar.SECOND);
			int milisec=cal.get(Calendar.MILLISECOND);
			int tzOffset=cal.get(Calendar.ZONE_OFFSET);

			return privateToString(year,month,day,hour,minute,second,milisec*1000000,tzOffset);
		}
		catch(Exception e){
		//	e.printStackTrace();
		}
		return null;
		
	}
	
	private static String privateToString(int year,int month,int day,int hour,int minute,int second,int nanosec,int tzOffset){
		
		StringBuffer sb=new StringBuffer(32);
		
		sb.append(Integer.toString(year));
		sb.append('-');
		if(month<10){ sb.append('0'); }
		sb.append(Integer.toString(month));
		sb.append('-');
		if(day<10){ sb.append('0'); }
		sb.append(Integer.toString(day));
		sb.append('T');
		if(hour<10){ sb.append('0'); }
		sb.append(Integer.toString(hour));
		sb.append(':');
		if(minute<10){ sb.append('0'); }
		sb.append(Integer.toString(minute));
		sb.append(':');
		if(second<10){ sb.append('0'); }
		sb.append(Integer.toString(second));
		sb.append('.');
		
		String nanosec_str=Integer.toString(nanosec/100);
		int i,length;
		length=7-nanosec_str.length();
		for(i=0;i<length;i++){ sb.append('0'); }
		sb.append(nanosec_str);
		
		if(tzOffset>=0){
			int tzOffset_hour=tzOffset/3600000;
			int tzOffset_minute=(tzOffset/60000)%60;
			sb.append('+');
			if(tzOffset_hour<10){ sb.append('0'); }
			sb.append(Integer.toString(tzOffset_hour));
			sb.append(':');
			if(tzOffset_minute<10){ sb.append('0'); }
			sb.append(Integer.toString(tzOffset_minute));
		}else{
			int tzOffset_hour=(-tzOffset)/3600000;
			int tzOffset_minute=((-tzOffset)/60000)%60;
			sb.append('-');
			if(tzOffset_hour<10){ sb.append('0'); }
			sb.append(Integer.toString(tzOffset_hour));
			sb.append(':');
			if(tzOffset_minute<10){ sb.append('0'); }
			sb.append(Integer.toString(tzOffset_minute));
		}
		return sb.toString();
	}
}
