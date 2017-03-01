/**
 * june_web_new:com.june.controller.back.system.base.menu.SysMenuController.java
 * Created by junehappylove on 2016/12/22
 */
package com.june.controller.back.system.base.menu;

import java.util.ArrayList;
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
import com.june.common.TreeDto;
import com.june.common.annotation.MethodLog;
import com.june.dto.back.system.base.FunctionDto;
import com.june.dto.back.system.base.SysMenuDto;
import com.june.dto.back.system.base.SysQxsjDto;
import com.june.service.back.system.base.menu.FunctionService;
import com.june.service.back.system.base.menu.SysMenuService;
import com.june.service.back.system.base.role.SysQxsjService;

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
    @Autowired
    private FunctionService functionService;
    @Autowired
    private SysQxsjService qxsjService;

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
				sysMenuService.updateDto(menu);// 系统菜单信息更新
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
		sysMenuService.deleteDtoByIds(menu);
		message(response,"info_delete_success", MESSAGE_INFO);
	}

	/**
	 * 初始化第一级菜单树
	 * @param request
	 * @param response
	 * @date 2017年2月16日 下午8:03:31
	 * @writer junehappylove
	 */
	@RequestMapping("/initMenuTree")
	protected void initMenuTree(HttpServletRequest request, HttpServletResponse response){
		TreeDto tree = new TreeDto();
		tree.setPid("0");//设置父节点 
		tree.setTree(sysMenuService.getTree(tree));
		toJson(tree.getTree(), response);
	}
	
	@RequestMapping("/childMenu")
	protected void childMenu(HttpServletRequest request, HttpServletResponse response){
		TreeDto tree = new TreeDto();
		fillRequestDto(request, tree);
		tree.setPid(tree.getId());//设置父节点 
		tree.setTree(sysMenuService.getTree(tree));
		toJson(tree.getTree(), response);
	}
	
	@RequestMapping("/selectQxsj/{menuId}")
	protected ModelAndView selectQxsj(HttpServletRequest request, HttpServletResponse response,@PathVariable String menuId){
		ModelAndView result = null;
		result = new ModelAndView("system/base/menu/qxsj_btn");
		SysMenuDto menu = new SysMenuDto();
		menu.setMenu_id(menuId);
		menu = sysMenuService.getDtoById(menu);
		result.addObject("menu", menu);
		return result;
	}
	
	/**
	 * 添加菜单权限按钮
	 * 
	 * @param request
	 * @param response
	 * @param menu
	 * @throws Exception
	 * @date 2017年2月18日 上午2:51:45
	 * @writer junehappylove
	 */
	@RequestMapping("/menuQxsjSave")
	protected void menuQxsjSave(HttpServletRequest request, HttpServletResponse response,SysMenuDto menu) throws Exception{
		String idss = menu.getIds();//权限code
		String menuId = menu.getMenu_id();//取当前操作的菜单id
		String pid = null;
		if(StringUtils.isNotEmpty(menuId)){
			menu = sysMenuService.getDtoById(menu);//这条菜单详情
			pid = menu.getParent_menu_id();
		}
		FunctionDto function = null;
		SysQxsjDto qxsj = new SysQxsjDto();
		List<FunctionDto> list = null;
		List<FunctionDto> list2 = null;
		if(StringUtils.isNotEmpty(idss)){
			list = new ArrayList<>();
			function = new FunctionDto();
			function.setBtn_3_id(menuId);//设置菜单id用于查询
			list2 = functionService.getDtos(function);//查询已经存在的权限功能列表
			String[] ids = idss.split(",");
			for (String code : ids) {
				function = new FunctionDto();
				qxsj.setQxsj_code(code);
				qxsj = qxsjService.getDtoById(qxsj);//取此条权限实体
				function.setBtn_3_id(menuId);
				function.setBtn_1_id(pid);
				function.setBtn_2_id(pid);//TODO 找二级菜单id
				function.setBtn_page_id(code);//权限码
				function.setBtn_name(qxsj.getQxsj_name());//
				function.setBtn_func(qxsj.getQxsj_name());
				function.setBtn_url(menu.getMenu_url());//
				super.filluser(function);
				if (!exitFunction(function, list2)) {
					list.add(function);
				} // 不存在才添加
			}
			//批量添加
			functionService.addList(list);
			messageSaveSuccess(response);
		}else{
			message(response, "menu_code_null", MESSAGE_ERRO);
		}
	}
	
	/**
	 * 删除按钮
	 * 
	 * @param request
	 * @param response
	 * @param menu
	 * @throws Exception
	 * @date 2017年2月18日 上午3:59:50
	 * @writer junehappylove
	 */
	@RequestMapping("/menuQxsjDelete")
	protected void menuQxsjDelete(HttpServletRequest request, HttpServletResponse response,SysMenuDto menu) throws Exception{
		String idss = menu.getIds();//权限code
		String menuId = menu.getMenu_id();//取当前操作的菜单id
		
		FunctionDto function = null;
		FunctionDto fun = null;
		List<FunctionDto> list = null;//deletes
		List<FunctionDto> btnlist = null;
		if(StringUtils.isNotEmpty(idss)){
			list = new ArrayList<>();
			function = new FunctionDto();
			function.setBtn_3_id(menuId);//设置菜单id用于查询
			btnlist = functionService.getDtos(function);//查询已经存在的权限功能列表
			String[] ids = idss.split(",");
			for (String code : ids) {
				fun = new FunctionDto();
				fun.setBtn_3_id(menuId);
				fun.setBtn_page_id(code);//权限码
				if(exitFunction(fun, btnlist)){
					list.add(fun);
				}//存在这条数据才去删除
			}
			// 批量删除
			functionService.deleteList(list);
			// TODO 删除权限按钮sys_function还应该关联删除sys_role_function的相关关系记录
			messageDeleteSuccess(response);
		}else{
			message(response, "menu_code_null", MESSAGE_ERRO);
		}
	}
	/**
	 * 判断在功能表中，这个功能是否存在
	 * @param function
	 * @param list
	 * @return
	 * @date 2017年2月18日 上午3:07:55
	 * @writer junehappylove
	 */
	private boolean exitFunction(FunctionDto function, List<FunctionDto> list) {
		boolean ret = false;
		if (list == null) {
			return false;
		}
		if (list.size() == 0) {
			return false;
		}
		if (function == null) {
			return true;
		}
		for (FunctionDto temp : list) {
			if (temp.getBtn_3_id().equals(function.getBtn_3_id())
					&& temp.getBtn_page_id().equals(function.getBtn_page_id())) {
				function.setAppid(temp.getBtn_id());//XXX 删除时候用的的主键 	注意 引用传递
				function.setBtn_id(temp.getBtn_id());//XXX 删除时候用到的主键
				ret = true;
				break;
			} else {
				ret = false;
			}
		}
		return ret;
	}
}

