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


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

/**  
* @Description: 身份验证登录，验证失败抛出session异常，重新登录
* @author caiyang
* @date 2016年2月16日 上午9:54:33 
* @version V1.0  
 */
public class ClientAuthenticationFilter extends AuthenticationFilter {  
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {  
        Subject subject = getSubject(request, response);  
        return subject.isAuthenticated();  
    }  
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
    	   HttpServletRequest httpServletRequest = (HttpServletRequest) request;
           HttpServletResponse httpServletResponse = (HttpServletResponse) response;
   		   httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/logout");
           return false;
    }  
}
