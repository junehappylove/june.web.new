package com.june.dao.back.system.base.user;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.login.RoleDto;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.dto.back.system.base.UserRoleDto;

/**
 * 用户管理用Dao
 * UserInfoDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月12日 下午8:49:25
 * @version 1.0.0
 */
public interface UserInfoDao extends BaseDao<UserInfoDto> {
	
	/**
	 * 添加用户schema信息
	 * @param userInfoDto
	 * @date 2017年3月12日 下午8:48:39
	 * @writer junehappylove
	 */
	public void addUserInfoToTenant(UserInfoDto userInfoDto);
	
	/**
	 * 获取用户角色信息
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月12日 下午8:48:27
	 * @writer junehappylove
	 */
	public List<UserRoleDto> getRolesById(UserInfoDto userInfoDto);
	
	/**
	 * 获取所有角色信息
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月12日 下午8:48:21
	 * @writer junehappylove
	 */
	public List<RoleDto> getAllRoles(UserInfoDto userInfoDto);
	
	/**
	 * 根据用户id和角色id删除用户角色信息
	 * @param userInfoDto
	 * @date 2017年3月12日 下午8:48:15
	 * @writer junehappylove
	 */
	public void deleteUserRoleInfo(UserInfoDto userInfoDto);
	
	/**
	 * 添加用户角色信息
	 * @param userInfoDto
	 * @date 2017年3月12日 下午8:48:08
	 * @writer junehappylove
	 */
	public void addUserRoleInfo(UserInfoDto userInfoDto);
	
}
