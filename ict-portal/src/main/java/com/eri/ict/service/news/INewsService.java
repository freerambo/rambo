/**  
* @title INewsService.java  
* @package com.eri.ict.service  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 20, 2015 5:17:06 PM  
* @version currentVersion  
*/
package com.eri.ict.service.news;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eri.ict.entity.News;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 5:17:06 PM
 */

public interface INewsService {

	/**
	 * 
	 * @param id
	 * @return
	 * @description get News By given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:19 PM
	 */
	public News getNews(Long id);

	/**
	 * 
	 * @param entity
	 * @description save given News
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:32 PM
	 */
	public void saveNews(News entity);

	/**
	 * 
	 * @param id
	 * @description deleted News by given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:45 PM
	 */
	public void deleteNews(Long id);

	/**
	 * 
	 * @return
	 * @description Retrieve All Newss
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:57 PM
	 */
	public List<News> getAllNews();

	/**
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @description retrieve page results for projects
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:38:19 PM
	 */
	public Page<News> getNewsByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType);

}
