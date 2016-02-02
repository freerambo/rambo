/**  
* @title FileConstants.java  
* @package com.rambo.common.params  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 23, 2015 3:15:35 PM  
* @version currentVersion  
*/
package com.rambo.common.params;

import java.io.File;
import java.util.HashMap;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 23, 2015 3:15:35 PM
 */

public class FileConstants {

	/**
	 * UPLOAD_PATH
	 */
	public static final String UPLOAD_PATH = System.getProperty("user.dir") + File.separator + "upload";

	/**
	 * file extention
	 */
	public static final HashMap<String, String> extMap = new HashMap<String, String>();

	static {

		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

	}
	
	
	
}
