/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.service.back.portal.checkinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.portal.checkinfo.CheckInfoDao;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;

@Service
public class CheckInfoService extends BaseService<CheckInfoDao, ReleaseInfoDto> {
	
	@Autowired
	private CheckInfoDao checkInfoDao;

	/**   
	 * @Description: 表格数据初始化
	 * @author	caiyang
	 * @param: @param releaseInfoDto
	 * @param: @return      
	 * @return: ReleaseInfoDto      
	 * @throws   
	 */
	public ReleaseInfoDto search(ReleaseInfoDto releaseInfoDto)
	{
		List<ReleaseInfoDto> list = checkInfoDao.getPageList(releaseInfoDto);
		//for (int i = 0; i < list.size(); i++) {
			//list.get(i).setContentType(SysCodeUtil.getSysCodeName("contentType", list.get(i).getContentType()));
		//}
		int total = checkInfoDao.getTotal(releaseInfoDto);
		releaseInfoDto.setRows(list);
		releaseInfoDto.setTotal(total);
		return releaseInfoDto;
	}
	
	/**   
	 * @Description: 审核通过操作
	 * @author	caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void checkPass(ReleaseInfoDto releaseInfoDto)
	{
		checkInfoDao.checkPass(releaseInfoDto);
	}
	
	/**   
	 * @Description: 驳回操作
	 * @author caiyang
	 * @param: @param releaseInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void checkBack(ReleaseInfoDto releaseInfoDto)
	{
		checkInfoDao.checkBack(releaseInfoDto);
	}
}
