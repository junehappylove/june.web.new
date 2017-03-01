/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.service.back.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.common.TreeDto;
import com.june.dao.back.common.CommonDao;
import com.june.dto.back.login.ButtonDto;

/**
 * 
 * 共通业务sevice <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月16日 上午10:14:39
 */
@Service
public class CommonService extends BaseService<CommonDao, TreeDto> {

	@Autowired
	private CommonDao commonDao;

	/**
	 * 获取故障等级下拉数据
	 * 
	 * @return
	 * @date 2016年5月16日 上午10:12:08
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getErrorLevel(){
		return commonDao.getErrorLevel();
	}
	
	/**
	 * 获取组织树
	 * 
	 * @return
	 * @date 2016年5月16日 上午10:14:29
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getOrgTree() {
		// 获取一级组织
		List<TreeDto> firstOrg = commonDao.getFirstOrg();
		firstOrg = getSubOrg(firstOrg);
		return firstOrg;
	}

	/**
	 * 根据以及组织递归获取二级组织
	 * 
	 * @param firstOrg
	 * @return
	 * @date 2016年5月16日 上午10:14:20
	 * @writer wjw.happy.love@163.com
	 */
	private List<TreeDto> getSubOrg(List<TreeDto> firstOrg) {
		if (firstOrg != null) {
			for (int i = 0; i < firstOrg.size(); i++) {
				firstOrg.get(i).setChildren(commonDao.getSubOrg(firstOrg.get(i)));
				getSubOrg(firstOrg.get(i).getChildren());
			}
		}
		return firstOrg;

	}

	/**
	 * 获取所有的角色
	 * 
	 * @return
	 * @date 2016年5月16日 上午10:14:12
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getRole() {
		return commonDao.getRole();
	}

	/**
	 * 根据用户角色以及页面访问的url获取该页面所有有权限的按钮
	 * 
	 * @param buttonDto
	 * @return
	 * @date 2016年5月16日 上午10:13:56
	 * @writer wjw.happy.love@163.com
	 */
	@Cacheable(value = "getFunctionByRole", key = "#root.args[0]")
	public List<ButtonDto> getFunctionByRole(ButtonDto buttonDto) {
		return commonDao.getFunctionByRole(buttonDto);
	}
}
