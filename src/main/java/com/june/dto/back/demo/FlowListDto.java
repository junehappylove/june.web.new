package com.june.dto.back.demo;

import java.io.Serializable;
import java.util.Date;

import com.june.common.PageDTO;


public class FlowListDto extends PageDTO<FlowListDto> implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -347775025322704312L;

	private String userId;

	private String id;
	private String name;
	private String assignee;
	private Date createTime;
	private String note;
	private String outcome;
	private Date time;
	private String fullMessage;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	@Override
	protected String getDtoName() {
		return "流程列表";
	}
	
}
