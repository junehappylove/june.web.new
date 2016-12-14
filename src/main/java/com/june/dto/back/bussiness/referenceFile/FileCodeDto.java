/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.referenceFile.ReferenceFileDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.referenceFile;

import java.io.Serializable;

import com.june.common.PageDTO;

/**
 * 
 * 参考文件与故障代码关联管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月16日 上午10:59:58
 */
public class FileCodeDto extends PageDTO<FileCodeDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;

	private String errorCode; 	// 故障代码
	private String errorId; 	// 故障代码ID
	private String fileName; 	// 参考文件名称
	private String fileId; 		// 参考文件ID

	public FileCodeDto() {
	}

	/**
	 * @param fileId
	 */
	public FileCodeDto(String fileId) {
		super();
		this.fileId = fileId;
	}

	/**
	 * @param errorId
	 * @param fileId
	 */
	public FileCodeDto(String errorId, String fileId) {
		super();
		this.errorId = errorId;
		this.fileId = fileId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	protected String getDtoName() {
		return "参考文件与故障代码";
	}

}
