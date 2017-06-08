/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dao.back.system.base.role;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.june.common.BaseDao;
import com.june.common.TreeDto;
import com.june.common.annotation.DataSource;
import com.june.dto.back.system.base.MenuInfoDto;
import com.june.dto.back.system.base.RoleInfoDto;

/**
 * 角色管理模块用dao
 * RoleDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月23日 下午3:45:10
 * @version 1.0.0
 */
public interface RoleDao extends BaseDao<RoleInfoDto>{

	/**
	 * 根据roleName获取角色信息
	 * @param roleInfoDto
	 * @return RoleInfoDto
	 */
	public RoleInfoDto getRoleByRoleName(RoleInfoDto roleInfoDto);
	
	/**
	 * 删除角色信息
	 * @param roleId
	 */
	public void deleteRoleById(String roleId);
	
	/**
	 * 根据角色获取菜单
	 * @param roleId
	 * @return List<TreeDto>
	 */
	public List<TreeDto> getFirstMenusByRole(String roleId);
	
	/**
	 * 获取子菜单
	 * @param treeDto
	 * @return List<TreeDto>
	 */
	public List<TreeDto> getSubMenu(TreeDto treeDto);
	
	/**
	 * 根据角色获取有权限的子菜单
	 * @param treeDto
	 * @return List<TreeDto>
	 */
	public List<TreeDto> getSubMenuByRole(TreeDto treeDto);
	
	/**
	 * 获取功能
	 * @param treeDto
	 * @return List<TreeDto>
	 */
	public List<TreeDto> getFunctions(TreeDto treeDto);
	
	/**
	 * 根据角色获取有权限的功能
	 * @param treeDto
	 * @return List<TreeDto>
	 */
	public List<TreeDto> getFunctionsByRole(TreeDto treeDto);
	
	/**
	 * 获取菜单
	 * @return List<TreeDto>
	 */
	//@Cacheable(value="getFirstMenus",key="#root.caches[0].name")
	public List<TreeDto> getFirstMenus();
	
	
	/**
	 * 获取该角色所拥有的所有权限
	 * @param roleId
	 * @return List<MenuInfoDto>
	 */
	//@Cacheable(value="getAuthorityofRole",key="#root.caches[0].name")
	public List<MenuInfoDto> getAuthorityofRole(String roleId);
	
	/**
	 * 获取该角色拥有的按钮权限
	 * @param roleId
	 * @return List<MenuInfoDto>
	 */
	//@Cacheable(value="getButtonauthorityOfRole",key="#root.caches[0].name")
	public List<MenuInfoDto> getButtonauthorityOfRole(String roleId);
	
	/**
	 * 给角色赋权限
	 * @param menuInfoDto
	 */
	public void authorityMenu(MenuInfoDto menuInfoDto);
	
	/**
	 * 给角色赋按钮权限
	 * @param menuInfoDto
	 */
	public void authorityButton(MenuInfoDto menuInfoDto);
	
	/**
	 * 给角色删除权限
	 * @param menuInfoDto
	 */
	public void delAuthorityMenu(MenuInfoDto menuInfoDto);

	/**
	 * 给角色按钮权限
	 * @param menuInfoDto
	 */
	public void delAuthorityButton(MenuInfoDto menuInfoDto);
	
	/**
	 * 根据角色id获取用户
	 * @param roleId
	 * @return List<MenuInfoDto>
	 */
	public List<MenuInfoDto> selectUserByRoleId(String roleId);
	
	/**
	 * 获取schema中的用户
	 * @return List<MenuInfoDto>
	 */
	public List<TreeDto> getUsers();
	
	/**
	 * 根据角色id获取用户id
	 * @param roleId
	 * @return List<RoleInfoDto>
	 */
	public List<RoleInfoDto> getUsersByRoleId(String roleId);
 	
	/**   
	 * 根据userid和roleid获取用户角色是否存在
	 * @param params
	 * @return RoleInfoDto      
	 */
	public RoleInfoDto getUserRoleByKey(Map<String, Object> params);
	
	/**
	 * 添加用户角色关系
	 * @param menuInfoDto
	 */
	public void addUserRole(MenuInfoDto menuInfoDto);
	
	/**
	 * 删除用户角色关系
	 * @param menuInfoDto
	 */
	public void delUserRole(MenuInfoDto menuInfoDto);
	
	/**
	 * 获取button
	 * @param menuInfoDto
	 * @return List<MenuInfoDto>
	 */
	public List<MenuInfoDto> getButtons(MenuInfoDto menuInfoDto);
	
	/**
	 * 获取角色对应的button
	 * @param menuInfoDto
	 * @return List<MenuInfoDto>
	 */
	public List<MenuInfoDto> getButtonsByRole(MenuInfoDto menuInfoDto);
}
