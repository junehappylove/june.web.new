/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.controller.back.portal.myinfo;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;
import com.june.service.back.portal.myinfo.MyInfoService;
import com.june.service.back.portal.releaseinfo.ReleaseInfoService;
import com.june.utility.MessageUtil;

/**  
* @Description: 我的资讯用controller
* @author  caiyang
* @date 2015年11月2日 下午3:13:48 
* @version V1.0  
 */
@Controller
@RequestMapping("/portal/MyInfo/")
public class MyInfoController extends BaseController{
	
	/**
	 * 我的资讯用service注入
	 */
	@Autowired
	private MyInfoService myInfoService;
	
	/**
	 * 咨询发布用service注入
	 */
	@Autowired
	private ReleaseInfoService releaseInfoService;
	/**   
	 * @Description: 我的资讯页面初始化
	 * @author caiyang
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws   
	 */
	@RequestMapping("/MyInfo")
	public ModelAndView initMyInfo()
	{
		ModelAndView result = new ModelAndView("portal/myInfo/myInfo");
		List<ReleaseInfoDto> channelList = releaseInfoService.getChannels();
		result.addObject("channelList", channelList);
		return result;
	}
	
	/**   
	 * @Description: 表格数据初始化
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/search")
	@MethodLog(module="我的资讯",remark="查询当前登录用户发布的资讯",operateType=Constants.OPERATE_TYPE_SEARCH)
	public void search(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		//将参数映射到dto中
		fillRequestDto(httpServletRequest, releaseInfoDto);
		releaseInfoDto = myInfoService.search(releaseInfoDto);
		JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	@RequestMapping("/submitRow")
	@MethodLog(module="我的资讯",remark="提交资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void submitRow(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		myInfoService.submitRow(releaseInfoDto);
		MessageDto messageDto = new MessageDto();
        //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.formatMessage("content_submit_success"));
        messageDto.setErrList(errList);
        messageDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(messageDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	@RequestMapping("/editRow")
	public ModelAndView editRow(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		String contentId = httpServletRequest.getParameter("contentId");
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		releaseInfoDto.setContentId(contentId);
		//根据id获取文章信息
		releaseInfoDto = myInfoService.getInfoById(releaseInfoDto);
		List<ReleaseInfoDto> channelList = releaseInfoService.getChannels();
		ModelAndView result = new ModelAndView("portal/myInfo/popEditMyInfo");
		//设置页面属性值
		result.addObject("channelList", channelList);
		result.addObject("contentId",releaseInfoDto.getContentId());
		result.addObject("channelId", releaseInfoDto.getChannelId());
		result.addObject("commentState", releaseInfoDto.getCommentState());
		result.addObject("title", releaseInfoDto.getTitle());
		result.addObject("summary", releaseInfoDto.getSummary());
		result.addObject("contentText", releaseInfoDto.getContentText());
		return result;
	}
	
	/**
	 * @throws Exception    
	 * @Description: 修改资讯pop画面保存操作
	 * @author	caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse    
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/saveContent")
	@MethodLog(module="我的资讯",remark="保存修改资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void saveContent(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{

		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		//设置评论状态0可评论，1不可评论
		if (releaseInfoDto.getCommentState() != null && releaseInfoDto.getCommentState().equals("true")) {
			releaseInfoDto.setCommentState("0");
		}else{
			releaseInfoDto.setCommentState("1");
		}
		//修改更新操作
		releaseInfoService.updateContent(releaseInfoDto);;
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.formatMessage("content_change_success"));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**
	 * @throws Exception    
	 * @Description: 修改资讯pop画面提交操作
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/submitContent")
	@MethodLog(module="我的资讯",remark="提交修改资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void submitContent(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		//设置评论状态0可评论，1不可评论
		if (releaseInfoDto.getCommentState() != null && releaseInfoDto.getCommentState().equals("true")) {
			releaseInfoDto.setCommentState("0");
		}else{
			releaseInfoDto.setCommentState("1");
		}
		releaseInfoService.submitNoDirect(releaseInfoDto);
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.formatMessage("content_submit_success"));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**   
	 * @Description: 点击行预览触发的操作
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws   
	 */
	@RequestMapping("/checkRow")
	public ModelAndView checkRow(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		String contentId = httpServletRequest.getParameter("contentId");
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		releaseInfoDto.setContentId(contentId);
		//根据id获取文章信息
		releaseInfoDto = myInfoService.getInfoById(releaseInfoDto);
		ModelAndView result = new ModelAndView("portal/myInfo/popViewMyInfo");
		result.addObject("content", releaseInfoDto);
		return result;
	}
	
	/**
	 * @throws Exception    
	 * @Description: 删除未提交的资讯
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/deleteRow")
	@MethodLog(module="我的资讯",remark="删除资讯",operateType=Constants.OPERATE_TYPE_DELETE)
	public void deleteRow(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		String contentId = httpServletRequest.getParameter("contentId");
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		releaseInfoDto.setContentId(contentId);
		myInfoService.deleteRow(releaseInfoDto);
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.formatMessage("content_delete_success"));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**
	 * @throws Exception    
	 * @Description: 批量提交资讯
	 * @author	caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/batchSubmit")
	@MethodLog(module="我的资讯",remark="批量提交资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void batchSubmit(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		String contentId = releaseInfoDto.getContentId();
		for (int i = 0; i < contentId.split(",").length; i++) {
			releaseInfoDto.setContentId(contentId.split(",")[i]);
			myInfoService.submitRow(releaseInfoDto);
		}
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.formatMessage("content_submit_success"));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**   
	 * @Description: 批量删除资讯
	 * @author	caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/batchDel")
	@MethodLog(module="我的资讯",remark="批量删除资讯",operateType=Constants.OPERATE_TYPE_DELETE)
	public void batchDel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		String contentId = releaseInfoDto.getContentId();
		for (int i = 0; i < contentId.split(",").length; i++) {
			releaseInfoDto.setContentId(contentId.split(",")[i]);
			myInfoService.deleteRow(releaseInfoDto);
		}
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.formatMessage("content_delete_success"));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
}
