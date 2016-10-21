/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.versionCode.VersionDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.client;

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
import org.springframework.format.annotation.DateTimeFormat;

import com.june.common.Constants;
import com.june.common.PageDTO;
import com.june.dto.back.bussiness.ftp.FtpConfig;
import com.june.dto.back.bussiness.ftp.FtpFile;
import com.june.utility.ApacheFTP;

/**
 * 客户端版本管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class VersionDto extends PageDTO<VersionDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;

	private String versionPath;// 更新路径
	@NotNull(message="{mustinput_version};更新日期")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp versionTime;//更新日期
	@Size(min = 0,max = 255,message="{inputlength_version};版本描述;255")
	private String versionInfo;// 版本描述
	@NotNull(message="{mustinput_version};版本号")
	@NotEmpty(message="{mustinput_version};版本号")
	@Size(min = 0,max = 64,message="{inputlength_range_version};版本号;0;64")
	private String versionCode; // 版本号
	@NotNull(message="{mustinput_version};版本名称")
	@NotEmpty(message="{mustinput_version};版本名称")
	@Size(min = 0,max = 64,message="{inputlength_range_version};版本名称;0;64")
	private String versionName; // 版本名称
	private String versionId;//客户端版本主键ID
	
	
	public Timestamp getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(Timestamp versionTime) {
		this.versionTime = versionTime;
	}
	public String getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	/**
	 * 设置更新ftp地址
	 * 举例：/客户端版本/版本1/1.0.0
	 * 格式：/客户端版本+/版本名称+/版本号
	 * @return
	 * @date 2016年7月3日 下午3:21:59
	 * @writer wjw.happy.love@163.com
	 */
	public String getVersionPath() {
		versionPath = StringUtils.isEmpty(versionPath) ? Constants.DIRECTORY_ROOT + Constants.DIRECTORY_C
				+ Constants.DIRECTORY_ROOT + this.versionName + Constants.DIRECTORY_ROOT + this.versionCode : this.versionPath;
		return versionPath;
	}
	public void setVersionPath(String versionPath) {
		this.versionPath = versionPath;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public VersionDto(String versionId) {
		super();
		this.versionId = versionId;
	}
	public VersionDto() {
		super();
	}
	
	@Override
	public List<FtpFile> getFiles() throws SocketException, IOException {
		// TODO 重写获取ftp目录下文件信息方法
		String ftpPath = this.versionPath;//ftp目录
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
