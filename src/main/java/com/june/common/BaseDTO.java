/**
 * 普导慧智软件有限公司<br>
 * june_web_new:com.june.common.BaseDTO.java
 * 日期:2016年12月11日
 */
package com.june.common;

import java.io.IOException;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.june.dto.back.bussiness.ftp.FtpFile;

/**
 * 业务基础DTO <br>
 * 用于记录本项目相关业务性的内容
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

	private String lastName;	//上一次的修改名称，针对FTP修改目录名称而设置的
	
	private List<FtpFile> files;//FTP目录下的文件信息
	
	private String currVehicle;//当前车型ID
	private String currVehicleName;//当前车型名称
	
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
}
