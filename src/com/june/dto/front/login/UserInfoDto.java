package com.june.dto.front.login;


import com.june.common.PageDTO;
//t_user表的实体类
public class UserInfoDto extends PageDTO<UserInfoDto>{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -3086156447697547576L;
	private String userId;//用户id
	private String orgId;//所在部门id
	private String userName;//用户名
	private String userPassword;//用户密码
	private String userEmail;//用户email
	private String userBirthday;//用户生日
	private String userAddress;//用户地址
	private String userTel;//用户座机
	private String userMobile;//用户手机
	private String userLock;//是否被锁定
	private String roleName;//角色名
	private String roleId;//角色id
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserLock() {
		return userLock;
	}
	public void setUserLock(String userLock) {
		this.userLock = userLock;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
