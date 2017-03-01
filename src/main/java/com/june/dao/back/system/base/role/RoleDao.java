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
* @Description: 角色管理模块用dao
* @author caiyang
* @date 2015年11月10日 上午9:05:00 
* @version V1.0  
 */
public interface RoleDao extends BaseDao<RoleInfoDto>{

	/**   
	 * @Description: 插入新增角色
	 * @author	caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	@DataSource("slave")
	public void saveAddRole(RoleInfoDto roleInfoDto);
	
	/**   
	 * @Description: 根据roleName获取角色信息
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: RoleInfoDto      
	 * @throws   
	 */
	public RoleInfoDto getRoleByRoleName(RoleInfoDto roleInfoDto);
	
	/**   
	 * @Description: 根据roleId获取角色信息
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: RoleInfoDto      
	 * @throws   
	 */
	public RoleInfoDto getRoleByRoleId(String roleId);
	
	/**   
	 * @Description: 更新角色信息
	 * @author caiyang
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	
	public void updateRoleById(RoleInfoDto roleInfoDto);
	
	/**   
	 * @Description: 删除角色信息
	 * @author	caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void deleteRoleById(String roleId);
	
	/**   
	 * @Description: 根据角色获取菜单
	 * @author caiyang
	 * @param: @param params      
	 * @return: List<MenuInfoDto>   
	 * @throws   
	 */
	public List<TreeDto> getFirstMenusByRole(String roleId);
	
	/**   
	 * @Description: 获取子菜单
	 * @author caiyang
	 * @param: @param treeDto
	 * @param: @return      
	 * @return: List<TreeDto>      
	 * @throws   
	 */
	public List<TreeDto> getSubMenu(TreeDto treeDto);
	
	/**   
	 * @Description: 根据角色获取有权限的子菜单
	 * @author caiyang
	 * @param: @param treeDto
	 * @param: @return      
	 * @return: List<TreeDto>      
	 * @throws   
	 */
	public List<TreeDto> getSubMenuByRole(TreeDto treeDto);
	
	/**   
	 * @Description: 获取功能
	 * @author caiyang
	 * @param: @param treeDto
	 * @param: @return      
	 * @return: List<TreeDto>      
	 * @throws   
	 */
	public List<TreeDto> getFunctions(TreeDto treeDto);
	
	/**   
	 * @Description: 根据角色获取有权限的功能
	 * @author caiyang
	 * @param: @param treeDto
	 * @param: @return      
	 * @return: List<TreeDto>      
	 * @throws   
	 */
	public List<TreeDto> getFunctionsByRole(TreeDto treeDto);
	
	/**   
	 * @Description: 获取菜单
	 * @author caiyang
	 * @param: @param params      
	 * @return: List<MenuInfoDto>   
	 * @throws   
	 */
	@Cacheable(value="getFirstMenus",key="#root.caches[0].name")
	public List<TreeDto> getFirstMenus();
	
	
	/**   
	 * @Description: 获取该角色所拥有的所有权限
	 * @author	caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: String      
	 * @throws   
	 */
	@Cacheable(value="getauthorityofRole",key="#root.caches[0].name")
	public List<MenuInfoDto> getauthorityofRole(String roleId);
	
	/**   
	 * @Description: 获取该角色拥有的按钮权限
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: String      
	 * @throws   
	 */
	@Cacheable(value="getButtonauthorityOfRole",key="#root.caches[0].name")
	public List<MenuInfoDto> getButtonauthorityOfRole(String roleId);
	
	/**   
	 * @Description: 给角色赋权限
	 * @author	caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void authorityMenu(MenuInfoDto menuInfoDto);
	
	/**   
	 * @Description: 给角色赋按钮权限
	 * @author	caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void authorityButton(MenuInfoDto menuInfoDto);
	
	/**   
	 * @Description: 给角色删除权限
	 * @author caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void delAuthorityMenu(MenuInfoDto menuInfoDto);

	/**   
	 * @Description: 给角色按钮权限
	 * @author caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void delAuthorityButton(MenuInfoDto menuInfoDto);
	/**   
	 * @Description: 根据角色id获取用户
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: List<MenuInfoDto>      
	 * @throws   
	 */
	public List<MenuInfoDto> selectUserByRoleId(String roleId);
	
	/**   
	 * @Description: 获取schema中的用户
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: List<MenuInfoDto>      
	 * @throws   
	 */
	public List<TreeDto> getUsers();
	
	
	/**   
	 * @Description: 根据角色id获取用户id
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: List<RoleInfoDto>      
	 * @throws   
	 */
	public List<RoleInfoDto> getUsersByRoleId(String roleId);
 	/**   
	 * @Description: 根据userid和roleid获取用户角色是否存在
	 * @author	caiyang
	 * @param: @return      
	 * @return: RoleInfoDto      
	 * @throws   
	 */
	public RoleInfoDto getUserRoleByKey(Map<String, Object> params);
	
	/**   
	 * @Description: 添加用户角色关系
	 * @author caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void addUserRole(MenuInfoDto menuInfoDto);
	
	/**   
	 * @Description: 删除用户角色关系
	 * @author caiyang
	 * @param: @param params      
	 * @return: void      
	 * @throws   
	 */
	public void delUserRole(MenuInfoDto menuInfoDto);
	
	/**   
	 * @Description: 获取button
	 * @author caiyang
	 * @param: @param params      
	 * @return: List<MenuInfoDto>   
	 * @throws   
	 */
	public List<MenuInfoDto> getButtons(MenuInfoDto menuInfoDto);
	
	/**   
	 * @Description: 获取角色对应的button
	 * @author caiyang
	 * @param: @param params      
	 * @return: List<MenuInfoDto>   
	 * @throws   
	 */
	public List<MenuInfoDto> getButtonsByRole(MenuInfoDto menuInfoDto);
}
