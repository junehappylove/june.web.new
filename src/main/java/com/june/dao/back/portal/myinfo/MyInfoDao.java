/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dao.back.portal.myinfo;

import com.june.common.BaseDao;
import com.june.dto.back.portal.ReleaseInfoDto;

/**  
* @Description: 我的资讯用dao
* @author caiyang
* @date 2015年11月2日 下午3:19:36 
* @version V1.0  
 */
public interface MyInfoDao extends BaseDao<ReleaseInfoDto>{

	/**   
	 * @Description: 点击行提进行提交资讯操作
	 * @author	caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void submitRow(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 根据contentid获取资讯信息
	 * @author
	 * @param: @param releaseInfoDto
	 * @param: @return      
	 * @return: ReleaseInfoDto      
	 * @throws   
	 */
	public ReleaseInfoDto getInfoById(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 删除未提交的资讯
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void deleteRow(ReleaseInfoDto releaseInfoDto);
}
