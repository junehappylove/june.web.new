package com.june.controller.back.system.base.user;

import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.JspPage;
import com.june.common.MessageDto;
import com.june.common.TreeDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.service.back.system.base.user.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 
 * 用户信息模块用controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 上午2:48:48
 */
@Controller
@RequestMapping("/system/user")
public class UserInfoController extends BaseController<UserInfoDto> {

	private final JspPage page = new JspPage("system/base/user/userinfo","user");
	/**
	 * 用户信息Service
	 */
	@Autowired
	protected UserInfoService userInfoService;

	/**
	 * 用户信息页面初始化
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月10日 下午3:36:52
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/")
	@MethodLog(module = "用户管理", remark = "用户信息页面初始化", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView InitUserManagement(HttpServletRequest request) {
		return initPage(request, page);
	}
	
	@RequestMapping("/view/{id}")
	@MethodLog(module = "用户管理", remark = "用户信息查看页面", operateType = Constants.OPERATE_TYPE_VIEW)
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,@PathVariable String id) {
		ModelAndView result = null;
		result = new ModelAndView("system/base/user/view");
		UserInfoDto user = userInfoService.getDtoById(new UserInfoDto(id));
		result.addObject("user", user);
		return result;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月10日 下午3:35:44
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getPagedList")
	@MethodLog(module = "用户管理", remark = "分页获取用户信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getUserInfos(HttpServletRequest request, HttpServletResponse response) {
		UserInfoDto userInfoDto = new UserInfoDto();
		fillRequestDto(request, userInfoDto);
		userInfoDto = userInfoService.getPagedDtos(userInfoDto);
		toJson(userInfoDto, response);
	}
	
	@RequestMapping("/getAllList")
	@MethodLog(module = "用户管理", remark = "获取所有用户信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getAllList(HttpServletRequest request, HttpServletResponse response) {
		UserInfoDto userInfoDto = new UserInfoDto();
		fillRequestDto(request, userInfoDto);
		List<UserInfoDto> list = userInfoService.getDtos(userInfoDto);
		toJson(list, response);
	}

	/**
	 * 查看详细，编辑触发事件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @date 2016年5月10日 下午3:36:30
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/checkDetail")
	@MethodLog(module = "用户管理", remark = "查看详细，编辑触发事件", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoDto userInfoDto = new UserInfoDto();
		fillRequestDto(request, userInfoDto);
		String name = userInfoDto.getDtoName();
		userInfoDto = userInfoService.getDtoById(userInfoDto);
		// 判断用户信息是否为空
		if (userInfoDto == null) {
			message(response,"info_not_exist", MESSAGE_ERRO, name);
		} else {
			toJson(userInfoDto, response);
		}
	}
	
	@RequestMapping("/viewDetail")
	@MethodLog(module = "用户管理", remark = "用户信息查看", operateType = Constants.OPERATE_TYPE_VIEW)
	public void viewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.checkDetail(request,response);
	}

	/**
	 * 组织树初始化
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月10日 下午3:37:21
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/initOrgTree")
	@MethodLog(module = "用户管理", remark = "初始化组织树", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void initOrgTree(HttpServletRequest request, HttpServletResponse response) {
		List<TreeDto> list = commonService.getOrgTree();
		toJson(list, response);
	}

	/**
	 * 初始化角色树
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月10日 下午3:37:27
	 * @writer wjw.happy.love@163.com
	 */
	@Override
	@RequestMapping("/initRoleTree")
	@MethodLog(module = "用户管理", remark = "初始化角色树", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void initRoleTree(HttpServletRequest request, HttpServletResponse response) {
		super.initRoleTree(request,response);
	}

	/**
	 * 保存新增用户
	 * 
	 * @param request
	 * @param response
	 * @param userInfoDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午3:37:35
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "用户管理", remark = "保存新增用户", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid UserInfoDto userInfoDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该用户不存在的情况
			if (userInfoService.getDtoById(userInfoDto) == null) {
				userInfoService.addDto(userInfoDto);// 添加用户
				userInfoService.addUserRoleInfo(userInfoDto);// 添加用户角色
				message(response,"new_save_success", MESSAGE_INFO, userInfoDto.getDtoName());
			} else {
				// 用户存在的情况返回消息
				message(response,"error_user_exist", MESSAGE_ERRO,userInfoDto.getUserId());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存用户信息
	 * 
	 * @param request
	 * @param response
	 * @param userInfoDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午3:37:42
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "用户管理", remark = "编辑保存用户信息", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid UserInfoDto userInfoDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (userInfoService.getDtoById(userInfoDto) == null) {
				// 用户不存在的情况返回消息
				message(response,"error_info_not_exist", MESSAGE_ERRO,userInfoDto.getDtoName(), userInfoDto.getUserId());
			} else {
				// 用户存在的情况进行更新
				userInfoService.updateDto(userInfoDto);// 用户信息更新
				userInfoService.updateUserRoleInfo(userInfoDto);// 用户角色更新
				message(response,"info_edit_success", MESSAGE_INFO, userInfoDto.getDtoName());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	@RequestMapping("/deleteSelected")
	@MethodLog(module = "用户管理", remark = "删除用户信息", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			UserInfoDto userInfoDto) throws Exception {
		// 选中的用户id
		String userIds = userInfoDto.getUserId();
		if (userIds != null) {
			for (int i = 0; i < userIds.split(",").length; i++) {
				userInfoDto.setUserId(userIds.split(",")[i]);
				userInfoService.deleteDto(userInfoDto);
			}
		}
		message(response,"user_delete_success", MESSAGE_INFO);
	}

}
