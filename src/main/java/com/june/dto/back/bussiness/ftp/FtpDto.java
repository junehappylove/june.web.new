/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.ftpCode.FtpDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.ftp;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.Constants;
import com.june.common.PageDTO;

/**
 * FTP设置管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class FtpDto extends PageDTO<FtpDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;

	@Size(min = 0,max = 255,message="{inputlength_ftp};FTP路径;255")
	private String ftpPath;// FTP路径
	private String ftpFileName;//FTP路径下的一个文件名称[保含文件格式]
	@Size(min = 0,max = 32,message="{inputlength_ftp};密码;32")
	private String ftpPassword;// 密码
	@NotNull(message="{mustinput_ftp};用户名")
	@NotEmpty(message="{mustinput_ftp};用户名")
	private String ftpUser; // 用户名
	@NotNull(message="{mustinput_ftp};IP")
	@NotEmpty(message="{mustinput_ftp};IP")
	@Size(min = 0,max = 32,message="{inputlength_range_ftp};IP;0;32")
	private String ftpIP; // FTP的IP
	private String ftpId;//FTP设置主键ID
	private int port = Constants.FTP_PORT;// 端口号
	private String[] paths;	//FTP路径集合
	private Map<String ,InputStream> fileMap;//上传文件
	private List<FtpFile> ftpfiles ;//所在的ftp目录下的文件信息
	
	public String getFtpFileName() {
		return ftpFileName;
	}
	public void setFtpFileName(String ftpFileName) {
		this.ftpFileName = ftpFileName;
	}
	public List<FtpFile> getFtpfiles() {
		return ftpfiles;
	}
	public void setFtpfiles(List<FtpFile> ftpfiles) {
		this.ftpfiles = ftpfiles;
	}
	public Map<String, InputStream> getFileMap() {
		return fileMap;
	}
	public void setFileMap(Map<String, InputStream> fileMap) {
		this.fileMap = fileMap;
	}
	public String[] getPaths() {
		return paths;
	}
	public void setPaths(String[] paths) {
		this.paths = paths;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getFtpPath() {
		return ftpPath;
	}
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpIP() {
		return ftpIP;
	}
	public void setFtpIP(String ftpIP) {
		this.ftpIP = ftpIP;
	}
	public String getFtpId() {
		return ftpId;
	}
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	public FtpDto(String ftpId) {
		this.ftpId = ftpId;
	}
	/**
	 * @param ftpId
	 * @param ftpPath
	 */
	public FtpDto(String ftpId, String ftpPath) {
		this.ftpPath = ftpPath;
		this.ftpId = ftpId;
	}
	public FtpDto() {
	}
}
