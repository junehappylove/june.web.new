package com.june.dto.back.demo;

import java.io.Serializable;
import java.util.List;

import com.june.common.PageDTO;

/**
 * (SYS_LEAVE)
 * 
 * @author bianj
 * @version 1.0.0 2015-12-18
 */
public class LeaveDto extends PageDTO<LeaveDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -2406554054168525456L;

	/** 请假ID */
	private Integer leaveId;

	/** 请假人 */
	private String leaveUser;

	/** 请假天数 */
	private Integer leaveDays;

	/** 请假原因 */
	private String leaveReason;

	/** 注释 */
	private String leaveNote;

	/** 请假开始时间 */
	private String leaveStart;

	/** 请假结束时间 */
	private String leaveEnd;

	/** 请假状态 */
	private Integer leaveStatus;

	/** 任务ID */
	private String taskId;

	private List<FlowListDto> list;

	/**
	 * 获取请假ID
	 * 
	 * @return 请假ID
	 */
	public Integer getLeaveId() {
		return this.leaveId;
	}

	/**
	 * 设置请假ID
	 * 
	 * @param leaveId
	 *            请假ID
	 */
	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}

	/**
	 * 获取请假人
	 * 
	 * @return 请假人
	 */
	public String getLeaveUser() {
		return this.leaveUser;
	}

	/**
	 * 设置请假人
	 * 
	 * @param leaveUser
	 *            请假人
	 */
	public void setLeaveUser(String leaveUser) {
		this.leaveUser = leaveUser;
	}

	/**
	 * 获取请假天数
	 * 
	 * @return 请假天数
	 */
	public Integer getLeaveDays() {
		return this.leaveDays;
	}

	/**
	 * 设置请假天数
	 * 
	 * @param leaveDays
	 *            请假天数
	 */
	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}

	/**
	 * 获取请假原因
	 * 
	 * @return 请假原因
	 */
	public String getLeaveReason() {
		return this.leaveReason;
	}

	/**
	 * 设置请假原因
	 * 
	 * @param leaveReason
	 *            请假原因
	 */
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	/**
	 * 获取注释
	 * 
	 * @return 注释
	 */
	public String getLeaveNote() {
		return this.leaveNote;
	}

	/**
	 * 设置注释
	 * 
	 * @param leaveNote
	 *            注释
	 */
	public void setLeaveNote(String leaveNote) {
		this.leaveNote = leaveNote;
	}

	/**
	 * 获取请假开始时间
	 * 
	 * @return 请假开始时间
	 */
	public String getLeaveStart() {
		return this.leaveStart;
	}

	/**
	 * 设置请假开始时间
	 * 
	 * @param leaveStart
	 *            请假开始时间
	 */
	public void setLeaveStart(String leaveStart) {
		this.leaveStart = leaveStart;
	}

	/**
	 * 获取请假结束时间
	 * 
	 * @return 请假结束时间
	 */
	public String getLeaveEnd() {
		return this.leaveEnd;
	}

	/**
	 * 设置请假结束时间
	 * 
	 * @param leaveEnd
	 *            请假结束时间
	 */
	public void setLeaveEnd(String leaveEnd) {
		this.leaveEnd = leaveEnd;
	}

	/**
	 * 获取请假状态
	 * 
	 * @return 请假状态
	 */
	public Integer getLeaveStatus() {
		return this.leaveStatus;
	}

	/**
	 * 设置请假状态
	 * 
	 * @param leaveStatus
	 *            请假状态
	 */
	public void setLeaveStatus(Integer leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	/**
	 * 获取任务ID
	 * 
	 * @return 任务ID
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * 设置任务ID
	 * 
	 * @param leaveStatus
	 *            任务ID
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FlowListDto> getList() {
		return list;
	}

	public void setList(List<FlowListDto> list) {
		this.list = list;
	}
}