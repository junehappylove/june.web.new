package com.june.dto.back.archive;

import com.june.common.PageDTO;

public class ArchiveDto extends PageDTO<ArchiveDto>{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -4828006797212957867L;
	private String id;
	private String archiveName;
	private String operateUserId;
	private String operateTime;
	private String operateUserName;
	private String pictureName;
	private String pictureUrl;
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArchiveName() {
		return archiveName;
	}
	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	@Override
	protected String getDtoName() {
		return "文档";
	}
}
