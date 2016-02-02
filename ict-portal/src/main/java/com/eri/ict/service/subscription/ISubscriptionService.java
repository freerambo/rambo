/**  
* @title ISubscriptionService.java  
* @package com.eri.ict.service  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 20, 2015 4:34:40 PM  
* @version currentVersion  
*/
package com.eri.ict.service.subscription;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eri.ict.entity.Subscription;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 4:34:40 PM
 */

public interface ISubscriptionService {

	/**
	 * 
	 * @param id
	 * @return
	 * @description get Subscription By given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:19 PM
	 */
	public Subscription getSubscription(Long id);

	/**
	 * 
	 * @param entity
	 * @description save given Subscription
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:32 PM
	 */
	public void saveSubscription(Subscription entity);

	/**
	 * 
	 * @param id
	 * @description deleted Subscription by given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:45 PM
	 */
	public void deleteSubscription(Long id);

	/**
	 * 
	 * @return
	 * @description Retrieve All Subscriptions
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:57 PM
	 */
	public List<Subscription> getAllSubscription();

	/**
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @description retrieve page results for subscriptions
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:38:19 PM
	 */
	public Page<Subscription> getSubscriptionByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType);

}
