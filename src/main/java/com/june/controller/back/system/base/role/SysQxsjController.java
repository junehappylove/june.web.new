/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.controller.back.system.base.role.SysQxsjController.java
 * 日期:2016年12月15日
 */
package com.june.controller.back.system.base.role;

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
import com.june.dto.back.system.base.SysQxsjDto;
import com.june.service.back.system.base.role.SysQxsjService;

/**
 * SysQxsjController <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 下午10:50:22
 */
@Controller
@RequestMapping("/system/base/qxsj")
public class SysQxsjController extends BaseController<SysQxsjDto> {

	@Autowired
	private SysQxsjService qxsjService;
	
	/**
	 * 权限设计信息页面初始化
	 * 
	 * @param request
	 * @return
	 * @date 2016年5月10日 下午3:36:52
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/")
	@MethodLog(module = "权限设计", remark = "权限设计信息页面初始化", operateType = Constants.OPERATE_TYPE_SEARCH)
	public ModelAndView init(HttpServletRequest request) {
		return initPage(request,"system/base/role/qxsj");
	}
	
	@RequestMapping("/view/{id}")
	@MethodLog(module = "权限设计", remark = "权限设计信息查看页面", operateType = Constants.OPERATE_TYPE_VIEW)
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,@PathVariable String id) {
		ModelAndView result = null;
		result = new ModelAndView("system/base/role/view");
		SysQxsjDto qxsj = new SysQxsjDto();
		qxsj.setQxsj_code(id);
		qxsj = qxsjService.getDtoById(qxsj);
		result.addObject("qxsj", qxsj);
		return result;
	}

	/**
	 * 获取权限设计信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月10日 下午3:35:44
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getPagedList")
	@MethodLog(module = "权限设计", remark = "分页获取权限设计信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getSysOrgs(HttpServletRequest request, HttpServletResponse response) {
		SysQxsjDto sysQxsjDto = new SysQxsjDto();
		fillRequestDto(request, sysQxsjDto);
		sysQxsjDto = qxsjService.getPagedDtos(sysQxsjDto);
		toJson(sysQxsjDto, response);
	}
	
	@RequestMapping("/getAllList")
	@MethodLog(module = "权限设计", remark = "获取所有权限设计信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getAllList(HttpServletRequest request, HttpServletResponse response) {
		SysQxsjDto sysQxsjDto = new SysQxsjDto();
		fillRequestDto(request, sysQxsjDto);
		List<SysQxsjDto> list = qxsjService.getDtos(sysQxsjDto);
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
	@MethodLog(module = "权限设计", remark = "查看详细，编辑触发事件", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysQxsjDto sysQxsjDto = new SysQxsjDto();
		fillRequestDto(request, sysQxsjDto);
		String name = sysQxsjDto.getDtoName();
		sysQxsjDto = qxsjService.getDtoById(sysQxsjDto);
		// 判断权限设计信息是否为空
		if (sysQxsjDto == null) {
			message(response,"info_not_exist", MESSAGE_ERRO, name);
		} else {
			toJson(sysQxsjDto, response);
		}
	}
	
	@RequestMapping("/viewDetail")
	@MethodLog(module = "权限设计", remark = "权限设计信息查看", operateType = Constants.OPERATE_TYPE_VIEW)
	public void viewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysQxsjDto sysQxsjDto = new SysQxsjDto();
		fillRequestDto(request, sysQxsjDto);
		String name = sysQxsjDto.getDtoName();
		sysQxsjDto = qxsjService.getDtoById(sysQxsjDto);
		// 判断权限设计信息是否为空
		if (sysQxsjDto == null) {
			message(response,"info_not_exist", MESSAGE_ERRO, name);
		} else {
			toJson(sysQxsjDto, response);
		}
	}

	/**
	 * 权限设计树初始化
	 * 
	 * @param request
	 * @param response
	 * @date 2016年5月10日 下午3:37:21
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/initOrgTree")
	@MethodLog(module = "权限设计", remark = "初始化权限设计树", operateType = Constants.OPERATE_TYPE_SEARCH)
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
	@MethodLog(module = "权限设计", remark = "初始化角色树", operateType = Constants.OPERATE_TYPE_SEARCH)
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
	 * 保存新增权限设计
	 * 
	 * @param request
	 * @param response
	 * @param sysQxsjDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午3:37:35
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/newSave")
	@MethodLog(module = "权限设计", remark = "保存新增权限设计", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid SysQxsjDto sysQxsjDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该权限设计不存在的情况
			if (qxsjService.getDtoById(sysQxsjDto) == null) {
				qxsjService.addDto(sysQxsjDto);// 添加权限设计
				message(response,"new_save_success", MESSAGE_INFO, sysQxsjDto.getDtoName());
			} else {
				// 权限设计存在的情况返回消息
				message(response,"error_info_exist", MESSAGE_ERRO, sysQxsjDto.getDtoName(),sysQxsjDto.getUserId());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	/**
	 * 编辑保存权限设计信息
	 * 
	 * @param request
	 * @param response
	 * @param sysQxsjDto
	 * @param bindingResult
	 * @throws Exception
	 * @date 2016年5月10日 下午3:37:42
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/saveEdit")
	@MethodLog(module = "权限设计", remark = "编辑保存权限设计信息", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid SysQxsjDto sysQxsjDto, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (qxsjService.getDtoById(sysQxsjDto) == null) {
				// 权限设计不存在的情况返回消息
				message(response,"error_info_not_exist", MESSAGE_ERRO,sysQxsjDto.getDtoName(), sysQxsjDto.getUserId());
			} else {
				// 权限设计存在的情况进行更新
				qxsjService.updateDtoById(sysQxsjDto);// 权限设计信息更新
				message(response,"info_edit_success", MESSAGE_INFO, sysQxsjDto.getDtoName());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}

	@RequestMapping("/deleteSelected")
	@MethodLog(module = "权限设计", remark = "删除权限设计信息", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			SysQxsjDto sysQxsjDto) throws Exception {
		// 选中的权限设计id
		String qxsjIds = sysQxsjDto.getUserId();
		if (qxsjIds != null) {
			for (int i = 0; i < qxsjIds.split(",").length; i++) {
				sysQxsjDto.setUserId(qxsjIds.split(",")[i]);
				qxsjService.deleteDtoById(sysQxsjDto);
			}
		}
		message(response,"info_delete_success", MESSAGE_INFO);
	}
}
