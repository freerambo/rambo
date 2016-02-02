package date;

import java.util.Date;

/**
 * 
 * @description convert the long to formated time  
 * @version currentVersion(1.0)  
 * @author Yuanbo Zhu  
 * @createtime Sep 8, 2015 3:56:16 PM
 */
public class UTCConvertor {
	
	
	public static String convertUTCtoDate(long lt){
		
		Date date = new Date(lt);
		
		return DateUtils.dateToString(date);
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = convertUTCtoDate(1434981599*1000L);
//		s = convertUTCtoDate(new Date().getTime());
		
		System.out.println(s);
		
		s = convertUTCtoDate(1222387200000L);
		
		System.out.println(s);
		
	}

}
