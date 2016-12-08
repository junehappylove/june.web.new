package com.june.service.back.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.june.common.BaseService;
import com.june.dao.back.demo.LeaveDao;
import com.june.dto.back.demo.LeaveDto;

//@Service
public class LeaveService extends BaseService<LeaveDao, LeaveDto> {

	private static Logger logger = LoggerFactory.getLogger(LeaveService.class);

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected IdentityService identityService;

	@Autowired
	private LeaveDao leaveDao;

	// 查询请假list
	public LeaveDto getleave(LeaveDto leaveDto) {
		List<LeaveDto> list = leaveDao.getPageList(leaveDto);
		int total = leaveDao.getTotal(leaveDto);
		leaveDto.setRows(list);
		leaveDto.setTotal(total);
		return leaveDto;
	}

	// 新插入一条请假单
	public void savenewleave(LeaveDto leaveDto) {
		leaveDao.savenewleave(leaveDto);
	}

	/**
	 * 启动流程实例，让启动的流程实例关联业务
	 *
	 * @param entity
	 */
	public void saveStartProcess(LeaveDto leaveDto) {
		// 1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
		int id = leaveDto.getLeaveId();
		// 3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
		String key = "myProcess";
		/**
		 * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人 inputUser是流程变量的名称，
		 * 获取的办理人是流程变量的值
		 */
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("inputUser", "admin");// 表示惟一用户
		/**
		 * 5： (1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
		 * (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
		 */
		// 格式：LeaveBill.id的形式（使用流程变量）
		String objId = key + "." + id;
		variables.put("objId", objId);
		// 6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		logger.debug("启动流程实例");
		runtimeService.startProcessInstanceByKey(key, objId, variables);
	}

	// 根据id查询请假单
	public LeaveDto getleaveByid(LeaveDto leaveDto) {
		leaveDto = leaveDao.getleaveByid(leaveDto);
		return leaveDto;
	}

	public String getLeader(String orgId) {
		return leaveDao.getLeader(orgId);
	}
}
