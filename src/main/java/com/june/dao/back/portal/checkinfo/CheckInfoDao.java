/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dao.back.portal.checkinfo;

import com.june.common.BaseDao;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;

/**  
* @Description: 审核资讯用dao
* @author caiyang
* @date 2015年11月3日 下午2:14:00 
* @version V1.0  
 */
public interface CheckInfoDao extends BaseDao<ReleaseInfoDto>{

	/**   
	 * @Description: 审核通过操作
	 * @author	caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void checkPass(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 驳回操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void checkBack(ReleaseInfoDto releaseInfoDto);
}
