/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.controller.back.system.base.org.SysOrgController.java
 * 日期:2016年12月15日
 */
package com.june.controller.back.system.base.org;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.common.Constants;
import com.june.common.MessageDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.common.TreeDto;
import com.june.dto.back.system.base.SysOrgDto;
import com.june.service.back.system.base.org.SysOrgService;

/**
 * 组织 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 上午2:56:33
 */
@Controller
@RequestMapping("/system/org")
public class SysOrgController extends BaseController<SysOrgDto> {

	@Autowired
	protected SysOrgService sysOrgService;
	/**
	 * 组织信息页面初始化
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月10日 下午3:36:52
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/")
	@MethodLog(module = "组织管理", remark = "组织信息页面初始化", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView InitUserManagement(HttpServletRequest request) {
		return initPage(request,"system/base/org/orginfo");
	}
	
	@RequestMapping("/view/{id}")
	@MethodLog(module = "组织管理", remark = "组织信息查看页面", operateType = Constants.OPERATE_TYPE_VIEW)
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,@PathVariable String id) {
		ModelAndView result = null;
		result = new ModelAndView("system/base/org/view");
		SysOrgDto org = sysOrgService.getDtoById(new SysOrgDto(id));
		result.addObject("org", org);
		return result;
	}

	/**
	 * 获取组织信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月10日 下午3:35:44
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getPagedList")
	@MethodLog(module = "组织管理", remark = "分页获取组织信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getSysOrgs(HttpServletRequest request, HttpServletResponse response) {
		SysOrgDto sysOrgDto = new SysOrgDto();
		fillRequestDto(request, sysOrgDto);
		sysOrgDto = sysOrgService.getPagedDtos(sysOrgDto);
		toJson(sysOrgDto, response);
	}
	
	@RequestMapping("/getAllList")
	@MethodLog(module = "组织管理", remark = "获取所有组织信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getAllList(HttpServletRequest request, HttpServletResponse response) {
		SysOrgDto sysOrgDto = new SysOrgDto();
		fillRequestDto(request, sysOrgDto);
		List<SysOrgDto> list = sysOrgService.getDtos(sysOrgDto);
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
	@MethodLog(module = "组织管理", remark = "查看详细，编辑触发事件", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysOrgDto sysOrgDto = new SysOrgDto();
		fillRequestDto(request, sysOrgDto);
		String name = sysOrgDto.getDtoName();
		sysOrgDto = sysOrgService.getDtoById(sysOrgDto);
		// 判断组织信息是否为空
		if (sysOrgDto == null) {
			message(response,"info_not_exist", MESSAGE_ERRO, name);
		} else {
			toJson(sysOrgDto, response);
		}
	}
	
	@RequestMapping("/viewDetail")
	@MethodLog(module = "组织管理", remark = "组织信息查看", operateType = Constants.OPERATE_TYPE_VIEW)
	public void viewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysOrgDto sysOrgDto = new SysOrgDto();
		fillRequestDto(request, sysOrgDto);
		String name = sysOrgDto.getDtoName();
		sysOrgDto = sysOrgService.getDtoById(sysOrgDto);
		// 判断组织信息是否为空
		if (sysOrgDto == null) {
			message(response,"info_not_exist", MESSAGE_ERRO, name);
		} else {
			toJson(sysOrgDto, response);
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
	@MethodLog(module = "组织管理", remark = "初始化组织树", operateType = Constants.OPERATE_TYPE_SEARCH)
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
	@MethodLog(module = "组织管理", remark = "初始化角色树", operateType = Constants.OPERATE_TYPE_SEARCH)
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
	 * 保存新增组织
	 * 
	 * @param request
	 * @param response
	 * @param sysOrgDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午3:37:35
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "组织管理", remark = "保存新增组织", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid SysOrgDto sysOrgDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该组织不存在的情况
			if (sysOrgService.getDtoById(sysOrgDto) == null) {
				sysOrgService.addDto(sysOrgDto);// 添加组织
				message(response,"new_save_success", MESSAGE_INFO, sysOrgDto.getDtoName());
			} else {
				// 组织存在的情况返回消息
				message(response,"error_info_exist", MESSAGE_ERRO, sysOrgDto.getDtoName(),sysOrgDto.getUserId());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存组织信息
	 * 
	 * @param request
	 * @param response
	 * @param sysOrgDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午3:37:42
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "组织管理", remark = "编辑保存组织信息", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid SysOrgDto sysOrgDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (sysOrgService.getDtoById(sysOrgDto) == null) {
				// 组织不存在的情况返回消息
				message(response,"error_info_not_exist", MESSAGE_ERRO,sysOrgDto.getDtoName(), sysOrgDto.getUserId());
			} else {
				// 组织存在的情况进行更新
				sysOrgService.updateDtoById(sysOrgDto);// 组织信息更新
				message(response,"info_edit_success", MESSAGE_INFO, sysOrgDto.getDtoName());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	@RequestMapping("/deleteSelected")
	@MethodLog(module = "组织管理", remark = "删除组织信息", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			SysOrgDto sysOrgDto) throws Exception {
		// 选中的组织id
		String orgIds = sysOrgDto.getUserId();
		if (orgIds != null) {
			for (int i = 0; i < orgIds.split(",").length; i++) {
				sysOrgDto.setUserId(orgIds.split(",")[i]);
				sysOrgService.deleteDtoById(sysOrgDto);
			}
		}
		message(response,"info_delete_success", MESSAGE_INFO);
	}
}
