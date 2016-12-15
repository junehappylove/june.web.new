/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.shiro.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.june.shiro.dto.Resource;
import com.june.shiro.dto.User;

/**
 * 
 * ShiroUserDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 下午6:10:11
 */
public interface ShiroUserDao {

	public User getUserByUsername(String username);
	
	/**
	 * 根据角色获取对应的menu
	 * @param roleId
	 * @return
	 * @date 2016年6月23日 下午1:39:06
	 * @writer wjw.happy.love@163.com
	 */
	@Cacheable(value="getRoleMenus",key="#root.caches[0].name")
	public List<Resource> getRoleMenus(String roleId);
	
	public Resource getMenubyRoleIdMenuId(Resource resource);
	
	/**
	 * 根据角色获取对应的button
	 * @param roleId
	 * @return
	 * @date 2016年6月23日 下午1:39:38
	 * @writer wjw.happy.love@163.com
	 */
	@Cacheable(value="getRoleButtons",key="#root.caches[0].name")
	public List<Resource> getRoleButtons(String roleId);
}
