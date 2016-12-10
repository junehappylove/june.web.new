/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.shiro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;

import com.june.common.Constants;
import com.june.dao.back.login.LoginDao;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.shiro.dao.ShiroUserDao;
import com.june.shiro.dto.Resource;
import com.june.shiro.dto.User;
import com.june.utility.DateUtil;
import com.june.utility.MessageUtil;
import com.june.utility.exception.LoginAttemptException;

public class ShiroDbRealm extends AuthorizingRealm{  
	 
    @Autowired  
    private ShiroUserDao shiroUserDao;  
    
    @Autowired
    private LoginDao loginDao;
 
    /**
     *   
     * 当用户进行访问链接时的授权方法  
     *   
     */ 
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
    	
    	// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }
    	
        if (principals == null) {  
            throw new AuthorizationException("Principal对象不能为空");  
        }  
 
        ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();  
        //获取访问的url
		String url =  ((HttpServletRequest)request).getServletPath().toString();
		HttpSession httpSession = ((HttpServletRequest)request).getSession();
		//获取用户信息
		UserInfoDto userInfoDto = (UserInfoDto)httpSession.getAttribute("userInfo");
        //获取用户响应的permission  
        List<String> permissions = new ArrayList<String>();
        // permissions.add("access");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
        //获取角色对应的菜单url
        List<Resource> list = shiroUserDao.getRoleMenus(userInfoDto.getRoleId());
        //获取角色对应的业务url
        List<Resource> buttonUrlList = shiroUserDao.getRoleButtons(userInfoDto.getRoleId());
        //判断是否是一级菜单或者二级菜单请求
        if (url.contains("/frame/")||url.contains("/SecondMenu/init/")) {
        	//获取一级菜单的id
			String menuId = url.split("/")[url.split("/").length-1];
			Resource resource = new Resource();
			resource.setRoleId(userInfoDto.getRoleId());
			resource.setMenuId(menuId);
			//判断该角色是否有访问该菜单的权限
			resource = shiroUserDao.getMenubyRoleIdMenuId(resource);
			if (resource == null) {
				permissions.add("noaccess");
			}else{
				permissions.add("access");
			}
		}else{
			//否则是三级菜单或者业务url
			boolean permissionFlag = false;
			//判断三级菜单是否有访问权限
			for (int i = 0; i < list.size(); i++) {
				if (url.matches(list.get(i).getMenuUrl())) {
					//info.addStringPermissions(permissions);
					permissionFlag = true;
					break;
				}
			}
			//判断业务url是否有访问权限
			if (!permissionFlag) {
				for (int i = 0; i < buttonUrlList.size(); i++) {
					if (url.matches(buttonUrlList.get(i).getMenuUrl())) {
						//info.addStringPermissions(permissions);
						permissionFlag = true;
						break;
					}
				}
			}
			if (!permissionFlag) {
				permissions.add("noaccess");
			}
			else{
				permissions.add("access");
			}
		}
        info.addStringPermissions(permissions);
        return info;  
    }  
 
    /**
     * 用户登录的认证方法  
     *   
     */ 
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;  
 
        String username = usernamePasswordToken.getUsername();  
 
        if (username == null) {  
            throw new AccountException("用户名不能为空");  
        }  
 
        User user = shiroUserDao.getUserByUsername(username);  
 
        if (user == null) {  
            throw new UnknownAccountException("用户不存在");  
        }  
		
		if(user.getUserLock().equals("1")){
			throw new  LockedAccountException("用户帐号已被锁定，请联系系统管理员！");
		}
		
		String lastLoginTime = user.getLastLoginTime();
		Date lastLoginTimeDate = DateUtil.convert2Date(lastLoginTime);//上一次登录时间
		Date now = DateUtil.convert2Date(DateUtil.convert2String(new Date()));
		long time = now.getTime();
		if(lastLoginTimeDate != null) {
			time = now.getTime() - lastLoginTimeDate.getTime();
		}
		int attempt = user.getAttempt();
		if(time < Constants.LOCK_TIME * 60 * 1000) { // 5分钟以内连续登录
			if (attempt == Constants.ATTEMP_TIME) {
				int min = (int) ((Constants.LOCK_TIME * 60 * 1000 -time)/60000);
				int sec = (int) (((Constants.LOCK_TIME * 60 * 1000) -( min * 60 * 1000) - time)/1000);
				throw new LoginAttemptException(MessageUtil.formatMessage("login_attempt_error", new String[] {String.valueOf(min),String.valueOf(sec)}));
			}
		
		}else{
			//非连续登陆
			UserInfoDto userInfoDto = new UserInfoDto();
			userInfoDto.setUserId(user.getUserId());
			loginDao.updateSuccessLoginAttempt(userInfoDto);
		}
        return new SimpleAuthenticationInfo(user.getUserId(),user.getUserPassword(),user.getUserName());  
    }  
} 
