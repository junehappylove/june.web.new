/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.controller.back.system.base.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.common.validate.ValidateEdit;
import com.june.common.validate.ValidateInsert;
import com.june.dto.back.common.TreeDto;
import com.june.dto.back.login.ButtonDto;
import com.june.dto.back.system.base.MenuInfoDto;
import com.june.dto.back.system.base.RoleInfoDto;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.service.back.common.CommonService;
import com.june.service.back.system.base.role.RoleService;
import com.june.utility.MessageUtil;

import net.sf.json.JSONObject;

/**
 * 角色管理用controller <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月18日 下午2:03:39
 */
@Controller
@RequestMapping("/system/base/role")
public class RoleController extends BaseController<RoleInfoDto> {

	/**
	 * 角色管理用service注入
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 共通业务service注入
	 */
	@Autowired
	protected CommonService commonService;

	/**
	 * form表单后台验证
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @return
	 * @throws Exception
	 * @date 2016年5月18日 下午2:03:28
	 * @writer wjw.happy.love@163.com
	 */
	@ModelAttribute
	public AbstractDTO validateForm(HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws Exception {
		// 将参数映射到对应的业务dto中并返回
		RoleInfoDto roleInfoDto = new RoleInfoDto();
		fillRequestDto(httpServletRequest, roleInfoDto);
		return roleInfoDto;
	}

	/**
	 * 角色管理画面初期画
	 * 
	 * @param httpServletRequest
	 * @return
	 * @date 2016年5月18日 下午2:03:20
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/")
	public ModelAndView init(HttpServletRequest httpServletRequest) {
		ModelAndView result = null;
		result = new ModelAndView("system/base/role/role");
		// 获取用户信息
		UserInfoDto userInfoDto = loginUser(httpServletRequest);
		ButtonDto buttonDto = new ButtonDto();
		if (userInfoDto != null) {
			buttonDto.setRoleId(userInfoDto.getRoleId());
			buttonDto.setMenuUrl(httpServletRequest.getServletPath());
			// 根据用户角色和初始化的页面获取 该页面有权限的操作
			List<ButtonDto> list = commonService.getFunctionByRole(buttonDto);
			for (int i = 0; i < list.size(); i++) {
				result.addObject(list.get(i).getButtonPageId(), "hasAuthority");
			}
		}
		return result;
	}

	/**
	 * 获取表格数据
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @date 2016年5月18日 下午2:03:12
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getRoleInfo")
	@MethodLog(module = "角色管理", remark = "查询角色信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getRoleInfo(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		RoleInfoDto roleInfoDto = new RoleInfoDto();
		// 页面检索条件映射到dto中
		fillRequestDto(httpServletRequest, roleInfoDto);
		roleInfoDto = roleService.getPagedDtos(roleInfoDto);
		JSONObject jsonObject = JSONObject.fromObject(roleInfoDto);
		ConvetDtoToJson(response, jsonObject);
		roleInfoDto = null;
	}

	/**
	 * 增加角色
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @param roleInfoDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月18日 下午2:02:44
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveAddRole")
	public void saveAddRole(HttpServletRequest httpServletRequest, HttpServletResponse response,
			@Validated({ ValidateInsert.class }) RoleInfoDto roleInfoDto, BindingResult bindingResult)
					throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			messageDto = new MessageDto();
			// 判断角色名称是否已经存在，存在返回错误消息，否则插入
			RoleInfoDto roleInfoDtoByName = roleService.getRoleByRoleName(roleInfoDto);
			if (roleInfoDtoByName != null) {
				// 返回消息 start
				ArrayList<String> errList = new ArrayList<String>();
				errList.add(MessageUtil.formatMessage("role_exist_error",
						new String[] { roleInfoDtoByName.getRoleName() }));
				messageDto.setErrList(errList);
				messageDto.setErrType("error");
				// 返回消息 end
			} else {
				roleService.addDto(roleInfoDto);
				// 返回消息 start
				ArrayList<String> errList = new ArrayList<String>();
				errList.add(MessageUtil.formatMessage("save_new_success", new String[] { "角色" }));
				messageDto.setErrList(errList);
				messageDto.setErrType("info");
				// 返回消息 end
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(messageDto);
		ConvetDtoToJson(response, jsonObject);
	}

	/**
	 * 获取选中行的详细信息
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @param roleInfoDto
	 * @throws Exception
	 * @date 2016年5月18日 下午2:02:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/checkDetail")
	public void checkDetail(HttpServletRequest httpServletRequest, HttpServletResponse response,
			RoleInfoDto roleInfoDto) throws Exception {
		if (roleInfoDto.getRoleId().isEmpty()) {
			ArrayList<String> errList = new ArrayList<String>();
			errList.add(MessageUtil.formatMessage("not_empty_error", new String[] { "角色ID" }));
			roleInfoDto.setErrList(errList);
			roleInfoDto.setErrType("error");
		} else {
			String roleName = roleInfoDto.getRoleName();
			roleInfoDto = roleService.getRoleByRoleId(roleInfoDto.getRoleId());
			if (roleInfoDto == null) {
				ArrayList<String> errList = new ArrayList<String>();
				roleInfoDto = new RoleInfoDto();
				errList.add(MessageUtil.formatMessage("role_not_exist", new String[] { roleName }));
				roleInfoDto.setErrList(errList);
				roleInfoDto.setErrType("error");
			}
			JSONObject jsonObject = JSONObject.fromObject(roleInfoDto);
			ConvetDtoToJson(response, jsonObject);
		}

	}

	/**
	 * 编辑页面保存
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @param roleInfoDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月18日 下午2:02:27
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEditRole")
	public void saveEditRole(HttpServletRequest httpServletRequest, HttpServletResponse response,
			@Validated({ ValidateEdit.class }) RoleInfoDto roleInfoDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 判断角色名称是否已经存在，存在返回错误消息，否则更新
			RoleInfoDto roleInfoDtoByName = roleService.getRoleByRoleName(roleInfoDto);
			if (roleInfoDtoByName != null) {
				throwMessage(response,"role_exist_error",  MESSAGE_ERRO, roleInfoDto.getRoleName());
			} else {
				roleService.updateDtoById(roleInfoDto);
				throwMessage(response,"role_edit_success", MESSAGE_INFO);
			}
		} else {
			toJson(messageDto, response);
		}
	}

	/**
	 * 删除选中的行
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @param roleInfoDto
	 * @throws Exception
	 * @date 2016年5月18日 下午2:02:14
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/delRole")
	public void delRole(HttpServletRequest httpServletRequest, HttpServletResponse response,
			RoleInfoDto roleInfoDto) throws Exception {
		String roleId = roleInfoDto.getRoleId();
		MessageDto messageDto = new MessageDto();// 返回消息dto
		if (roleId.isEmpty()) {
			ArrayList<String> errList = new ArrayList<String>();
			errList.add(MessageUtil.formatMessage("not_empty_error", new String[] { "角色ID" }));
			messageDto.setErrList(errList);
			messageDto.setErrType("error");
		} else {
			ArrayList<String> errList = new ArrayList<String>();// 返回消息list
			String errorString = "";// 删除未成功的角色
			String successString = "";// 删除成功的角色
			for (int i = 0; i < roleId.split(",").length; i++) {
				roleInfoDto.setRoleId(roleId.split(",")[i]);
				// 删除前先判断该角色是否已经赋给用户，如果已经赋给用户则不能直接删除，需要先将拥有该角色的用户删除，才能删除该角色
				List<MenuInfoDto> list = roleService.selectUserByRoleId(roleInfoDto.getRoleId());
				if (list != null && list.size() > 0) {
					errorString = errorString + "【" + list.get(0).getRoleName() + "】";
				} else {
					RoleInfoDto roleInfoDto2 = roleService.getRoleByRoleId(roleInfoDto.getRoleId());
					if (roleInfoDto2 != null) {
						successString = successString + "【" + roleInfoDto2.getRoleName() + "】";
					}
					roleService.deleteRoleById(roleInfoDto.getRoleId());
				}
			}
			if (!errorString.equals("")) {
				errList.add(MessageUtil.formatMessage("delete_role_error", new String[] { errorString }));
			}
			if (!successString.equals("")) {
				errList.add(MessageUtil.formatMessage("delete_role_success", new String[] { successString }));
			}
			messageDto.setErrList(errList);
			if (!errorString.equals("")) {
				messageDto.setErrType("warning");
			} else {
				messageDto.setErrType("info");
			}
		}
		toJson(messageDto, response);
	}

	/**
	 * 初始化角色树
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @date 2016年5月18日 下午2:04:03
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/initMenuTree")
	public void initRoleTree(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		TreeDto treeDto = new TreeDto();
		fillRequestDto(httpServletRequest, treeDto);
		// 获取用户已经拥有的角色
		List<TreeDto> list = roleService.getMenus(treeDto.getRoleId());
		toJson(list, response);
	}

	/**
	 * 进行角色赋权限操作
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @throws Exception
	 * @date 2016年5月18日 下午2:04:10
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/authorityMenus")
	public void authorityMenus(HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws Exception {
		MenuInfoDto menuInfoDto = new MenuInfoDto();
		fillRequestDto(httpServletRequest, menuInfoDto);
		// 页面选中的菜单和button的id
		String authorityMenus = menuInfoDto.getAuthorityMenusId();
		String menuIds = getMenus(authorityMenus);
		String buttonIds = getButtons(authorityMenus);

		// 更新操作之前查询角色的菜单权限
		String originalauthorityMenus = roleService.getauthorityofRole(menuInfoDto.getRoleId());
		// 更新操作之前查询角色的按钮权限
		String originalauthorityButtons = roleService.getButtonauthorityOfRole(menuInfoDto.getRoleId());
		// 获取需要添加的菜单权限
		String addAuthority = compareAddAuthority(menuIds, originalauthorityMenus);
		// 获取需要删除的菜单权限
		String delAuthority = compareDelAuthority(menuIds, originalauthorityMenus);
		// 需要添加的按钮权限
		String addButtons = compareAddAuthority(buttonIds, originalauthorityButtons);
		// 需要删除的按钮权限
		String delButtons = compareDelAuthority(buttonIds, originalauthorityButtons);
		// 添加菜单权限
		if (addAuthority != null && !addAuthority.equals("")) {
			for (int i = 0; i < addAuthority.split(",").length; i++) {
				menuInfoDto.setAuthorityMenusId(addAuthority.split(",")[i]);
				roleService.authorityMenu(menuInfoDto);
			}
		}
		// 删除菜单权限
		if (delAuthority != null && !delAuthority.equals("")) {
			for (int i = 0; i < delAuthority.split(",").length; i++) {
				menuInfoDto.setAuthorityMenusId(delAuthority.split(",")[i]);
				roleService.delAuthorityMenu(menuInfoDto);
			}
		}

		// 添加按钮权限
		if (addButtons != null && !addButtons.equals("")) {
			for (int i = 0; i < addButtons.split(",").length; i++) {
				menuInfoDto.setAuthorityMenusId(addButtons.split(",")[i]);
				roleService.authorityButton(menuInfoDto);
			}
		}

		// 删除按钮权限
		if (delButtons != null && !delButtons.equals("")) {
			for (int i = 0; i < delButtons.split(",").length; i++) {
				menuInfoDto.setAuthorityMenusId(delButtons.split(",")[i]);
				roleService.delAuthorityButton(menuInfoDto);
			}
		}

		throwMessage(response,"authority_success", MESSAGE_INFO, "角色");
	}

	/**
	 * 比较页面传过来的权限和用户原来权限，取出新增的权限菜单
	 * 
	 * @param authorityFromPage
	 * @param authorityOriginal
	 * @return
	 * @date 2016年5月18日 下午2:04:25
	 * @writer wjw.happy.love@163.com
	 */
	private String compareAddAuthority(String authorityFromPage, String authorityOriginal) {
		// 如果页面传过来的为空，则不需要进行比较选出需要添加的权限
		if (authorityFromPage == null || authorityFromPage.equals("")) {
			return "";
		}
		// 如果原来的权限为空，则将页面权限全部返回
		else if (authorityOriginal == null || authorityOriginal.equals("")) {
			return authorityFromPage;
		}
		// 否则进行比较返回需要进行添加操作
		else {
			return stringCompare(authorityFromPage, authorityOriginal);
		}
	}

	/**
	 * 比较页面传过来的权限和用户原来权限，取出需要删除的权限菜单
	 * 
	 * @param authorityFromPage
	 * @param authorityOriginal
	 * @return
	 * @date 2016年5月18日 下午2:04:32
	 * @writer wjw.happy.love@163.com
	 */
	private String compareDelAuthority(String authorityFromPage, String authorityOriginal) {
		// 如果页面传过来的为空，则需要将权限全部删除
		if (authorityFromPage == null || authorityFromPage.equals("")) {
			return authorityOriginal;
		}
		// 如果原来的权限为空个，则不需比较，直接返回空值即可
		else if (authorityOriginal == null || authorityOriginal.equals("")) {
			return "";
		}
		// 否则进行比较
		else {
			return stringCompare(authorityOriginal, authorityFromPage);
		}
	}

	/**
	 * 比较两个字符串，字符串用逗号分隔，取出其中的不同
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 * @date 2016年5月18日 下午2:04:40
	 * @writer wjw.happy.love@163.com
	 */
	private String stringCompare(String str1, String str2) {
		String[] arr1 = str1.split(",");
		String[] arr2 = str2.split(",");
		for (int i = 0; i < arr2.length; i++) {
			for (int j = 0; j < arr1.length; j++) {
				if (arr1[j].equals(arr2[i])) {
					arr1[j] = "";
				}
			}
		}

		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < arr1.length; j++) {
			if (!"".equals(arr1[j])) {
				sb.append(arr1[j] + ",");
			}
		}

		return sb.toString();
	}

	/**
	 * 用户初始化
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @date 2016年5月18日 下午2:04:47
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/initUserTree")
	public void initUserTree(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		TreeDto treeDto = new TreeDto();
		fillRequestDto(httpServletRequest, treeDto);
		treeDto.setId("-1");
		treeDto.setName("用户");
		treeDto.setOpen("true");
		List<TreeDto> userTree = roleService.getUsers(treeDto.getRoleId());
		treeDto.setChildren(userTree);
		toJson(treeDto, response);
	}

	/**
	 * 将角色分配给用户
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @throws Exception
	 * @date 2016年5月18日 下午2:04:53
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/assginUsers")
	public void assginUsers(HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws Exception {
		MenuInfoDto menuInfoDto = new MenuInfoDto();
		fillRequestDto(httpServletRequest, menuInfoDto);

		String roleId = menuInfoDto.getRoleId();// 获取页面传过来的roleId
		String userId = menuInfoDto.getAuthorityMenusId();// 获取页面选中的userId
		// 更新操作之前获取拥有该角色的用户
		String originalUsers = roleService.getUsersByRoleId(roleId);
		// 获取需要添加的用户角色
		String addUserRoles = compareAddAuthority(userId, originalUsers);
		String delUserRoles = compareDelAuthority(userId, originalUsers);
		// 添加用户角色
		if (!addUserRoles.isEmpty()) {
			for (int i = 0; i < addUserRoles.split(",").length; i++) {
				menuInfoDto.setAuthorityMenusId(addUserRoles.split(",")[i]);
				roleService.addUserRole(menuInfoDto);
			}
		}
		if (!delUserRoles.isEmpty()) {
			for (int i = 0; i < delUserRoles.split(",").length; i++) {
				menuInfoDto.setAuthorityMenusId(delUserRoles.split(",")[i]);
				roleService.delUserRole(menuInfoDto);
			}
		}
		throwMessage(response,"assign_userrole_success", MESSAGE_INFO);
	}

	/**
	 * 获取授权菜单的id
	 * 
	 * @param authority
	 * @return
	 * @date 2016年5月18日 下午2:05:02
	 * @writer wjw.happy.love@163.com
	 */
	private String getMenus(String authority) {
		String menuIds = "";
		if (authority.contains(",")) {
			for (int i = 0; i < authority.split(",").length; i++) {
				if (!authority.split(",")[i].contains("button")) {
					menuIds = menuIds + authority.split(",")[i] + ",";
				}
			}
		} else {
			if (!authority.contains("button")) {
				menuIds = authority;
			}
		}
		return menuIds;
	}

	/**
	 * 获取授权按钮的id
	 * 
	 * @param authority
	 * @return
	 * @date 2016年5月18日 下午2:05:07
	 * @writer wjw.happy.love@163.com
	 */
	private String getButtons(String authority) {
		String buttonIds = "";
		if (authority.contains(",")) {
			for (int i = 0; i < authority.split(",").length; i++) {
				if (authority.split(",")[i].contains("button")) {
					buttonIds = buttonIds + authority.split(",")[i].substring(6) + ",";
				}
			}
		} else {
			if (authority.contains("button")) {
				buttonIds = authority.substring(6);
			}
		}
		return buttonIds;
	}
}
