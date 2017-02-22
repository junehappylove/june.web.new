/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dto.back.login;

import com.june.common.PageDTO;

public class ButtonDto extends PageDTO<ButtonDto> {

	private static final long serialVersionUID = -147792280064499369L;
	private String buttonId;//按钮id
	private String firstModuleId;//一级模块id
	private String secondModuleId;//二级模块id
	private String thirdModuleId;//三级模块id
	private String buttonPageId;//页面上按钮的id属性
	private String buttonName;//按钮名称
	private String buttonFunction;//按钮功能
	private String buttonUrl;//按钮的url
	private String roleId;//角色id
	private String menuUrl;//页面url
	
	public String getButtonId() {
		return buttonId;
	}
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	public String getFirstModuleId() {
		return firstModuleId;
	}
	public void setFirstModuleId(String firstModuleId) {
		this.firstModuleId = firstModuleId;
	}
	public String getSecondModuleId() {
		return secondModuleId;
	}
	public void setSecondModuleId(String secondModuleId) {
		this.secondModuleId = secondModuleId;
	}
	public String getThirdModuleId() {
		return thirdModuleId;
	}
	public void setThirdModuleId(String thirdModuleId) {
		this.thirdModuleId = thirdModuleId;
	}
	public String getButtonPageId() {
		return buttonPageId;
	}
	public void setButtonPageId(String buttonPageId) {
		this.buttonPageId = buttonPageId;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public String getButtonFunction() {
		return buttonFunction;
	}
	public void setButtonFunction(String buttonFunction) {
		this.buttonFunction = buttonFunction;
	}
	public String getButtonUrl() {
		return buttonUrl;
	}
	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	@Override
	protected String getDtoName() {
		return "按钮";
	}
	
}
