/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dao.back.portal.releaseinfo;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.portal.ReleaseInfoDto;

/**  
* @Description: 发布咨询用dao
* @author caiyang
* @date 2015年10月30日 上午9:48:49 
* @version V1.0  
 */
public interface ReleaseInfoDao extends BaseDao<ReleaseInfoDto>{

	/**   
	 * @Description: 获取所有的咨询模块
	 * @author caiyang
	 * @param: @return      
	 * @return: List<ReleaseInfoDto>      
	 * @throws   
	 */
	List<ReleaseInfoDto> getChannels();
	
	/**   
	 * @Description: 获取最新的文章id
	 * @author caiyang
	 * @param: @return      
	 * @return: int      
	 * @throws   
	 */
	int getMaxContentId();
	
	/**   
	 * @Description: content表插入数据
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void insertContent(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: content_txt表插入数据
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void insertContentText(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: content表更新
	 * @author	caiyang
 	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void updateContent(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: contentTxt表更新
	 * @author	caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void updateContentTxt(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 未保存直接提交资讯内容content表插入
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void insertSubmitContent(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 未保存直接提交资讯content_txt表插入
	 * @author
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void insertSubmitContentText(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 先进行保存或者修改操作在进行提交 content表操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void updateSubmitContent(ReleaseInfoDto releaseInfoDto);
	
	/**   
	 * @Description: 先进行保存或者修改操作在进行提交 content_txt表操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	void updateSubmitContentTxt(ReleaseInfoDto releaseInfoDto);
}
