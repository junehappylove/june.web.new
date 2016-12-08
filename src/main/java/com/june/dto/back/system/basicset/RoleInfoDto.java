/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dto.back.system.basicset;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.PageDTO;
import com.june.common.validate.ValidateEdit;
import com.june.common.validate.ValidateInsert;

/**  
* @Description: 角色dto
* @author	caiyang
* @date 2015年11月10日 上午8:44:32 
* @version V1.0  
 */
public class RoleInfoDto extends PageDTO<RoleInfoDto> implements Serializable{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2086342917622727433L;
	@NotNull(message="{not_empty_error};角色ID",groups={ValidateEdit.class})
	@NotEmpty(message="{not_empty_error};角色ID",groups={ValidateEdit.class})
	private String roleId;//角色id
	@NotNull(message="{mustinput_error};角色名称",groups={ValidateInsert.class,ValidateEdit.class})
	@NotEmpty(message="{mustinput_error};角色名称",groups={ValidateInsert.class,ValidateEdit.class})
	@Size(min = 1,max = 40, message="{inputlength_range_error};角色名称;1;40",groups={ValidateInsert.class,ValidateEdit.class})
	private String roleName;//角色名称
	
	@NotNull(message="{mustinput_error};角色描述",groups={ValidateInsert.class,ValidateEdit.class})
	@NotEmpty(message="{mustinput_error};角色描述",groups={ValidateInsert.class,ValidateEdit.class})
	@Size(min = 1,max = 50, message="{inputlength_range_error};角色描述;1;50",groups={ValidateInsert.class,ValidateEdit.class})
	private String roleDesc;//角色描述
	private String addUser;//添加人
	private Timestamp addTime;//添加时间
	private String updateUser;//更新人
	private Timestamp updateTime;//更新时间
	private String userId;//用户id
	private String buttonId;//按钮id
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getButtonId() {
		return buttonId;
	}
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	/**
	 * 
	 */
	public RoleInfoDto() {
		super();
	}
	/**
	 * @param roleId
	 */
	public RoleInfoDto(String roleId) {
		super();
		this.roleId = roleId;
	}
}
