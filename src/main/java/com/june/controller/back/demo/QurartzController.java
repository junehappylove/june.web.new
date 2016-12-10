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
import com.june.common.PageDTO;
import com.june.dto.back.demo.QuartzTriggerDto;
import com.june.service.back.demo.QuartzService;
import com.june.utility.MessageUtil;

import net.sf.json.JSONObject;

/**
 * 
 * 定时任务用controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年10月21日 下午6:23:11
 */
// @Controller
// @RequestMapping("/quatrz")
public class QurartzController extends BaseController {

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
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:23:26
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getquartzlist")
	public void getquartzlist(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
		quartzTriggerDto = quartzService.getPageList(quartzTriggerDto);
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}

	/**
	 * 暂停任务
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:23:32
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/pause")
	public void pause(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
		quartzService.pause(quartzTriggerDto.getTriggerName(), quartzTriggerDto.getTriggerGroup());
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.getResourceValue("quartz_pause_success"));
		quartzTriggerDto.setErrList(errList);
		quartzTriggerDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}

	/**
	 * 恢复任务
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:23:37
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/resume")
	public void resume(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
		quartzService.resume(quartzTriggerDto.getTriggerName(), quartzTriggerDto.getTriggerGroup());
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.getResourceValue("quartz_resume_success"));
		quartzTriggerDto.setErrList(errList);
		quartzTriggerDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}

	@RequestMapping("/remove")
	public void remove(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
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
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}

	/**
	 * 添加simpletrigger
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:23:45
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/addsimpletrigger")
	public void addsimpletrigger(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
		quartzService.addSimpleTrigger(quartzTriggerDto);
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.formatMessage("quartz_add_success", new String[] { "simpleTrigger" }));
		quartzTriggerDto.setErrList(errList);
		quartzTriggerDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}

	/**
	 * 获取添加modal的初始化数据
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:23:51
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getinitdata")
	public void getinitdata(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		List<QuartzTriggerDto> jobNames = quartzService.getAllJobName();
		PageDTO<QuartzTriggerDto> pageDTO = new PageDTO<QuartzTriggerDto>();
		pageDTO.setRows(jobNames);
		JSONObject jsonObject = JSONObject.fromObject(pageDTO);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		pageDTO = null;
	}

	/**
	 * 根据cron表达式添加crontrigger
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:23:58
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/addcrontrigger")
	public void addCrontrigger(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
		quartzService.addCrontrigger(quartzTriggerDto);
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.formatMessage("quartz_add_success", new String[] { "cronTrigger" }));
		quartzTriggerDto.setErrList(errList);
		quartzTriggerDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}

	/**
	 * 立即执行一次
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:24:04
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/runTrigger")
	public void runTrigger(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		QuartzTriggerDto quartzTriggerDto = new QuartzTriggerDto();
		fillRequestDto(httpServletRequest, quartzTriggerDto);
		quartzService.runTrigger(quartzTriggerDto);
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.formatMessage("quartz_run_success"));
		quartzTriggerDto.setErrList(errList);
		quartzTriggerDto.setErrType("info");
		// 返回消息 end
		JSONObject jsonObject = JSONObject.fromObject(quartzTriggerDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
		quartzTriggerDto = null;
	}
}
