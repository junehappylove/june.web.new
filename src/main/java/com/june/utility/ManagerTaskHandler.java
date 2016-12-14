package com.june.utility;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.june.dto.back.system.base.UserInfoDto;
import com.june.service.back.demo.LeaveService;

@Service
public class ManagerTaskHandler implements TaskListener {
	
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -7422711371450947390L;

	@Override
	public void notify(DelegateTask delegateTask) {
		//获取当前登录用户的id
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		ApplicationContext ac2 = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()); 
		LeaveService leaveService = (LeaveService) ac2.getBean("leaveService");
		UserInfoDto userInfoDto =  (UserInfoDto) request.getSession().getAttribute("userInfo");
		String orgId = userInfoDto.getOrgId();
		String leader = leaveService.getLeader(orgId);
		//设置个人任务的办理人
		delegateTask.setAssignee(leader);
	}

}