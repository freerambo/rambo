/**  
* @title IUploadFileService.java  
* @package com.eri.ict.service  
* @description   
* @author Yuanbo Zhu  
* @createtime Nov 20, 2015 4:34:40 PM  
* @version currentVersion  
*/
package com.eri.ict.service.uploadFile;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eri.ict.entity.UploadFile;

/**
 * @description
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 20, 2015 4:34:40 PM
 */

public interface IUploadFileService {

	/**
	 * 
	 * @param id
	 * @return
	 * @description get UploadFile By given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:19 PM
	 */
	public UploadFile getUploadFile(Long id);

	/**
	 * 
	 * @param entity
	 * @description save given UploadFile
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:32 PM
	 */
	public void saveUploadFile(UploadFile entity);

	/**
	 * 
	 * @param id
	 * @description deleted UploadFile by given Id
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:45 PM
	 */
	public void deleteUploadFile(Long id);

	/**
	 * 
	 * @param file  
	 * @description   deleteUploadFile by File
	 * @version currentVersion  
	 * @author Yuanbo Zhu  
	 * @createtime Nov 23, 2015 4:22:37 PM
	 */
	public void deleteUploadFile(UploadFile file) ;

	
	/**
	 * 
	 * @return
	 * @description Retrieve All UploadFiles
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:37:57 PM
	 */
	public List<UploadFile> getAllUploadFile();

	/**
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 * @description retrieve page results for uploadFiles
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 20, 2015 4:38:19 PM
	 */
	public Page<UploadFile> getUploadFileByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType);

}
