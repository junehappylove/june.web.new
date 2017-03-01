/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 
package com.june.controller.back.portal.releaseinfo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.Message;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;
import com.june.service.back.portal.releaseinfo.ReleaseInfoService;

@Controller
@RequestMapping("/portal/ReleaseInfo/")
public class ReleaseInfoController extends BaseController<ReleaseInfoDto>{

	/**
	 * 咨询发布用service注入
	 */
	@Autowired
	private ReleaseInfoService releaseInfoService;
	
	/**
	 * 发布咨询页面初始化
	 * @return
	 * @date 2016年12月22日 下午8:39:35
	 * @writer junehappylove
	 */
	@RequestMapping("/ReleaseInfo/")
	public ModelAndView releaseInfoInit()
	{
		ModelAndView result = new ModelAndView("portal/releaseInfo/releaseInfo");
		List<ReleaseInfoDto> channelList = releaseInfoService.getChannels();
		result.addObject("channelList", channelList);
		return result;
	}
	
	/**
	 * 保存资讯操作
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年12月22日 下午8:39:19
	 * @writer junehappylove
	 */
	@RequestMapping("/saveContent")
	@MethodLog(module="发布资讯",remark="保存资讯",operateType=Constants.OPERATE_TYPE_ADD)
	public void saveContent(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(request, releaseInfoDto);
		//设置评论状态0可评论，1不可评论
		if (releaseInfoDto.getCommentState() != null && releaseInfoDto.getCommentState().equals("true")) {
			releaseInfoDto.setCommentState("0");
		}else{
			releaseInfoDto.setCommentState("1");
		}
		//初次保存操作
		if (releaseInfoDto.getContentId() == null || releaseInfoDto.getContentId().equals("")) {
			int contentId = releaseInfoService.getMaxContentId();
			releaseInfoDto.setContentId(Integer.toString(contentId));
			//文章插入
			releaseInfoService.saveContent(releaseInfoDto);
			 //返回消息 start
			ArrayList<String> errList = new ArrayList<String>();
	        errList.add(Message.$VALUE("content_save_success"));
	        releaseInfoDto.setErrList(errList);
	        releaseInfoDto.setErrType("info");
	        //返回消息 end
		}
		else{//修改更新操作
			releaseInfoService.updateContent(releaseInfoDto);
			 //返回消息 start
			ArrayList<String> errList = new ArrayList<String>();
	        errList.add(Message.$VALUE("content_save_success"));
	        releaseInfoDto.setErrList(errList);
	        releaseInfoDto.setErrType("info");
	        //返回消息 end
		}
        toJson(releaseInfoDto, response);
	}
	
	/**
	 * 提交资讯操作
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年12月22日 下午8:39:09
	 * @writer junehappylove
	 */
	@RequestMapping("/submitContent")
	@MethodLog(module="发布资讯",remark="提交资讯",operateType=Constants.OPERATE_TYPE_ADD)
	public void submitContent(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(request, releaseInfoDto);
		//设置评论状态0可评论，1不可评论
		if (releaseInfoDto.getCommentState() != null && releaseInfoDto.getCommentState().equals("true")) {
			releaseInfoDto.setCommentState("0");
		}else{
			releaseInfoDto.setCommentState("1");
		}
		//未保存直接提交
		if (releaseInfoDto.getContentId().equals("") || releaseInfoDto.getChannelId() == null) {
			int contentId = releaseInfoService.getMaxContentId();
			releaseInfoDto.setContentId(Integer.toString(contentId));
			releaseInfoService.submitDirect(releaseInfoDto);
	        message(response,"content_submit_success",MESSAGE_INFO);
		}else {//先进行保存再提交
			releaseInfoService.submitNoDirect(releaseInfoDto);
			 //返回消息 start
			ArrayList<String> errList = new ArrayList<String>();
	        errList.add(Message.$VALUE("content_submit_success"));
	        releaseInfoDto.setErrList(errList);
	        releaseInfoDto.setErrType("info");
	        //返回消息 end
	        message(response,"content_submit_success",MESSAGE_INFO);
		}
		toJson(releaseInfoDto, response);
	}
}
