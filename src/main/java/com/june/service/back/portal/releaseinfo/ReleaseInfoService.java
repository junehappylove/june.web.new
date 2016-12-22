/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.service.back.portal.releaseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.june.common.BaseService;
import com.june.dao.back.portal.releaseinfo.ReleaseInfoDao;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;

/**  
* @Description: 发布咨询用service
* @author  caiyang
* @date 2015年10月30日 上午9:41:35 
* @version V1.0  
 */
@Service
public class ReleaseInfoService extends BaseService<ReleaseInfoDao, ReleaseInfoDto>{
	
	/**
	 * 发布咨询用dao注入
	 */
	@Autowired
	private ReleaseInfoDao releaseInfoDao;
	
	/**   
	 * @Description: 获取咨询模块
	 * @author caiyang
	 * @param: @return      
	 * @return: List<ReleaseInfoDto>      
	 * @throws   
	 */
	//@Cacheable(value="CustomerCache")
	public List<ReleaseInfoDto> getChannels()
	{
		return releaseInfoDao.getChannels();
	}
	
	/**   
	 * @Description: 获取最新的contentid
	 * @author
	 * @param: @return      
	 * @return: int      
	 * @throws   
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int getMaxContentId()
	{
		return releaseInfoDao.getMaxContentId();
	}
	
	/**   
	 * @Description: 文章插入操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void saveContent(ReleaseInfoDto releaseInfoDto)
	{
		releaseInfoDao.insertContent(releaseInfoDto);
		releaseInfoDao.insertContentText(releaseInfoDto);
	}
	
	/**   
	 * @Description: 文章修改更新操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void updateContent(ReleaseInfoDto releaseInfoDto)
	{
		releaseInfoDao.updateContent(releaseInfoDto);
		releaseInfoDao.updateContentTxt(releaseInfoDto);
	}
	
	/**   
	 * @Description: 未保存直接进行提交操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void submitDirect(ReleaseInfoDto releaseInfoDto)
	{
		releaseInfoDao.insertSubmitContent(releaseInfoDto);;
		releaseInfoDao.insertSubmitContentText(releaseInfoDto);
	}
	
	/**   
	 * @Description: 先进行保存修改操作在进行提交
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void submitNoDirect(ReleaseInfoDto releaseInfoDto)
	{
		releaseInfoDao.updateSubmitContent(releaseInfoDto);
		releaseInfoDao.updateSubmitContentTxt(releaseInfoDto);
	}
	
}
