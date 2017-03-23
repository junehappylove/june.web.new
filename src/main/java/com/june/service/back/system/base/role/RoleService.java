/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.service.back.system.base.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.common.TreeDto;
import com.june.dao.back.system.base.role.RoleDao;
import com.june.dto.back.system.base.MenuInfoDto;
import com.june.dto.back.system.base.RoleInfoDto;

/**
 * 
 * 角色管理模块用service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月13日 下午1:59:14
 */
@Service
public class RoleService extends BaseService<RoleDao, RoleInfoDto> {

	/**
	 * 角色管理模块用dao注入
	 */
	@Autowired
	private RoleDao roleDao;

	/**
	 * 根据roleName获取角色信息
	 * 
	 * @param roleInfoDto
	 * @return
	 * @date 2016年5月13日 下午2:00:55
	 * @writer wjw.happy.love@163.com
	 */
	@Cacheable(value = "getRoleByRoleName", key = "#root.args[0]")
	public RoleInfoDto getRoleByRoleName(RoleInfoDto roleInfoDto) {
		return roleDao.getRoleByRoleName(roleInfoDto);
	}

	/**
	 * 根据roleId获取角色信息
	 * 
	 * @param roleId
	 * @return
	 * @date 2016年5月13日 下午2:01:09
	 * @writer wjw.happy.love@163.com
	 */
	@Cacheable(value = "getRoleByRoleId", key = "#p0")
	public RoleInfoDto getRoleByRoleId(String roleId) {
		return roleDao.get(new RoleInfoDto(roleId));
	}

	/**
	 * 删除角色信息 void
	 */
	@CacheEvict(value = { "getPageList", "getTotal", "getRoleByRoleId", "getRoleByRoleName" }, allEntries = true)
	public void deleteRoleById(String roleId) {
		roleDao.delete(new RoleInfoDto(roleId));
	}

	/**
	 * 获取菜单 List<MenuInfoDto>
	 */
	public List<TreeDto> getMenus(String roleId) {
		// 获取全部的一级菜单
		List<TreeDto> firstMenu = roleDao.getFirstMenus();
		// 根据角色获取有权限的一级菜单
		List<TreeDto> firstMenuByRole = roleDao.getFirstMenusByRole(roleId);
		for (int i = 0; i < firstMenu.size(); i++) {
			for (int j = 0; j < firstMenuByRole.size(); j++) {
				if (firstMenuByRole.get(j).getId().equals(firstMenu.get(i).getId())) {
					firstMenu.get(i).setChecked(true);
				}
			}
		}
		firstMenu = getSubMenu(firstMenu, roleId);

		return firstMenu;
	}

	private List<TreeDto> getSubMenu(List<TreeDto> menuList, String roleId) {
		for (int i = 0; i < menuList.size(); i++) {
			List<TreeDto> subMenu = roleDao.getSubMenu(menuList.get(i));
			// 有子菜单，根据角色获取有权限的子菜单
			if (subMenu != null && subMenu.size() > 0) {
				menuList.get(i).setRoleId(roleId);
				List<TreeDto> subMenuByRole = roleDao.getSubMenuByRole(menuList.get(i));
				for (int j = 0; j < subMenu.size(); j++) {
					for (int m = 0; m < subMenuByRole.size(); m++) {
						if (subMenuByRole.get(m).getId().equals(subMenu.get(j).getId())) {
							subMenu.get(j).setChecked(true);
						}
					}
				}
				menuList.get(i).setChildren(subMenu);
				getSubMenu(menuList.get(i).getChildren(), roleId);
			} else {
				menuList.get(i).setRoleId(roleId);
				// 无子菜单，获取页面按钮
				List<TreeDto> functions = roleDao.getFunctions(menuList.get(i));
				// 获取页面有权限的按钮
				List<TreeDto> functionsByRole = roleDao.getFunctionsByRole(menuList.get(i));
				for (int j = 0; j < functions.size(); j++) {
					for (int m = 0; m < functionsByRole.size(); m++) {
						if (functionsByRole.get(m).getId().equals(functions.get(j).getId())) {
							functions.get(j).setChecked(true);
						}
					}
					functions.get(j).setId("button" + functions.get(j).getId());
				}
				menuList.get(i).setChildren(functions);
			}
		}
		return menuList;
	}

	/**
	 * 给角色赋菜单权限
	 */
	@CacheEvict(value = { "getFunctionByRole", "getAuthorityofRole", "getButtonauthorityOfRole", "getPageList",
			"getTotal", "getRoleMenus", "getRoleButtons" }, allEntries = true)
	public void authorityMenu(MenuInfoDto menuInfoDto) {
		roleDao.authorityMenu(menuInfoDto);
	}

	/**
	 * 给角色赋按钮权限
	 */
	@CacheEvict(value = { "getFunctionByRole", "getAuthorityofRole", "getButtonauthorityOfRole", "getPageList",
			"getTotal", "getRoleMenus", "getRoleButtons" }, allEntries = true)
	public void authorityButton(MenuInfoDto menuInfoDto) {
		roleDao.authorityButton(menuInfoDto);
	}

	/**
	 * 给角色删除权限
	 */
	@CacheEvict(value = { "getFunctionByRole", "getAuthorityofRole", "getButtonauthorityOfRole", "getPageList",
			"getTotal", "getRoleMenus", "getRoleButtons" }, allEntries = true)
	public void delAuthorityMenu(MenuInfoDto menuInfoDto) {
		roleDao.delAuthorityMenu(menuInfoDto);
	}

	/**
	 * 给角色按钮权限
	 */
	@CacheEvict(value = { "getFunctionByRole", "getAuthorityofRole", "getButtonauthorityOfRole", "getPageList",
			"getTotal", "getRoleMenus", "getRoleButtons" }, allEntries = true)
	public void delAuthorityButton(MenuInfoDto menuInfoDto) {
		roleDao.delAuthorityButton(menuInfoDto);
	}

	/**
	 * 获取该角色所拥有的所有权限
	 */
	public String getAuthorityofRole(String roleId) {
		List<MenuInfoDto> list = roleDao.getAuthorityofRole(roleId);
		String authorityMenus = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				authorityMenus = authorityMenus + list.get(i).getId() + ",";
			}
		}
		return authorityMenus;
	}

	/**
	 * 获取该角色拥有的按钮权限
	 */
	public String getButtonauthorityOfRole(String roleId) {
		List<MenuInfoDto> list = roleDao.getButtonauthorityOfRole(roleId);
		String authorityButtons = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				authorityButtons = authorityButtons + list.get(i).getId() + ",";
			}
		}
		return authorityButtons;
	}

	/**
	 * 根据角色id获取用户
	 */
	public List<MenuInfoDto> selectUserByRoleId(String roleId) {
		return roleDao.selectUserByRoleId(roleId);
	}

	/**
	 * 获取schema中的用户
	 */
	public List<TreeDto> getUsers(String roleId) {
		List<RoleInfoDto> authorityUsers = roleDao.getUsersByRoleId(roleId);
		List<TreeDto> allUsers = roleDao.getUsers();
		for (int i = 0; i < allUsers.size(); i++) {
			for (int j = 0; j < authorityUsers.size(); j++) {
				if (authorityUsers.get(j).getUserId().equals(allUsers.get(i).getId())) {
					allUsers.get(i).setChecked(true);
				}
			}
		}
		return allUsers;
	}

	/**
	 * 根据角色id获取用户id
	 */
	public String getUsersByRoleId(String roleId) {
		List<RoleInfoDto> list = roleDao.getUsersByRoleId(roleId);
		String userIds = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				userIds = userIds + list.get(i).getUserId() + ",";
			}
		}
		return userIds;
	}

	/**
	 * 添加用户角色关系
	 */
	@CacheEvict(value = { "getFunctionByRole", "getAuthorityofRole", "getButtonauthorityOfRole", "getPageList",
			"getTotal", "getRoleMenus", "getRoleButtons" }, allEntries = true)
	public void addUserRole(MenuInfoDto menuInfoDto) {
		roleDao.addUserRole(menuInfoDto);
	}

	/**
	 * 删除用户角色关系
	 */
	@CacheEvict(value = { "getFunctionByRole", "getAuthorityofRole", "getButtonauthorityOfRole", "getPageList",
			"getTotal", "getRoleMenus", "getRoleButtons" }, allEntries = true)
	public void delUserRole(MenuInfoDto menuInfoDto) {
		roleDao.delUserRole(menuInfoDto);
	}
}
