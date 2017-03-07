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
 * 发布咨询用service ReleaseInfoService <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月6日 上午12:28:56
 * @version 1.0.0
 */
@Service
public class ReleaseInfoService extends BaseService<ReleaseInfoDao, ReleaseInfoDto> {

	/**
	 * 发布咨询用dao注入
	 */
	@Autowired
	private ReleaseInfoDao releaseInfoDao;

	/**
	 * 获取咨询模块
	 * 
	 * @return
	 * @date 2017年3月6日 上午12:28:43
	 * @writer junehappylove
	 */
	// @Cacheable(value="CustomerCache")
	public List<ReleaseInfoDto> getChannels() {
		return releaseInfoDao.getChannels();
	}

	/**
	 * 获取最新的contentid
	 * 
	 * @return
	 * @date 2017年3月6日 上午12:29:11
	 * @writer junehappylove
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int getMaxContentId() {
		return releaseInfoDao.getMaxContentId();
	}

	/**
	 * 文章插入操作
	 * 
	 * @param releaseInfoDto
	 * @date 2017年3月6日 上午12:29:19
	 * @writer junehappylove
	 */
	public void saveContent(ReleaseInfoDto releaseInfoDto) {
		releaseInfoDao.insertContent(releaseInfoDto);
		releaseInfoDao.insertContentText(releaseInfoDto);
	}

	/**
	 * 文章修改更新操作
	 * 
	 * @param releaseInfoDto
	 * @date 2017年3月6日 上午12:29:26
	 * @writer junehappylove
	 */
	public void updateContent(ReleaseInfoDto releaseInfoDto) {
		releaseInfoDao.updateContent(releaseInfoDto);
		releaseInfoDao.updateContentTxt(releaseInfoDto);
	}

	/**
	 * 未保存直接进行提交操作
	 * 
	 * @param releaseInfoDto
	 * @date 2017年3月6日 上午12:29:34
	 * @writer junehappylove
	 */
	public void submitDirect(ReleaseInfoDto releaseInfoDto) {
		releaseInfoDao.insertSubmitContent(releaseInfoDto);
		;
		releaseInfoDao.insertSubmitContentText(releaseInfoDto);
	}

	/**
	 * 先进行保存修改操作在进行提交
	 * 
	 * @param releaseInfoDto
	 * @date 2017年3月6日 上午12:29:42
	 * @writer junehappylove
	 */
	public void submitNoDirect(ReleaseInfoDto releaseInfoDto) {
		releaseInfoDao.updateSubmitContent(releaseInfoDto);
		releaseInfoDao.updateSubmitContentTxt(releaseInfoDto);
	}

}
