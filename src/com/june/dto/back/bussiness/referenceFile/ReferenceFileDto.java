/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.referenceFile.ReferenceFileDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.referenceFile;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.PageDTO;
import com.june.dto.back.bussiness.ftp.FtpConfig;
import com.june.dto.back.bussiness.ftp.FtpFile;
import com.june.utility.ApacheFTP;

/**
 * 参考文件管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class ReferenceFileDto extends PageDTO<ReferenceFileDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;
	
	private String errorCode;//故障代码
	private String errorId;//故障代码ID
	private String ftpPath;// FTP服务器的存放路径
	@NotNull(message="{mustinput_error};参考文件名称")
	@NotEmpty(message="{mustinput_error};参考文件名称")
	@Size(min = 0,max = 64,message="{inputlength_range_error};参考文件;0;64")
	private String referenceFileName; // 参考文件名称
	private String referenceFileId;//参考文件主键ID
	private String vehicleId; //车型
	private String vehicleName;//所属车型名
	private String system;//对应子系统
	
	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 取ftp目录地址
	 * 举例：/车型A1/参考文件/TCU
	 * 规则：/所属车型/参考文件/所属系统
	 * @return
	 * @date 2016年7月3日 下午5:34:26
	 * @writer wjw.happy.love@163.com
	 */
	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getReferenceFileName() {
		return referenceFileName;
	}

	public void setReferenceFileName(String referenceFileName) {
		this.referenceFileName = referenceFileName;
	}

	public String getReferenceFileId() {
		return referenceFileId;
	}

	public void setReferenceFileId(String referenceFileId) {
		this.referenceFileId = referenceFileId;
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
			List<String> slist = ftpUtil.getFileList(ftpPath);
			for(String name:slist){
				url = ftpPath + "/" + name;
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
}
