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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.portal.ReleaseInfo.ReleaseInfoDto;
import com.june.service.back.portal.checkinfo.CheckInfoService;
import com.june.service.back.portal.myinfo.MyInfoService;
import com.june.service.back.portal.releaseinfo.ReleaseInfoService;
import com.june.util.MessageUtil;

/**
 * 审核资讯用controller
 * CheckInfoController <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月22日 下午8:37:03
 * @version 1.0.0
 */
@Controller
@RequestMapping("/portal/CheckInfo")
public class CheckInfoController extends BaseController<ReleaseInfoDto> {

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
	 * 审核资讯页面初始化
	 * @param request
	 * @param response
	 * @return
	 * @date 2016年12月22日 下午8:37:13
	 * @writer junehappylove
	 */
	@RequestMapping("/CheckInfo")
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("portal/checkInfo/checkInfo");
		List<ReleaseInfoDto> channelList = releaseInfoService.getChannels();
		result.addObject("channelList", channelList);
		return result;
	}

	/**
	 * 页面表格数据初始化
	 * @param request
	 * @param response
	 * @date 2016年12月22日 下午8:37:20
	 * @writer junehappylove
	 */
	@RequestMapping("/search")
	@MethodLog(module = "审核资讯", remark = "查询待审核资讯", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void search(HttpServletRequest request, HttpServletResponse response) {
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		// 将参数映射到dto中
		fillRequestDto(request, releaseInfoDto);
		releaseInfoDto = checkInfoService.search(releaseInfoDto);
		toJson(releaseInfoDto, response);
	}

	/**
	 * 审核通过操作
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年12月22日 下午8:37:28
	 * @writer junehappylove
	 */
	@RequestMapping("/checkPass")
	@MethodLog(module = "审核资讯", remark = "通过待审核资讯", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkPass(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(request, releaseInfoDto);
		checkInfoService.checkPass(releaseInfoDto);
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.$VALUE("content_checkpass_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

	@RequestMapping("/checkBack")
	@MethodLog(module = "审核资讯", remark = "驳回待审核资讯", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkBack(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(request, releaseInfoDto);
		checkInfoService.checkBack(releaseInfoDto);
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.$VALUE("content_checkback_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

	/**
	 * 预览资讯操作 
	 * @param request
	 * @param response
	 * @return
	 * @date 2016年12月22日 下午8:37:38
	 * @writer junehappylove
	 */
	@RequestMapping("/checkView")
	public ModelAndView checkView(HttpServletRequest request, HttpServletResponse response) {
		String contentId = request.getParameter("contentId");
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		releaseInfoDto.setContentId(contentId);
		// 根据id获取文章信息
		releaseInfoDto = myInfoService.getInfoById(releaseInfoDto);
		ModelAndView result = new ModelAndView("portal/checkInfo/popViewCheckInfo");
		result.addObject("content", releaseInfoDto);
		return result;
	}

	/**
	 * 批量通过审核操作
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年12月22日 下午8:37:44
	 * @writer junehappylove
	 */
	@RequestMapping("/batchCheckPass")
	@MethodLog(module = "审核资讯", remark = "批量通过待审核资讯", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void batchCheckPass(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(request, releaseInfoDto);
		String contentId = releaseInfoDto.getContentId();
		for (int i = 0; i < contentId.split(",").length; i++) {
			releaseInfoDto.setContentId(contentId.split(",")[i]);
			checkInfoService.checkPass(releaseInfoDto);
		}
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.$VALUE("content_checkpass_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

	/**
	 * 批量驳回资讯操作
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年12月22日 下午8:37:51
	 * @writer junehappylove
	 */
	@RequestMapping("batchCheckBack")
	@MethodLog(module = "审核资讯", remark = "批量驳回待审核资讯", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void batchCheckBack(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
		fillRequestDto(request, releaseInfoDto);
		String contentId = releaseInfoDto.getContentId();
		for (int i = 0; i < contentId.split(",").length; i++) {
			releaseInfoDto.setContentId(contentId.split(",")[i]);
			checkInfoService.checkBack(releaseInfoDto);
		}
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.$VALUE("content_checkback_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

}
