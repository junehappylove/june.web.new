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
import com.june.utility.StringUtil;

/**
 * @Description 用户信息Service
 * @author liren
 *
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
	 * 
	 * @param userInfoDto
	 * @return: void
	 */
	@CacheEvict(value = { "getUserInfoById", "getPageList", "getTotal" }, allEntries = true)
	@Override
	public void deleteDtoById(UserInfoDto userInfoDto) {
		userInfoDao.delete(userInfoDto);//删除用户
		userInfoDao.deleteUserRoleInfo(userInfoDto);//删除用户的角色
	}
	
	/**
	 * 用户角色更新
	 * 
	 * @param userInfoDto
	 * @return: void @throws
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
	 * 
	 * @param params
	 * @return
	 */
	public List<UserRoleDto> getRolesById(UserInfoDto userInfoDto) {
		return userInfoDao.getRolesById(userInfoDto);
	}

	/**
	 *
	 * 删除用户所有角色信息
	 * 
	 * @return void
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
	
	/**
	 * 设置用户默认车型
	 * @param userInfoDto
	 * @date 2016年6月29日 下午2:09:49
	 * @writer wjw.happy.love@163.com
	 */
	public void setDefaultVehicle(UserInfoDto userInfoDto){
		userInfoDao.setDefaultVehicle(userInfoDto);
	}
}
