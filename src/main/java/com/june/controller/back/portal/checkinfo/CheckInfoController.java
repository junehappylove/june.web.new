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
import com.june.utility.MessageUtil;

/**
 * @Description: 审核资讯用controller
 * @author caiyang
 * @date 2015年11月3日 下午1:50:19
 * @version V1.0
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
	 * @Description: 审核资讯页面初始化 @author caiyang @param: @param
	 *               request @param: @param
	 *               response @return: void @throws
	 */
	@RequestMapping("/CheckInfo")
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("portal/checkInfo/checkInfo");
		List<ReleaseInfoDto> channelList = releaseInfoService.getChannels();
		result.addObject("channelList", channelList);
		return result;
	}

	/**
	 * @Description: 页面表格数据初始化 @author caiyang @param: @param
	 *               request @param: @param
	 *               response @return: void @throws
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
	 * @throws Exception
	 * 			@Description: 审核通过操作 @author caiyang @param: @param
	 *             request @param: @param
	 *             response @return: void @throws
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
		errList.add(MessageUtil.formatMessage("content_checkpass_success"));
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
		errList.add(MessageUtil.formatMessage("content_checkback_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

	/**
	 * @Description: 预览资讯操作 @author caiyang @param: @param
	 *               request @param: @param
	 *               response @param: @return @return:
	 *               ModelAndView @throws
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
	 * @Description: 批量通过审核操作 @author caiyang @param: @param
	 *               request @param: @param
	 *               response @param: @throws Exception @return:
	 *               void @throws
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
		errList.add(MessageUtil.formatMessage("content_checkpass_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

	/**
	 * @throws Exception
	 * 			@Description: 批量驳回资讯操作 @author caiyang @param: @param
	 *             request @param: @param
	 *             response @return: void @throws
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
		errList.add(MessageUtil.formatMessage("content_checkback_success"));
		releaseInfoDto.setErrList(errList);
		releaseInfoDto.setErrType("info");
		// 返回消息 end
		toJson(releaseInfoDto, response);
	}

}
