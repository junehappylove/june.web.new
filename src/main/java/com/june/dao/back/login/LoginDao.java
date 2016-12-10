package com.june.dao.back.login;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.login.ButtonDto;
import com.june.dto.back.login.MenuDto;
import com.june.dto.back.system.base.UserInfoDto;

/**  
* @Description: 登录用dao
* @author caiyang
* @date 2015年9月11日 下午2:42:28 
* @version V1.0  
 */
public interface LoginDao extends BaseDao<UserInfoDto> {
	/** 
	* @Description: 登录用户查询
	* @param @param userInfoDto
	* @param @return  
	* @return UserInfoDto 
	* @throws 
	*/ 
	public UserInfoDto loginCheck(UserInfoDto userInfoDto);
	
	/**   
	 * @Description: 根据userid获取用户信息
	 * @param: @param userId
	 * @param: @return      
	 * @return: UserInfoDto      
	 * @throws   
	 */
	public UserInfoDto getLoginUser(String userId);
	
	/**   
	 * @Description: 获取一级菜单
	 * @param: @param menuDto
	 * @return: List<MenuDto>      
	 * @throws   
	 */
	public List<MenuDto> getFristMenu(UserInfoDto userInfoDto);
	
	/**   
	 * @Description: 获取二级以及三级菜单
	 * @param: @param menuDto
	 * @return: List<MenuDto>      
	 * @throws   
	 */
	public List<MenuDto> getSecondMenu(MenuDto menuDto);
	
	/**   
	 * @Description: 根据menuid获取菜单信息
	 * @param: @param menuDto
	 * @return: List<MenuDto>      
	 * @throws   
	 */
	public MenuDto getMenuById(MenuDto menuDto);
	
	/**   
	 * @Description: 根据userid获取用户信息
	 * @author caiyang
	 * @param: @return      
	 * @return: UserInfoDto      
	 * @throws   
	 */
	public UserInfoDto getUserInfoById(String userId);
	
	/**   
	 * @Description: 根据用户id到，用户对应的schema下获取对应的角色信息
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: UserInfoDto      
	 * @throws   
	 */
	public List<UserInfoDto> getRoleInfoByUserId(UserInfoDto userInfoDto);
	
	/**   
	 * @Description: 获取角色对应的有权限的按钮
	 * @author caiyang
	 * @param: @param params
	 * @param: @return      
	 * @return: ButtonDto      
	 * @throws   
	 */
	public List<ButtonDto> getRoleButton(UserInfoDto userInfoDto);
	
	/**   
	 * @Description: 登录失败时更新登录尝试次数
	 * @author caiyang
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	public void updateFailLoginAttempt(UserInfoDto userInfoDto);
	
	/**   
	 * @Description: 登录成功时更新登录尝试次数
	 * @author caiyang
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	public void updateSuccessLoginAttempt(UserInfoDto userInfoDto);
}
