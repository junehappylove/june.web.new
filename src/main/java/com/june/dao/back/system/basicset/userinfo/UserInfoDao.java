package com.june.dao.back.system.basicset.userinfo;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.login.RoleDto;
import com.june.dto.back.system.basicset.UserInfoDto;
import com.june.dto.back.system.basicset.UserRoleDto;

/**
* @Description: 用户管理用Dao
* @author CL 
* @date 2015年11月6日 下午2:30:30 
* @version V1.0
 */
public interface UserInfoDao extends BaseDao<UserInfoDto> {
	

	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param userId
	 * @return UserInfoDto
	 */
	public UserInfoDto get(String userId);
	
	/**   
	 * @Description: 根据用户编号查询用户信息
	 * @author CL
	 * @param: @param userId
	 * @param: @return      
	 * @return: UserInfoDto      
	 * @throws   
	 */
	public UserInfoDto getUserInfoById(String userId);
	
	/**
	 * @Description:查询用户信息
	 * @param params
	 * @return
	 */
	public List<UserInfoDto> selectUserInfo(UserInfoDto userInfoDto);
	
	/**
	 * @Description 根据用户id获取用户信息
	 * @param userInfoDto
	 * @return
	 */
	public UserInfoDto getUserInfo(UserInfoDto userInfoDto);
	
	/**
	 * @Description 根据用户id获取用户信息
	 * @param userInfoDto
	 * @return
	 */
	public UserInfoDto getUserInfoById(UserInfoDto userInfoDto);
	
	/**
	 * @Description:添加用户信息
	 * @param params
	 */
	public void addUserInfo(UserInfoDto userInfoDto);
	
	/**
	 * @Description:添加用户schema信息
	 * @param params
	 */
	public void addUserInfoToTenant(UserInfoDto userInfoDto);
	
	/**
	 * @Description:编辑用户信息
	 * @param params
	 */
	public void updateUserInfoById(UserInfoDto userInfoDto);
	
	/**
	 * @Description 获取用户角色信息
	 * @param params
	 * @return
	 */
	public List<UserRoleDto> getRolesById(UserInfoDto userInfoDto);
	
	/**
	 * @Description 获取所有角色信息
	 * @param params
	 * @return
	 */
	public List<RoleDto> getAllRoles(UserInfoDto userInfoDto);
	
	/**
	 * @Description 根据用户id和角色id删除用户角色信息
	 * @param params
	 */
	public void deleteUserRoleInfo(UserInfoDto userInfoDto);
	
	/**
	 * @Description 添加用户角色信息
	 * @param params
	 */
	public void addUserRoleInfo(UserInfoDto userInfoDto);
	
	/**   
	 * @Description: 删除用户
	 * @author caiyang
	 * @param: @param userInfoDto      
	 * @return: void      
	 * @throws   
	 */
	public void deleteUserById(UserInfoDto userInfoDto);
	
	/**
	 * 设置用户默认车型
	 * 
	 * @param userInfoDto
	 * @date 2016年6月29日 下午2:08:20
	 * @writer wjw.happy.love@163.com
	 */
	void setDefaultVehicle(UserInfoDto userInfoDto);
}
