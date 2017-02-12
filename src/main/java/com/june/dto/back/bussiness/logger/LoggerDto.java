/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.errorCode.ErrorCodeDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.logger;

import java.io.Serializable;
import java.sql.Timestamp;

import com.june.common.PageDTO;

/**
 * 日志管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class LoggerDto extends PageDTO<LoggerDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;
	
	private String loggerId;// 主键
	private String userId;// 操作用户
	private String operateType;// 操作类型
	private String operateModule; // 操作的功能模块
	private String operateRemark;// 操作注释
	private String operateMethod;// 操作的方法
	private String operateParams;// 参数
	private Timestamp operateTime;// 操作时间
	public String getLoggerId() {
		return loggerId;
	}
	public void setLoggerId(String loggerId) {
		this.loggerId = loggerId;
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
	public String getOperateModule() {
		return operateModule;
	}
	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
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
	public String getOperateParams() {
		return operateParams;
	}
	public void setOperateParams(String operateParams) {
		this.operateParams = operateParams;
	}
	public Timestamp getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
	@Override
	protected String getDtoName() {
		return "日志";
	}
	
}
