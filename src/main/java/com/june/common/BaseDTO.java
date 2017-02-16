/**
 * 普导慧智软件有限公司<br>
 * june_web_new:com.june.common.BaseDTO.java
 * 日期:2016年12月11日
 */
package com.june.common;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务基础DTO <br>
 * 用于记录系统相关业务性的内容<br>
 * 开发人员可以修改此类用于适应相应的系统的具体业务功能
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月11日 上午1:56:48
 */
public abstract class BaseDTO extends AbstractDTO {
	protected static final Logger logger = LoggerFactory.getLogger(BaseDTO.class);
	private static final long serialVersionUID = 97L;

	private String appid;	//表示表中的id字段，主键定义为appid，禁止随意命名
	private String updUserId;//
	private Timestamp updTime;//
	private String add_user_id;
	private Timestamp add_time;
	private String upd_user_id;
	private Timestamp upd_time;
	
	public String getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(String add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Timestamp getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}

	public String getUpd_user_id() {
		return upd_user_id;
	}

	public void setUpd_user_id(String upd_user_id) {
		this.upd_user_id = upd_user_id;
	}

	public Timestamp getUpd_time() {
		return upd_time;
	}

	public void setUpd_time(Timestamp upd_time) {
		this.upd_time = upd_time;
	}

	public Timestamp getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Timestamp updTime) {
		this.updTime = updTime;
	}
	
	public String getAppid() {
		return appid;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
}
