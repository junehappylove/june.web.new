/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.service.back.portal.myinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.portal.myinfo.MyInfoDao;
import com.june.dto.back.portal.ReleaseInfoDto;

/**  
* @Description: 我的资讯用service
* @author caiyang
* @date 2015年11月2日 下午3:18:14 
* @version V1.0  
 */
@Service
public class MyInfoService extends BaseService<MyInfoDao, ReleaseInfoDto>{

	/**
	 * 我的资讯用dao注入
	 */
	@Autowired
	MyInfoDao myInfoDao;
	
	/**   
	 * @Description: 表格数据检索
	 * @author caiyang
	 * @param: @param releaseInfoDto
	 * @param: @return      
	 * @return: ReleaseInfoDto      
	 * @throws   
	 */
	public ReleaseInfoDto search(ReleaseInfoDto releaseInfoDto)
	{
		List<ReleaseInfoDto> list = myInfoDao.getPageList(releaseInfoDto);
		//for (int i = 0; i < list.size(); i++) {
		//	list.get(i).setContentType(SysCodeUtil.getSysCodeName("contentType", list.get(i).getContentType()));
		//}
		int total = myInfoDao.getTotal(releaseInfoDto);
		releaseInfoDto.setRows(list);
		releaseInfoDto.setTotal(total);
		return releaseInfoDto;
	}
	
	/**   
	 * @Description: 点击行提交时进行资讯提交操作
	 * @author  caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void submitRow(ReleaseInfoDto releaseInfoDto)
	{
		myInfoDao.submitRow(releaseInfoDto);
	}
	
	/**   
	 * @Description: 根据id获取资讯信息
	 * @author caiyang
	 * @param: @param releaseInfoDto
	 * @param: @return      
	 * @return: ReleaseInfoDto      
	 * @throws   
	 */
	public ReleaseInfoDto getInfoById(ReleaseInfoDto releaseInfoDto)
	{
		return myInfoDao.getInfoById(releaseInfoDto);
	}
	
	/**   
	 * @Description: 删除未提交的资讯
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void deleteRow(ReleaseInfoDto releaseInfoDto)
	{
		myInfoDao.deleteRow(releaseInfoDto);
	}
}
