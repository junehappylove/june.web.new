/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.guide.GuideDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.guide;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.Constants;
import com.june.common.PageDTO;
import com.june.dto.back.bussiness.ftp.FtpConfig;
import com.june.dto.back.bussiness.ftp.FtpFile;
import com.june.utility.ApacheFTP;

/**
 * 操作指南管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class GuideDto extends PageDTO<GuideDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;
	
	@Size(min = 0,max = 255,message="{inputlength_error};FTP存放路径;255")
	private String ftpPath;// FTP服务器的存放路径

	@NotNull(message="{mustinput_error};应急操作指南")
	@NotEmpty(message="{mustinput_error};应急操作指南")
	private String emergency = Constants.FLAG_YES; // 是否应急操作指南	1 是(默认)；0 否
	
	@NotNull(message="{mustinput_error};操作指南名称")
	@NotEmpty(message="{mustinput_error};操作指南名称")
	@Size(min = 0,max = 64,message="{inputlength_range_error};操作指南;0;64")
	private String guideName; // 操作指南名称
	private String guideId;//操作指南主键ID
	private String vehicleId;//所属车型
	private String vehicleName;//所属车型名
	private Timestamp fileTime;	//文件时间
	private Timestamp videoTime;//视频时间
	private String errorCode;//关联的故障代码
	private String errorId;//故障代码的Id

	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 */
	public GuideDto() {
		super();
	}

	/**
	 * @param guideId
	 */
	public GuideDto(String guideId) {
		super();
		this.guideId = guideId;
	}

	public Timestamp getFileTime() {
		return fileTime;
	}

	public void setFileTime(Timestamp fileTime) {
		this.fileTime = fileTime;
	}

	public Timestamp getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(Timestamp videoTime) {
		this.videoTime = videoTime;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getEmergency() {
		return emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	@Override
	public List<FtpFile> getFiles() throws SocketException, IOException {
		// TODO 重写获取ftp目录下文件信息方法
		String ftpPath = this.ftpPath;//ftp目录
		if(StringUtils.isNotEmpty(ftpPath)){
			ApacheFTP ftpUtil = ApacheFTP.getInstance();
			FtpConfig ff = FtpConfig.get();
			ftpUtil.connectServer(ff.getFtpIP(), ff.getPort(), ff.getFtpUser(), ff.getFtpPassword(), ff.getFtpPath());
			List<FtpFile> list = new ArrayList<FtpFile>();
			FtpFile file = null;//new FtpFile();
			String url=null;
			List<String> slist = ftpUtil.getFileList(ftpPath+File.separator+Constants.DIRECTORY_F);
			List<String> slist2 = ftpUtil.getFileList(ftpPath+File.separator+Constants.DIRECTORY_V);
			for(String name:slist){ //文件目录下的文件
				url = ftpPath + "/" + Constants.DIRECTORY_F + "/" + name;
				file = new FtpFile();
				file.setFileName(name);
				file.setFileUrl(url);
				list.add(file);
			}
			for(String name:slist2){ //视频目录下的文件
				url = ftpPath + "/" + Constants.DIRECTORY_V + "/" + name;
				file = new FtpFile();
				file.setFileName(name);
				file.setFileUrl(url);
				list.add(file);
			}
			ftpUtil.closeServer();
			return list;
		}else{
			return super.getFiles();
		}
	}
	@Override
	protected String getDtoName() {
		return "操作指南";
	}
}
