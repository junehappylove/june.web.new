/**
 * june_web_new:com.june.controller.back.system.base.menu.SysMenuController.java
 * Created by junehappylove on 2016/12/22
 */
package com.june.controller.back.system.base.menu;

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
import com.june.dto.back.system.base.SysMenuDto;
import com.june.service.back.system.base.menu.SysMenuService;

/**
 * SysMenuController:系统菜单 <br>
 *
 * @author 王俊伟 junehappylove(wjw.happy.love@163.com)
 * @blog https://www.github.com/junehappylove
 * @date 2016-12-22 18:10
 */
@Controller
@RequestMapping("/system/base/menu")
public class SysMenuController extends BaseController<SysMenuDto> {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/")
    @MethodLog(module = "系统菜单", remark = "系统菜单页面初始化", operateType = Constants.OPERATE_TYPE_INIT)
    public ModelAndView init(HttpServletRequest request) {
        return initPage(request,"system/base/menu/menu");
    }

	@RequestMapping("/view/{id}")
	@MethodLog(module = "系统菜单", remark = "系统菜单信息查看页面", operateType = Constants.OPERATE_TYPE_VIEW)
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response,@PathVariable String id) {
		ModelAndView result = null;
		result = new ModelAndView("system/base/menu/view");
		SysMenuDto menu = new SysMenuDto();
		menu.setMenu_id(id);
		menu = sysMenuService.getDtoById(menu);
		result.addObject("menu", menu);
		return result;
	}

	@RequestMapping("/getPagedList")
	@MethodLog(module = "系统菜单", remark = "分页获取系统菜单信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getPagedList(HttpServletRequest request, HttpServletResponse response) {
		SysMenuDto sysMenuDto = new SysMenuDto();
		fillRequestDto(request, sysMenuDto);
		sysMenuDto = sysMenuService.getPagedDtos(sysMenuDto);
		toJson(sysMenuDto, response);
	} 
	
	@RequestMapping("/getAllList")
	@MethodLog(module = "系统菜单", remark = "获取所有系统菜单信息", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void getAllList(HttpServletRequest request, HttpServletResponse response) {
		SysMenuDto menu = new SysMenuDto();
		fillRequestDto(request, menu);
		List<SysMenuDto> list = sysMenuService.getDtos(menu);
		toJson(list, response);
	}
	
	@RequestMapping("/checkDetail")
	@MethodLog(module = "系统菜单", remark = "查看详细，编辑触发事件", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void checkDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysMenuDto menu = new SysMenuDto();
		fillRequestDto(request, menu);
		String name = menu.getDtoName();
		menu = sysMenuService.getDtoById(menu);
		// 判断系统菜单信息是否为空
		if (menu == null) {
			message(response,"info_not_exist", MESSAGE_ERRO, name);
		} else {
			toJson(menu, response);
		}
	}
	
	@RequestMapping("/viewDetail")
	@MethodLog(module = "系统菜单", remark = "系统菜单信息查看", operateType = Constants.OPERATE_TYPE_VIEW)
	public void viewDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.checkDetail(request,response);
	}
	
	@Override
	@RequestMapping("/initRoleTree")
	@MethodLog(module = "系统菜单", remark = "初始化角色树", operateType = Constants.OPERATE_TYPE_SEARCH)
	public void initRoleTree(HttpServletRequest request, HttpServletResponse response) {
		super.initRoleTree(request,response);
	}
	
	@RequestMapping("/newSave")
	@MethodLog(module = "系统菜单", remark = "保存新增系统菜单", operateType = Constants.OPERATE_TYPE_ADD)
	public void newSave(HttpServletRequest request, HttpServletResponse response,
			@Valid SysMenuDto menu, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			// 该系统菜单不存在的情况
			if (sysMenuService.getDtoById(menu) == null) {
				sysMenuService.addDto(menu);// 添加系统菜单
				message(response,"new_save_success", MESSAGE_INFO, menu.getDtoName());
			} else {
				// 系统菜单存在的情况返回消息
				message(response,"error_info_exist", MESSAGE_ERRO, menu.getDtoName(),menu.getMenu_name());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}
	
	@RequestMapping("/saveEdit")
	@MethodLog(module = "系统菜单", remark = "编辑保存系统菜单信息", operateType = Constants.OPERATE_TYPE_UPDATE)
	public void saveEdit(HttpServletRequest request, HttpServletResponse response,
			@Valid SysMenuDto menu, BindingResult bindingResult) throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);// 将校验消息存放到messagedto中
		if (StringUtils.isEmpty(messageDto.getErrType())) {
			if (sysMenuService.getDtoById(menu) == null) {
				// 系统菜单不存在的情况返回消息
				message(response,"error_info_not_exist", MESSAGE_ERRO,menu.getDtoName(), menu.getMenu_name());
			} else {
				// 系统菜单存在的情况进行更新
				sysMenuService.updateDtoById(menu);// 系统菜单信息更新
				message(response,"info_edit_success", MESSAGE_INFO, menu.getDtoName());
			}
		} else {
			// 有错误返回
			toJson(messageDto, response);
		}
	}
	
	@RequestMapping("/deleteSelected")
	@MethodLog(module = "系统菜单", remark = "删除系统菜单信息", operateType = Constants.OPERATE_TYPE_DELETE)
	public void deleteSelected(HttpServletRequest request, HttpServletResponse response,
			SysMenuDto menu) throws Exception {
		String ids = menu.getUserId();
		if (ids != null) {
			for (int i = 0; i < ids.split(",").length; i++) {
				menu.setUserId(ids.split(",")[i]);
				sysMenuService.deleteDtoById(menu);
			}
		}
		message(response,"info_delete_success", MESSAGE_INFO);
	}
}

