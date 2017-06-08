/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.dto.back.system.base;

import com.june.common.PageDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限设计分配菜单使用
 * MenuInfoDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月16日 下午7:40:20
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MenuInfoDto extends PageDTO<MenuInfoDto> {

	private static final long serialVersionUID = 1000L;
	private String id;// 菜单id
	private String text;// 菜单名
	private String roleId;// 角色id
	private String authorityMenusId;// 页面上传过来的需要赋权限的id
	private String roleName;// 角色名
	private String buttonId;// 按钮id
	/** 一级菜单id. */
	private String firstMenuId;
	/** 二级菜单id. */
	private String secondMenuId;
	/** 三级菜单id. */
	private String thirdMenuId;

	@Override
	protected String getDtoName() {
		return "菜单";
	}

}
