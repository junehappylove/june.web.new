/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.dao.back.common;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.common.TreeDto;
import com.june.dto.back.login.ButtonDto;

/**
 * @Description: 共通业务dao
 * @author caiyang
 * @date 2016年3月7日 下午2:45:57
 * @version V1.0
 */
public interface CommonDao extends BaseDao<TreeDto> {

	/**
	 * 获取故障等级下拉数据
	 * 
	 * @return
	 * @date 2016年5月16日 上午10:12:08
	 * @writer wjw.happy.love@163.com
	 */
	List<TreeDto> getErrorLevel();

	/**
	 * 获取一级组织
	 * 
	 * @return
	 * @date 2016年5月16日 上午10:12:32
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getFirstOrg();

	/**
	 * 获取子组织
	 * 
	 * @param treeDto
	 * @return
	 * @date 2016年5月16日 上午10:12:39
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getSubOrg(TreeDto treeDto);

	/**
	 * 获取角色
	 * 
	 * @return
	 * @date 2016年5月16日 上午10:12:47
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getRole();

	/**
	 * 根据用户角色以及页面访问的url获取该页面所有有权限的按钮
	 * 
	 * @param buttonDto
	 * @return
	 * @date 2016年5月16日 上午10:12:55
	 * @writer wjw.happy.love@163.com
	 */
	public List<ButtonDto> getFunctionByRole(ButtonDto buttonDto);
}
