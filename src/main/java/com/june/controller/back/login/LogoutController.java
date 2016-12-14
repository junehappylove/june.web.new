/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.controller.back.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	/**
	 * app用户退出，返回applogoutsuccess
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @date 2016年10月21日 下午6:39:23
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/app/logout")
	public void success(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			PrintWriter pw = resp.getWriter();
			pw.print("applogoutsuccess");
		}
		
	}
}
