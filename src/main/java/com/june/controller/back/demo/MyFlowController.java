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
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.MessageDto;
import com.june.dto.back.demo.FlowListDto;
import com.june.dto.back.demo.LeaveDto;
import com.june.service.back.demo.MyflowService;
import com.june.utility.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * 我的流程用controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年10月21日 下午6:21:33
 */
// @Controller
// @RequestMapping("/myflow")
public class MyFlowController extends BaseController<FlowListDto> {

	@Autowired
	private MyflowService myflowService;

	/**
	 * 页面初始化
	 * @return
	 * @date 2016年10月21日 下午6:21:44
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/init/")
	public ModelAndView init() {
		ModelAndView result = null;
		result = new ModelAndView("demo/myFlow");
		return result;
	}

	/**
	 * 获取页面数据一览
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:21:50
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getmyFlow")
	public void getflowlist(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		FlowListDto flowListDto = new FlowListDto();
		fillRequestDto(httpServletRequest, flowListDto);
		flowListDto = myflowService.findTaskListByName(flowListDto);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject jsonObject = JSONObject.fromObject(flowListDto, jsonConfig);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}

	/**
	 * 任务审批 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:21:56
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/doprocess")
	public void doprocess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		FlowListDto flowListDto = new FlowListDto();
		fillRequestDto(httpServletRequest, flowListDto);
		myflowService.submitTask(flowListDto);
		MessageDto messageDto = new MessageDto();
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add("任务办理成功");
		messageDto.setErrList(errList);
		messageDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(messageDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}

	/**
	 * 办理任务页面初始化
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:22:04
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/doprocessinit")
	public void doprocessinit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		LeaveDto leaveDto = new LeaveDto();
		fillRequestDto(httpServletRequest, leaveDto);
		String taskid = leaveDto.getTaskId();
		leaveDto = myflowService.getLeaveInfoByTaskid(taskid);
		List<FlowListDto> list = myflowService.findOutComeListByTaskId(taskid);
		leaveDto.setList(list);
		leaveDto.setTaskId(taskid);
		JSONObject jsonObject = JSONObject.fromObject(leaveDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}

	/**
	 * 获取审批历史记录 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:22:10
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/gethistory")
	public void gethistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		LeaveDto leaveDto = new LeaveDto();
		fillRequestDto(httpServletRequest, leaveDto);
		String taskid = leaveDto.getTaskId();
		List<FlowListDto> list = myflowService.gethistory(taskid);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		ConvertListToJson(httpServletResponse, jsonArray);
	}

	/**
	 * 获取流程图 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:22:16
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getimage")
	public void getimage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String taskId = httpServletRequest.getParameter("taskId");
		ProcessDefinition pd = myflowService.findProcessDefinitionBytaskId(taskId);
		myflowService.viewImage(pd.getDeploymentId(), pd.getDiagramResourceName(), httpServletResponse);
	}
}
