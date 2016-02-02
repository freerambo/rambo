/**  
* @title IProjectService.java  
* @package com.eri.ict.service  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 20, 2015 5:17:06 PM  
* @version currentVersion  
*/
package com.eri.ict.service.project;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eri.ict.entity.Project;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 5:17:06 PM
 */

public interface IProjectService {

	/**
	 * 
	 * @param id
	 * @return
	 * @description get Project By given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:19 PM
	 */
	public Project getProject(Long id);
	
	/**
	 * 
	 * @param id
	 * @return  
	 * @description   get top n projects
	 * @version currentVersion  
	 * @author Yuanbo Zhu  
	 * @createtime Dec 2, 2015 4:39:20 PM
	 */
	public List<Project> getTopN(int id);
	/**
	 * 
	 * @param entity
	 * @description save given Project
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:32 PM
	 */
	public void saveProject(Project entity);

	/**
	 * 
	 * @param id
	 * @description deleted Project by given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:45 PM
	 */
	public void deleteProject(Long id);

	/**
	 * 
	 * @return
	 * @description Retrieve All Projects
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:57 PM
	 */
	public List<Project> getAllProject();

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
	public Page<Project> getProjectByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType);

}
