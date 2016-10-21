/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.controller.back.demo;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.MessageDto;
import com.june.dto.back.demo.LeaveDto;
import com.june.service.back.demo.LeaveService;

import net.sf.json.JSONObject;

/**
 * @Description: 申请休假用controller
 * @author caiyang
 * @date 2015年12月18日 下午1:46:13
 * @version V1.0
 */
// @Controller
// @RequestMapping("/leave")
public class LeaveActivitiController extends BaseController {

	@Autowired
	private LeaveService leaveService;

	// 申请休假页面初始化
	@RequestMapping("/leaveinit/")
	public ModelAndView leaveinit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		// flowListService.processback();
		ModelAndView result = null;
		result = new ModelAndView("demo/leave");
		return result;
	}

	/**
	 * 获取请假列表
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:20:38
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getleave")
	public void getleave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		LeaveDto leaveDto = new LeaveDto();
		fillRequestDto(httpServletRequest, leaveDto);
		leaveDto = leaveService.getleave(leaveDto);
		JSONObject jsonObject = JSONObject.fromObject(leaveDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}

	@RequestMapping("/saveleave")
	public void saveleave(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		LeaveDto leaveDto = new LeaveDto();
		fillRequestDto(httpServletRequest, leaveDto);
		leaveDto.setLeaveStatus(0);// 提出申请，还未启动
		leaveService.savenewleave(leaveDto);
		// 启动流程
		leaveService.saveStartProcess(leaveDto);
		MessageDto messageDto = new MessageDto();
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add("流程启动成功");
		messageDto.setErrList(errList);
		messageDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(messageDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}

}
