/**  
* @title ITeamService.java  
* @package com.eri.ict.service  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 20, 2015 4:34:40 PM  
* @version currentVersion  
*/
package com.eri.ict.service.team;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eri.ict.entity.Team;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 4:34:40 PM
 */

public interface ITeamService {

	/**
	 * 
	 * @param id
	 * @return
	 * @description get Team By given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:19 PM
	 */
	public Team getTeam(Long id);

	/**
	 * 
	 * @param entity
	 * @description save given Team
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:32 PM
	 */
	public void saveTeam(Team entity);

	/**
	 * 
	 * @param id
	 * @description deleted Team by given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:45 PM
	 */
	public void deleteTeam(Long id);

	/**
	 * 
	 * @return
	 * @description Retrieve All Teams
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:57 PM
	 */
	public List<Team> getAllTeam();

	/**
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @description retrieve page results for teams
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:38:19 PM
	 */
	public Page<Team> getTeamByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);

}
