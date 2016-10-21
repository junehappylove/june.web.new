package com.june.dto.back.systemset.basicset;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.june.common.PageDTO;

//sys_user表的实体类
public class UserInfoDto extends PageDTO<UserInfoDto> implements Serializable {
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -3281195967211539360L;

	@NotNull(message = "{mustinput_error};用户ID")
	@NotEmpty(message = "{mustinput_error};用户ID")
	@Size(message = "{inputlength_range_error};用户ID;5;16")
	private String userId;// 用户id

	@NotNull(message="{mustinput_error};所属机构")
	@NotEmpty(message="{mustinput_error};所属机构")
	private String orgId = "1";// 所在部门id

	@NotNull(message = "{mustinput_error};用户名")
	@NotEmpty(message = "{mustinput_error};用户名")
	@Size(min = 5, max = 16, message = "{inputlength_range_error};用户名;5;16")
	private String userName;// 用户名

	@NotNull(message = "{mustinput_error};密码")
	@NotEmpty(message = "{mustinput_error};密码")
	private String userPassword;// 用户密码

	@Pattern(regexp = "^\\s*$|^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "{inputformat_error};电子邮箱")
	@Size(min = 0, max = 50, message = "{inputlength_error};电子邮箱;50")
	private String userEmail;// 用户email

	// @Pattern(regexp="^\\s*$|^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$",message="{inputformat_error};日期")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date userBirthday;// 用户生日

	@Size(min = 0, max = 255, message = "{inputlength_error};地址;255")
	private String userAddress;// 用户地址

	@Pattern(regexp = "^\\s*$|^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})$", message = "{inputformat_error};座机号码")
	@Size(min = 0, max = 13, message = "{inputlength_error};座机号码;13")
	private String userTel;// 用户座机

	@Pattern(regexp = "^\\s*$|^(1[0-9])\\d{9}$", message = "{inputformat_error};手机号码")
	@Size(min = 0, max = 11, message = "{inputlength_error};手机号码;11")
	private String userMobile;// 用户手机
	private String userLocked;// 是否被锁定

	@NotNull(message = "{mustinput_error};角色")
	@NotEmpty(message = "{mustinput_error};角色")
	private String roleName;// 角色名

	@NotNull(message = "{mustinput_error};角色")
	@NotEmpty(message = "{mustinput_error};角色")
	private String roleId;// 角色id

	@NotNull(message="{mustinput_error};所属机构")
	@NotEmpty(message="{mustinput_error};所属机构")
	private String orgName;

	private String encodeEmail; // 加密的邮箱号
	private String verifyCode; // 加密的验证码

	private String isDefaultPassword;// 是否默认密码

	/**
	 * 
	 */
	public UserInfoDto() {
		super();
	}

	/**
	 * @param userId
	 */
	public UserInfoDto(String userId) {
		super();
		this.userId = userId;
	}

	public String getEncodeEmail() {
		return encodeEmail;
	}

	public void setEncodeEmail(String encodeEmail) {
		this.encodeEmail = encodeEmail;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getIsDefaultPassword() {
		return isDefaultPassword;
	}

	public void setIsDefaultPassword(String isDefaultPassword) {
		this.isDefaultPassword = isDefaultPassword;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

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

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
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

	public String getUserLocked() {
		return userLocked;
	}

	public void setUserLocked(String userLocked) {
		this.userLocked = userLocked;
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
