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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.dto.back.demo.QuartzTriggerDto;
import com.june.service.back.demo.QuartzService;
import com.june.utility.MessageUtil;

/**
 * 
 * 定时任务用controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年10月21日 下午6:23:11
 */
// @Controller
// @RequestMapping("/quatrz")
public class QurartzController extends BaseController<QuartzTriggerDto> {

	@Autowired
	private QuartzService quartzService;

	/**
	 * 页面初始化
	 * @return
	 * @date 2016年10月21日 下午6:23:20
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/init/")
	public ModelAndView inti() {
		ModelAndView result = null;
		result = new ModelAndView("demo/quartzList");
		return result;
	}

	/**
	 * 获取全部的任务
	 * @param request
	 * @param response
	 * @date 2016年10月21日 下午6:23:26
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getquartzlist")
	public void getquartzlist(HttpServletRequest request, HttpServletResponse response) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		quartzTriggerDto = quartzService.getPageList(quartzTriggerDto);
		toJson(quartzTriggerDto, response);
	}

	/**
	 * 暂停任务
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年10月21日 下午6:23:32
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/pause")
	public void pause(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		quartzService.pause(quartzTriggerDto.getTriggerName(), quartzTriggerDto.getTriggerGroup());
		
		message(response,"quartz_pause_success",MESSAGE_INFO);
	}

	/**
	 * 恢复任务
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年10月21日 下午6:23:37
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/resume")
	public void resume(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		quartzService.resume(quartzTriggerDto.getTriggerName(), quartzTriggerDto.getTriggerGroup());
		message(response,"quartz_resume_success",MESSAGE_INFO);
	}

	@RequestMapping("/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		boolean result = quartzService.remove(quartzTriggerDto.getTriggerName(), quartzTriggerDto.getTriggerGroup());
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		if (result) {
			errList.add(MessageUtil.formatMessage("quartz_delete_success"));
		} else {
			errList.add(MessageUtil.formatMessage("quartz_delete_error"));
		}
		quartzTriggerDto.setErrList(errList);
		quartzTriggerDto.setErrType("info");
		// 返回消息 end
		toJson(quartzTriggerDto, response);
	}

	/**
	 * 添加simpletrigger
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年10月21日 下午6:23:45
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/addsimpletrigger")
	public void addsimpletrigger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		quartzService.addSimpleTrigger(quartzTriggerDto);

		message(response,"quartz_add_success",MESSAGE_INFO,"simpleTrigger");
	}

	/**
	 * 获取添加modal的初始化数据
	 * @param request
	 * @param response
	 * @date 2016年10月21日 下午6:23:51
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getinitdata")
	public void getinitdata(HttpServletRequest request, HttpServletResponse response) {
		List<QuartzTriggerDto> jobNames = quartzService.getAllJobName();
		QuartzTriggerDto qtd = new QuartzTriggerDto();
		qtd.setRows(jobNames);
		toJson(qtd, response);
		qtd = null;
	}

	/**
	 * 根据cron表达式添加crontrigger
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年10月21日 下午6:23:58
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/addcrontrigger")
	public void addCrontrigger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		quartzService.addCrontrigger(quartzTriggerDto);
		
		message(response,"quartz_add_success",MESSAGE_INFO,"cronTrigger");
	}

	/**
	 * 立即执行一次
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年10月21日 下午6:24:04
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/runTrigger")
	public void runTrigger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(request, quartzTriggerDto);
		quartzService.runTrigger(quartzTriggerDto);
		message(response,"quartz_run_success",MESSAGE_INFO);
	}
}
