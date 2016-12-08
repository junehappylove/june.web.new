package com.june.controller.back.system.basicset.userinfo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.MethodLog;
import com.june.dto.back.bussiness.vehicle.VehicleUser;
import com.june.dto.back.common.TreeDto;
import com.june.dto.back.login.ButtonDto;
import com.june.dto.back.system.basicset.UserInfoDto;
import com.june.service.back.bussiness.vehicle.VehicleUserService;
import com.june.service.back.system.basicset.userinfo.UserInfoService;

/**
 * @Description: 用户信息模块用controller
 * @author liren
 * @date 2015年11月09日
 * @version V1.0
 */
@Controller
@RequestMapping("/SystemSet/UserInfo")
public class UserInfoController extends BaseController {

	/**
	 * 用户信息Service
	 */
	@Autowired
	protected UserInfoService userInfoService;
	@Autowired
	protected VehicleUserService vehicleUserService;

	/**
	 * form表单后台验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @date 2016年5月10日 下午3:36:44
	 * @writer wjw.happy.love@163.com
	 */
	@ModelAttribute
	public AbstractDTO validateForm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 将参数映射到对应的业务dto中并返回
		UserInfoDto userInfoDto = new UserInfoDto();
		fillRequestDto(request, userInfoDto);
		return userInfoDto;
	}

	/**
	 * 用户信息页面初始化
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月10日 下午3:36:52
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/BasicInfo/")
	@MethodLog(module = "用户管理", remark = "用户信息页面初始化", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView InitUserManagement(HttpServletRequest request) {
		ModelAndView result = null;
		result = new ModelAndView("system/basicset/userinfo/basicInfo");
		// 获取用户信息
		UserInfoDto userInfoDto = loginUser(request);
		ButtonDto buttonDto = new ButtonDto();
		if (userInfoDto != null) {
			buttonDto.setRoleId(userInfoDto.getRoleId());
			buttonDto.setMenuUrl(request.getServletPath());
			// 根据用户角色和初始化的页面获取该页面有权限的操作
			List<ButtonDto> list = commonService.getFunctionByRole(buttonDto);
			for (int i = 0; i < list.size(); i++) {
				result.addObject(list.get(i).getButtonPageId(), "hasAuthority");
			}
		}

		return result;
	}
	
	@RequestMapping("/view/{id}")
	@MethodLog(module = "用户管理", remark = "用户信息查看页面", operateType = Constants.OPERATE_TYPE_VIEW)
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,@PathVariable String id) {
		ModelAndView result = null;
		result = new ModelAndView("system/basicset/userinfo/view");
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
	@RequestMapping("/getUserInfos")
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
		userInfoDto = userInfoService.getDtoById(userInfoDto);
		// 判断用户信息是否为空
		if (userInfoDto == null) {
			throwMessage("userinfo_not_exist", MESSAGE_ERRO, response);
		} else {
			toJson(userInfoDto, response);
		}
	}
	
	@RequestMapping("/viewDetail")
	@MethodLog(module = "用户管理", remark = "用户信息查看", operateType = Constants.OPERATE_TYPE_VIEW)
	public void viewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoDto userInfoDto = new UserInfoDto();
		fillRequestDto(request, userInfoDto);
		userInfoDto = userInfoService.getDtoById(userInfoDto);
		// 判断用户信息是否为空
		if (userInfoDto == null) {
			throwMessage("userinfo_not_exist", MESSAGE_ERRO, response);
		} else {
			toJson(userInfoDto, response);
		}
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
	@RequestMapping("/initRoleTree")
	@MethodLog(module = "用户管理", remark = "初始化角色树", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void initRoleTree(HttpServletRequest request, HttpServletResponse response) {
		TreeDto treeDto = new TreeDto();
		fillRequestDto(request, treeDto);
		String[] roleIds = treeDto.getId().split(",");
		List<TreeDto> list = commonService.getRole();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < roleIds.length; j++) {
				if (list.get(i).getId().equals(roleIds[j])) {
					list.get(i).setChecked(true);
					break;
				}
			}
		}
		toJson(list, response);
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
				throwMessage("newuser_save_success", MESSAGE_INFO, response);
			} else {
				// 用户存在的情况返回消息
				throwMessage("error_user_exist", new String[] { userInfoDto.getUserId() }, MESSAGE_ERRO, response);
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
				throwMessage("error_user_not_exist", new String[] { userInfoDto.getUserId() }, MESSAGE_ERRO, response);
			} else {
				// 用户存在的情况进行更新
				userInfoService.updateDtoById(userInfoDto);// 用户信息更新
				userInfoService.updateUserRoleInfo(userInfoDto);// 用户角色更新
				throwMessage("user_edit_success", MESSAGE_INFO, response);
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
				userInfoService.deleteDtoById(userInfoDto);
			}
		}
		throwMessage("user_delete_success", MESSAGE_INFO, response);
	}
	
	/**
	 * 用户跟车型关联列表
	 * @param request
	 * @param response
	 * @date 2016年7月3日 下午1:59:09
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/userVehicle")
	public void userVehicle(HttpServletRequest request, HttpServletResponse response) {
		VehicleUser vehicleUser = new VehicleUser();
		fillRequestDto(request, vehicleUser);
		vehicleUser = vehicleUserService.noPagedDtos(vehicleUser);
		toJson(vehicleUser, response);
	}

}
