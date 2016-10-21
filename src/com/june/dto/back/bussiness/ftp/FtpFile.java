/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.ftpCode.FtpDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.ftp;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * FTP文件管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class FtpFile implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 6847363847362080752L;
	private String fileName;// 文件名
	private String fileUrl;// 文件访问地址
	private Map<String ,InputStream> fileMap;//文件内容map
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Map<String, InputStream> getFileMap() {
		return fileMap;
	}
	public void setFileMap(Map<String, InputStream> fileMap) {
		this.fileMap = fileMap;
	}
}
