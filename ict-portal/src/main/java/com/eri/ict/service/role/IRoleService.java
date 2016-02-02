/**  
* @title IRoleService.java  
* @package com.eri.ict.service  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 20, 2015 5:17:06 PM  
* @version currentVersion  
*/
package com.eri.ict.service.role;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eri.ict.entity.Role;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 5:17:06 PM
 */

public interface IRoleService {

	/**
	 * 
	 * @param id
	 * @return
	 * @description get Role By given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:19 PM
	 */
	public Role getRole(Long id);

	/**
	 * 
	 * @param entity
	 * @description save given Role
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:32 PM
	 */
	public void saveRole(Role entity);

	/**
	 * 
	 * @param id
	 * @description deleted Role by given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:45 PM
	 */
	public void deleteRole(Long id);

	/**
	 * 
	 * @return
	 * @description Retrieve All Roles
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:57 PM
	 */
	public List<Role> getAllRole();

	/**
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @description retrieve page results for roles
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:38:19 PM
	 */
	public Page<Role> getRoleByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);

}
