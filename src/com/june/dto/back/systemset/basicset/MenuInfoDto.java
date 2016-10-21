/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.dto.back.systemset.basicset;

import java.io.Serializable;

import com.june.common.PageDTO;

/**
 * 
 * 分配菜单用dto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月20日 下午12:16:45
 */
public class MenuInfoDto extends PageDTO<MenuInfoDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -8510087054445180342L;
	private String id;// 菜单id
	private String text;// 菜单名
	private String roleId;// 角色id
	private String authorityMenusId;// 页面上传过来的需要赋权限的id
	private String roleName;// 角色名
	private String buttonId;// 按钮id
	/**
	 * 一级菜单id.
	 */
	private String firstMenuId;

	/**
	 * 二级菜单id.
	 */
	private String secondMenuId;

	/**
	 * 三级菜单id.
	 */
	private String thirdMenuId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getAuthorityMenusId() {
		return authorityMenusId;
	}

	public void setAuthorityMenusId(String authorityMenusId) {
		this.authorityMenusId = authorityMenusId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	/**
	 * 得到firstMenuId.
	 * 
	 * @return firstMenuId.
	 */
	public String getFirstMenuId() {
		return firstMenuId;
	}

	/**
	 * 设置firstMenuId.
	 * 
	 * @param firstMenuId.
	 */
	public void setFirstMenuId(String firstMenuId) {
		this.firstMenuId = firstMenuId;
	}

	/**
	 * 得到secondMenuId.
	 * 
	 * @return secondMenuId.
	 */
	public String getSecondMenuId() {
		return secondMenuId;
	}

	/**
	 * 设置secondMenuId.
	 * 
	 * @param secondMenuId.
	 */
	public void setSecondMenuId(String secondMenuId) {
		this.secondMenuId = secondMenuId;
	}

	/**
	 * 得到thirdMenuId.
	 * 
	 * @return thirdMenuId.
	 */
	public String getThirdMenuId() {
		return thirdMenuId;
	}

	/**
	 * 设置thirdMenuId.
	 * 
	 * @param thirdMenuId.
	 */
	public void setThirdMenuId(String thirdMenuId) {
		this.thirdMenuId = thirdMenuId;
	}

}
