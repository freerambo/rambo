package com.rambo.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springside.modules.mapper.JsonMapper;

import com.google.common.collect.Lists;
import com.rambo.common.utils.DateUtils;
/**
 * 
 * @author User1 Test
 * testing
 * 
 * 
 *
 */
public class Test {
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();
	public static void main(String[] args) {
		
//		List<String> stringList = Lists.newArrayList("A", "B", "C");
//		String listString = mapper.toJson(stringList);
//		System.out.println(listString);
//		JSONDemo jd = new JSONDemo();
//		String s = mapper.toJson(jd);
//		System.out.println(s);
		// TODO Auto-generated method stub
//		System.out.println(DateUtils.getDatefromLong(1415898000000L));
//		
//		Date dt = DateUtils.stringToDate("2014-11-01 00:00:00");
//		
//		dt = new Date(dt.getTime() + 1800 * 1 * 1000);
//		
//		System.out.println(DateUtils.dateToString(dt));
		System.out.println(DateUtils.dateToString(DateUtils.stringToDate("2014-11-01")));
	}
}

class JSONDemo implements Serializable{
	String name = "hello";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	ArrayList x = null;
	ArrayList y = null;
	public ArrayList getX() {
		return Lists.newArrayList("1","2","3","4");
	}
	public void setX(ArrayList x) {
		this.x = x;
	}
	public ArrayList getY() {
		return Lists.newArrayList("1","2","3","4");
	}
	public void setY(ArrayList y) {
		this.y = y;
	}
	
	
	
	
}