package com.june.service.back.system.base.user;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.system.base.user.UserInfoDao;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.dto.back.system.base.UserRoleDto;
import com.june.util.StringUtil;

/**
 * 用户信息Service
 * UserInfoService <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月12日 下午8:58:53
 * @version 1.0.0
 */
@Service
public class UserInfoService extends BaseService<UserInfoDao, UserInfoDto> {

	/**
	 * 用户信息dao
	 */
	@Autowired
	protected UserInfoDao userInfoDao;

	/**
	 * 删除用户以及用户相关的角色信息
	 * @param userInfoDto
	 */
	@Override
	@CacheEvict(value = { "getUserInfoById", "getPageList", "getTotal" }, allEntries = true)
	public void deleteDto(UserInfoDto userInfoDto) {
		userInfoDao.delete(userInfoDto);//删除用户
		userInfoDao.deleteUserRoleInfo(userInfoDto);//删除用户的角色
	}
	
	/**
	 * 用户角色更新
	 * 
	 * @param userInfoDto
	 */
	@CacheEvict(value = { "getUserInfoById", "getPageList", "getTotal" }, allEntries = true)
	public void updateUserRoleInfo(UserInfoDto userInfoDto) {
		// 删除用户角色信息
		userInfoDao.deleteUserRoleInfo(userInfoDto);
		// 添加新的用户角色信息
		addUserRoleInfo(userInfoDto);

	}

	/**
	 * 根据用户ID获取用户角色信息
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月12日 下午8:58:21
	 * @writer junehappylove
	 */
	public List<UserRoleDto> getRolesById(UserInfoDto userInfoDto) {
		return userInfoDao.getRolesById(userInfoDto);
	}

	/**
	 *
	 * 删除用户所有角色信息
	 * 
	 * @param userInfoDto
	 */
	@CacheEvict(value = { "getUserInfoById", "getPageList", "getTotal" }, allEntries = true)
	public void deleteUserRoleInfo(UserInfoDto userInfoDto) {
		List<UserRoleDto> userRoleList = getRolesById(userInfoDto);
		if (userRoleList != null && userRoleList.size() > 0) {// 如果存在用户角色信息
			userInfoDao.deleteUserRoleInfo(userInfoDto);
		}
	}

	/**
	 * 添加用户角色信息
	 * 
	 * @param userInfoDto
	 */
	@CacheEvict(value = { "getUserInfoById", "getPageList", "getTotal" }, allEntries = true)
	public void addUserRoleInfo(UserInfoDto userInfoDto) {
		String roleIds = userInfoDto.getRoleId();
		if (!StringUtil.isBlank(roleIds)) {
			String[] roleIdArray = roleIds.split(",");
			for (String roleId : roleIdArray) {
				if (!StringUtil.isBlank(roleId)) {
					userInfoDto.setRoleId(roleId);
					List<UserRoleDto> userRoleList = getRolesById(userInfoDto);
					if (userRoleList == null || userRoleList.size() == 0) {// 不存在添加用户角色信息
						userInfoDto.setAddTime(new Timestamp(System.currentTimeMillis()));
						userInfoDao.addUserRoleInfo(userInfoDto);
					}
				}
			}
		}
	}
	
}
