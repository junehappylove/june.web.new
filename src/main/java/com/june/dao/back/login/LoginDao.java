package com.june.dao.back.login;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.login.ButtonDto;
import com.june.dto.back.login.MenuDto;
import com.june.dto.back.system.base.UserInfoDto;

/**
 * 登录用dao
 * LoginDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月10日 下午12:02:11
 * @version 1.0.0
 */
public interface LoginDao extends BaseDao<UserInfoDto> {
	
	/**
	 * 登录用户查询
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月7日 上午12:32:04
	 * @writer junehappylove
	 */
	public UserInfoDto loginCheck(UserInfoDto userInfoDto);
	
	/**
	 * 根据userid获取用户信息
	 * @param userId
	 * @return
	 * @date 2017年3月7日 上午12:32:14
	 * @writer junehappylove
	 */
	public UserInfoDto getLoginUser(String userId);
	
	/**
	 * 获取一级菜单
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月7日 上午12:32:20
	 * @writer junehappylove
	 */
	public List<MenuDto> getFristMenu(UserInfoDto userInfoDto);
	
	/**
	 * 获取二级以及三级菜单
	 * @param menuDto
	 * @return
	 * @date 2017年3月7日 上午12:32:27
	 * @writer junehappylove
	 */
	public List<MenuDto> getSecondMenu(MenuDto menuDto);
	
	/**
	 * 根据menuid获取菜单信息
	 * @param menuDto
	 * @return
	 * @date 2017年3月7日 上午12:32:34
	 * @writer junehappylove
	 */
	public MenuDto getMenuById(MenuDto menuDto);
	
	/**
	 * 根据userid获取用户信息
	 * @param userId
	 * @return
	 * @date 2017年3月7日 上午12:32:41
	 * @writer junehappylove
	 */
	public UserInfoDto getUserInfoById(String userId);
	
	/**
	 * 根据用户id到，用户对应的schema下获取对应的角色信息
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月7日 上午12:32:48
	 * @writer junehappylove
	 */
	public List<UserInfoDto> getRoleInfoByUserId(UserInfoDto userInfoDto);
	
	/**
	 * 获取角色对应的有权限的按钮
	 * @param userInfoDto
	 * @return
	 * @date 2017年3月7日 上午12:32:53
	 * @writer junehappylove
	 */
	public List<ButtonDto> getRoleButton(UserInfoDto userInfoDto);
	
	/**
	 * 登录失败时更新登录尝试次数
	 * @param userInfoDto
	 * @date 2017年3月7日 上午12:32:59
	 * @writer junehappylove
	 */
	public void updateFailLoginAttempt(UserInfoDto userInfoDto);
	
	/**
	 * 登录成功时更新登录尝试次数
	 * @param userInfoDto
	 * @date 2017年3月7日 上午12:33:06
	 * @writer junehappylove
	 */
	public void updateSuccessLoginAttempt(UserInfoDto userInfoDto);
}
