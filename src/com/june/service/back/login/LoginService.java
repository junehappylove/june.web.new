/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.service.back.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.login.LoginDao;
import com.june.dto.back.login.ButtonDto;
import com.june.dto.back.login.MenuDto;
import com.june.dto.back.systemset.basicset.UserInfoDto;

/**
 * @Description: 登录用service
 * @author caiyang
 * @date 2015年9月11日 下午2:32:14
 * @version V1.0
 */
@Service
public class LoginService extends BaseService<LoginDao, UserInfoDto> {

	/**
	 * 登录用dao注入
	 */
	@Autowired
	protected LoginDao loginDao;

	/**
	 * 人员service
	 */
	//@Autowired
	//protected PersonInfoService personInfoService;

	/**
	 * 围栏service
	 */
	//@Autowired
	//protected FenceSetService fenceSetService;

	/**
	 * @Description: 登录用户check
	 * @param @param userInfoDto
	 * @return UserInfoDto
	 * @throws
	 */
	public UserInfoDto loginCheck(UserInfoDto userInfoDto) {
		userInfoDto = loginDao.loginCheck(userInfoDto);
		return userInfoDto;
	}

	/**
	 * @Description: 获取一级菜单
	 * @param menuDto
	 * @return List<MenuDto>
	 * @throws
	 */
	public List<MenuDto> getFristMenu(UserInfoDto userInfoDto) {
		List<MenuDto> list = loginDao.getFristMenu(userInfoDto);
		return list;
	}

	/**
	 * @Description: 获取二级以及三级菜单
	 * @param @param menuDto
	 * @return List<MenuDto>
	 * @throws
	 */
	//@Cacheable(value = "CustomerCache")
	public List<MenuDto> GetSecondMenu(MenuDto menuDto) {
		return loginDao.getSecondMenu(menuDto);
	}

	/**
	 * @Description: 根据menuId获取菜单信息
	 * @param @param menuDto
	 * @return MenuDto
	 * @throws
	 */
	public MenuDto getMenuById(MenuDto menuDto) {
		menuDto = loginDao.getMenuById(menuDto);
		return menuDto;
	}

	/**
	 * @Description: 根据userId获取用户信息
	 * @author caiyang
	 * @param: @return
	 * @return: UserInfoDto
	 * @throws
	 */
	public UserInfoDto getUserInfoById(String userId) {
		return loginDao.getUserInfoById(userId);
	}

	/**
	 * @Description: 根据用户id到，用户对应的schema下获取对应的角色信息
	 * @author caiyang
	 * @param: @param params
	 * @param: @return
	 * @return: UserInfoDto
	 * @throws
	 */
	public List<UserInfoDto> getRoleInfoByUserId(UserInfoDto userInfoDto) {
		return loginDao.getRoleInfoByUserId(userInfoDto);
	}
	
	/**   
	 * @Description: 获取角色对应的有权限的按钮
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: ButtonDto      
	 * @throws   
	 */
	public List<ButtonDto> getRoleButton(UserInfoDto userInfoDto)
	{
		return loginDao.getRoleButton(userInfoDto);
	}
	
	/**   
	 * @Description: 登录失败时更新登录尝试次数
	 * @author caiyang
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	public void updateFailLoginAttempt(UserInfoDto userInfoDto)
	{
		loginDao.updateFailLoginAttempt(userInfoDto);
	}
	
	/**   
	 * @Description: 登录成功时更新登录尝试次数
	 * @author caiyang
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	public void updateSuccessLoginAttempt(UserInfoDto userInfoDto)
	{
		loginDao.updateSuccessLoginAttempt(userInfoDto);
	}
}
