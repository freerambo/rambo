/**  
* @title ObjectStatus.java  
* @package com.rambo.common.params  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 24, 2015 7:17:52 PM  
* @version currentVersion  
*/
package com.rambo.common.params;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 24, 2015 7:17:52 PM
 */

public class ObjectStatus {

	public static final Map<String, String> STATUS = Maps.newHashMap();

	static {
		STATUS.put("1", "enable");
		STATUS.put("0", "disabled");
	}
	
	public static final Map<String, String> TYPES = Maps.newHashMap();

	static {
		TYPES.put("1", "External");
		TYPES.put("0", "Internal");
	}

	public static final Map<String, String> USER_STATUS = Maps.newHashMap();

	static {
		USER_STATUS.put("0", "disabled");
		USER_STATUS.put("1", "active");
		USER_STATUS.put("2", "registered");
	}

	public static final Map<String, String> PUBLISH_STATUS = Maps.newHashMap();

	static {
		PUBLISH_STATUS.put("0", "disabled");
		PUBLISH_STATUS.put("1", "active");
		PUBLISH_STATUS.put("2", "draft");
	}
	
	
	public static final Map<String, String> PROJECT_TYPES = Maps.newHashMap();

	static {
		PROJECT_TYPES.put("0", "ICT-Core");
		PROJECT_TYPES.put("1", "Co-NTU");
		PROJECT_TYPES.put("2", "External");
		PROJECT_TYPES.put("3", "Legacy");
	}
	
	
	

}
