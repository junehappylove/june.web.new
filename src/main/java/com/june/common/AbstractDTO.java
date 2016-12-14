package com.june.common;

import java.io.Serializable;
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

import com.june.dto.back.common.TreeDto;
import com.june.dto.back.system.base.UserInfoDto;

/**
 * 
 * 系统基础类dto <br>
 * 记录框架中所必须包含的基础数据
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月11日 上午1:52:16
 */
public abstract class AbstractDTO implements Serializable {
	
	private static final long serialVersionUID = 101L;
	
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
	
	protected abstract String getDtoName();

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

	public void setSys_user(String sys_user) {
		this.sys_user = sys_user;
	}

}
