package com.june.common;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;

import com.june.dto.back.bussiness.ftp.FtpFile;
import com.june.dto.back.common.TreeDto;
import com.june.dto.back.systemset.basicset.UserInfoDto;

public abstract class AbstractDTO implements Serializable {
	
	private static final long serialVersionUID = 2215994476692153946L;
	private Date sys_date = new Date();//获取当前系统时间
	private String sys_user;//获取系统当前登录的用户id
	private String identifyCode;  //验证码
	public Map<String,Object> params = new HashMap<String,Object>();//单schema多表用
	private String userId;
	private String userName;

	private String sort;	//排序字段
	private String order;	//排序方向：asc/desc
	//存放错误消息
	private ArrayList<String> errList;
    //错误类型
    private String errType;
	
	private String addUserId;
	private Timestamp addTime;
	private String updateUserId;
	private Timestamp updateTime;
	private String version;
	private List<TreeDto> tree;	//DTO的下拉值
	private String lastName;	//上一次的修改名称，针对FTP修改目录名称而设置的
	
	private List<FtpFile> files;//FTP目录下的文件信息
	
	private String currVehicle;//当前车型ID
	private String currVehicleName;//当前车型名称

	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
	public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	public String getSys_user() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) ((((WebSubject)SecurityUtils.getSubject()).getServletRequest()));
		UserInfoDto userInfoDto = (UserInfoDto)httpServletRequest.getSession().getAttribute("userInfo");
		if (userInfoDto!=null) {
			sys_user = userInfoDto.getUserId();
			return sys_user;
		}else{
			return null;
		}
		
	}

	public Date getSys_date() {
		return sys_date;
	}

	public void setSys_date(Date sys_date) {
		this.sys_date = sys_date;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public ArrayList<String> getErrList() {
		return errList;
	}

	public void setErrList(ArrayList<String> errList) {
		this.errList = errList;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<TreeDto> getTree() {
		return tree;
	}

	public void setTree(List<TreeDto> tree) {
		this.tree = tree;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<FtpFile> getFiles() throws SocketException, IOException {
		return files;
	}

	public void setFiles(List<FtpFile> files) {
		this.files = files;
	}

	public String getCurrVehicle() {
		return currVehicle;
	}

	public void setCurrVehicle(String currVehicle) {
		this.currVehicle = currVehicle;
	}

	public String getCurrVehicleName() {
		return currVehicleName;
	}

	public void setCurrVehicleName(String currVehicleName) {
		this.currVehicleName = currVehicleName;
	}

	public void setSys_user(String sys_user) {
		this.sys_user = sys_user;
	}

}
