package com.june.dao.back.demo;

import com.june.common.BaseDao;
import com.june.dto.back.demo.LeaveDto;


public interface LeaveDao extends BaseDao<LeaveDto>{

	//获取请假
	int getNewLeaveid();
	//保存新增请假单
	void savenewleave(LeaveDto leaveDto);
	//删除请假单
	void deleteleave(LeaveDto leaveDto);
	//根据id获取请假单
	LeaveDto getleaveByid(LeaveDto leaveDto);
	//更新请假状态
	void updataLeaveStatus(LeaveDto leaveDto);
	//获取审批流程下一级的领导
	String getLeader(String orgId);
	//根据id获取审批人的name
	String getAssignName(String userid);
}
