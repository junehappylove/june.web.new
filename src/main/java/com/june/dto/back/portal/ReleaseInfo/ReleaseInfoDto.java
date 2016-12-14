/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dto.back.portal.ReleaseInfo;

import java.io.Serializable;

import com.june.common.PageDTO;

/**
 * 
 * 发布咨询用dto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午1:19:43
 */
public class ReleaseInfoDto extends PageDTO<ReleaseInfoDto> implements Serializable{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -8871412038284785504L;
	private String channelId;//咨询类型id
	private String channelName;//咨询类型名称
	private String channelPath;//咨询存放路径
	private String contentId;//文章id
	private String status;//文章的审核状态
	private String checkDate;//审核日期
	private String commentCount;//评论数
	private String commentState;//是否允许评论
	private String contentText;//文章内容
	private String summary;//摘要
	private String title;//标题
	private String delFlg;//是否删除
	private String delDate;//删除日期
	private String checkUser;//审批人
	private String saveTime;//保存日期
	private String submitTime;//提交日期
	private String checkTime;//审批日期
	private String author;//作者，提交者
	private String view;//点击次数
	private String contentType;//文章的类型
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelPath() {
		return channelPath;
	}
	public void setChannelPath(String channelPath) {
		this.channelPath = channelPath;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getCommentState() {
		return commentState;
	}
	public void setCommentState(String commentState) {
		this.commentState = commentState;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	@Override
	protected String getDtoName() {
		return "咨询";
	}
}
