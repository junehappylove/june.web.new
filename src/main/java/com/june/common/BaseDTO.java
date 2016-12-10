/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.common.BaseDTO.java
 * 日期:2016年12月11日
 */
package com.june.common;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.june.dto.back.bussiness.ftp.FtpFile;

/**
 * 业务基础DTO <br>
 * 用于记录本项目相关业务性的内容
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月11日 上午1:56:48
 */
public class BaseDTO extends AbstractDTO {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -2126615952862633621L;

	private String lastName;	//上一次的修改名称，针对FTP修改目录名称而设置的
	
	private List<FtpFile> files;//FTP目录下的文件信息
	
	private String currVehicle;//当前车型ID
	private String currVehicleName;//当前车型名称
	


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
