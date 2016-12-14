package com.june.dto.back.common;

import java.io.Serializable;
import java.sql.Timestamp;

import com.june.common.PageDTO;

/**
 * 
 * 操作日志 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 上午2:37:13
 */
public class LogOperateDto extends PageDTO<LogOperateDto> implements Serializable{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 507L;
	private String logId;
	private String userId;
	private String operateType; // 操作类型
	private String funModule; // 功能模块
	private String operateRemark; // 操作备注
	private String operateMethod; // 操作方法
	private String opetateParams; // 操作参数
	private Timestamp operateTime; // 操作时间

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getFunModule() {
		return funModule;
	}

	public void setFunModule(String funModule) {
		this.funModule = funModule;
	}

	public String getOperateRemark() {
		return operateRemark;
	}

	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}

	public String getOperateMethod() {
		return operateMethod;
	}

	public void setOperateMethod(String operateMethod) {
		this.operateMethod = operateMethod;
	}

	public String getOpetateParams() {
		return opetateParams;
	}

	public void setOpetateParams(String opetateParams) {
		// 替换所有的逗号
		//this.opetateParams = opetateParams.replace(',', '@');
		this.opetateParams = opetateParams;
	}

	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	protected String getDtoName() {
		return "操作日志";
	}

}
