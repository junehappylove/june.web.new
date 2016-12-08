/*   

 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.controller.back.portal.checkinfo;

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
import com.june.common.MethodLog;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;
import com.june.service.back.portal.checkinfo.CheckInfoService;
import com.june.service.back.portal.myinfo.MyInfoService;
import com.june.service.back.portal.releaseinfo.ReleaseInfoService;
import com.june.utility.MessageUtil;

/**  
* @Description: 审核资讯用controller
* @author caiyang
* @date 2015年11月3日 下午1:50:19 
* @version V1.0  
 */
@Controller
@RequestMapping("/portal/CheckInfo")
public class CheckInfoController extends BaseController{

	/**
	 * 审核资讯用service注入
	 */
	@Autowired
	private CheckInfoService checkInfoService;
	
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
	 * @Description: 审核资讯页面初始化
	 * @author	caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/CheckInfo")
	public ModelAndView init(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ModelAndView result = new ModelAndView("portal/checkInfo/checkInfo");
		List<ReleaseInfoDto> channelList = releaseInfoService.getChannels();
		result.addObject("channelList", channelList);
		return result;
	}
	
	/**   
	 * @Description: 页面表格数据初始化
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/search")
	@MethodLog(module="审核资讯",remark="查询待审核资讯",operateType=Constants.OPERATE_TYPE_SEARCH)
	public void search(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		//将参数映射到dto中
		fillRequestDto(httpServletRequest, releaseInfoDto);
		releaseInfoDto = checkInfoService.search(releaseInfoDto);
		JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**
	 * @throws Exception    
	 * @Description: 审核通过操作
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/checkPass")
	@MethodLog(module="审核资讯",remark="通过待审核资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void checkPass(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		checkInfoService.checkPass(releaseInfoDto);
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.getFormatMessage("content_checkpass_success", null));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	@RequestMapping("/checkBack")
	@MethodLog(module="审核资讯",remark="驳回待审核资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void checkBack(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		checkInfoService.checkBack(releaseInfoDto);
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.getFormatMessage("content_checkback_success", null));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**   
	 * @Description: 预览资讯操作
	 * @author	caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws   
	 */
	@RequestMapping("/checkView")
	public ModelAndView checkView(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		String contentId = httpServletRequest.getParameter("contentId");
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		releaseInfoDto.setContentId(contentId);
		//根据id获取文章信息
		releaseInfoDto = myInfoService.getInfoById(releaseInfoDto);
		ModelAndView result = new ModelAndView("portal/checkInfo/popViewCheckInfo");
		result.addObject("content", releaseInfoDto);
		return result;
	}
	
	/**   
	 * @Description: 批量通过审核操作
	 * @author caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse
	 * @param: @throws Exception      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("/batchCheckPass")
	@MethodLog(module="审核资讯",remark="批量通过待审核资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void batchCheckPass(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		String contentId = releaseInfoDto.getContentId();
		for (int i = 0; i < contentId.split(",").length; i++) {
			releaseInfoDto.setContentId(contentId.split(",")[i]);
			checkInfoService.checkPass(releaseInfoDto);
		}
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.getFormatMessage("content_checkpass_success", null));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	/**
	 * @throws Exception    
	 * @Description: 批量驳回资讯操作
	 * @author  caiyang
	 * @param: @param httpServletRequest
	 * @param: @param httpServletResponse      
	 * @return: void      
	 * @throws   
	 */
	@RequestMapping("batchCheckBack")
	@MethodLog(module="审核资讯",remark="批量驳回待审核资讯",operateType=Constants.OPERATE_TYPE_UPDATE)
	public void batchCheckBack(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception
	{
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(httpServletRequest, releaseInfoDto);
		String contentId = releaseInfoDto.getContentId();
		for (int i = 0; i < contentId.split(",").length; i++) {
			releaseInfoDto.setContentId(contentId.split(",")[i]);
			checkInfoService.checkBack(releaseInfoDto);
		}
		 //返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
        errList.add(MessageUtil.getFormatMessage("content_checkback_success", null));
        releaseInfoDto.setErrList(errList);
        releaseInfoDto.setErrType("info");
        //返回消息 end
        JSONObject jsonObject = JSONObject.fromObject(releaseInfoDto);
        ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
}
