package com.june.dto.back.system.base;

import java.io.Serializable;

import com.june.common.PageDTO;

/**
 * @Description 用户-角色 dto
 * @author liren
 *
 */
public class UserRoleDto extends PageDTO<UserRoleDto> implements Serializable{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 754496859383697717L;
	private String userId;
	private String userName;
	private String roleId;
	private String roleName;
	private String roleDesc;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

}
