package com.rambo.common.utils;

/**
 * 
 * function description： MeterIDConverter eg. 1 <------> M00000001A
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 Create: 13 Nov, 2014 10:50:06 am
 */
public class MeterIdConverter {
	/**
	 * 
	 * @function: get int id from given string meterId
	 * @param meterId
	 * @return
	 * @author: zhuyuanbo 13 Nov, 2014 10:59:23 am
	 */
	public static int getIntMeterId(String meterId) {
		int id = -1;
		switch (meterId) {
		case "M00000001A":
			id = 1;
			break;
		case "M00000002A":
			id = 2;
			break;
		case "M00000003A":
			id = 3;
			break;
		case "M00000004A":
			id = 4;
			break;
		case "M00000005A":
			id = 5;
			break;
		case "M00000006A":
			id = 6;
			break;
		case "M00000007A":
			id = 7;
			break;
		case "M00000008A":
			id = 8;
			break;
		case "M00000009A":
			id = 9;
			break;
		case "M00000010A":
			id = 10;
			break;
		case "M00000011A":
			id = 11;
			break;
		case "M00000012A":
			id = 12;
			break;
		}
		return id;
	}

	/**
	 * 
	 * @function: get String id from given int meterId
	 * @param meterId
	 * @return
	 * @author: zhuyuanbo 13 Nov, 2014 10:59:41 am
	 */
	public static String getStringMeterId(int meterId) {
		String id = null;
		switch (meterId) {
		case 1:
			id = "M00000001A";
			break;
		case 2:
			id = "M00000002A";
			break;
		case 3:
			id = "M00000003A";
			break;
		case 4:
			id = "M00000004A";
			break;
		case 5:
			id = "M00000005A";
			break;
		case 6:
			id = "M00000006A";
			break;
		case 7:
			id = "M00000007A";
			break;
		case 8:
			id = "M00000008A";
			break;
		case 9:
			id = "M00000009A";
			break;
		case 10:
			id = "M00000010A";
			break;
		case 11:
			id = "M00000011A";
			break;
		case 12:
			id = "M00000012A";
			break;
		}
		return id;
	}

	/**
	 * 
	 * @function: test
	 * @param args
	 * @author: zhuyuanbo 13 Nov, 2014 10:59:53 am
	 */
	public static void main(String args[]) {
		System.out.println(getIntMeterId("M00000002A"));
		for (int i = 0; i < 13; i++) {
			System.out.println(getStringMeterId(i));

		}
	}

}
