package com.june.service.back.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.june.common.BaseService;
import com.june.dao.back.demo.LeaveDao;
import com.june.dto.back.demo.FlowListDto;
import com.june.dto.back.demo.LeaveDto;

//@Service
public class MyflowService extends BaseService<LeaveDao, LeaveDto> {

	private Logger log = LogManager.getLogger(MyflowService.class);

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
	protected LeaveDao leaveDao;

	public FlowListDto findTaskListByName(FlowListDto flowListDto) {
		List<Task> list = taskService.createTaskQuery()//
				.taskAssignee(flowListDto.getSys_user())// 指定个人任务查询
				.orderByTaskCreateTime().asc()//
				.listPage(flowListDto.getStart(), flowListDto.getPageSize());
		taskService.createTaskQuery()//
				.taskAssignee("admin").count();// 指定个人任务查询
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setAssignee(leaveDao.getAssignName(list.get(i).getAssignee()));
		}

		List<FlowListDto> flowList = new ArrayList<FlowListDto>();
		for (int i = 0; i < list.size(); i++) {
			FlowListDto flowListDto2 = new FlowListDto();
			flowListDto2.setId(list.get(i).getId());
			flowListDto2.setName(list.get(i).getName());
			flowListDto2.setAssignee(list.get(i).getAssignee());
			flowListDto2.setCreateTime(list.get(i).getCreateTime());
			flowList.add(flowListDto2);
		}
		flowListDto.setTotal((int) taskService.createTaskQuery()//
				.taskAssignee(flowListDto.getSys_user()).count());
		flowListDto.setRows(flowList);

		return flowListDto;
	}

	// 提交任务
	public void submitTask(FlowListDto flowListDto) {
		// 获取任务ID
		String taskId = flowListDto.getId();
		// 批注信息
		String message = flowListDto.getNote();
		// 获取连线的名称
		String outcome = flowListDto.getOutcome();
		// 使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		Authentication.setAuthenticatedUserId("admin");
		taskService.addComment(taskId, processInstanceId, message);
		Map<String, Object> variables = new HashMap<String, Object>();
		if (outcome != null && !outcome.equals("提交")) {
			variables.put("outcome", outcome);
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		String buniness_key = pi.getBusinessKey();
		// 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		if (StringUtils.isNotBlank(buniness_key)) {
			// 截取字符串，取buniness_key小数点的第2个值
			id = buniness_key.split("\\.")[1];
		}
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables);
		pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		LeaveDto leaveDto = new LeaveDto();
		if (outcome.equals("驳回")) {
			leaveDto.setLeaveId(Integer.parseInt(id));
			leaveDto.setLeaveStatus(3);// 驳回将请假状态更新成3审核未通过
			leaveDao.updataLeaveStatus(leaveDto);
		} else if (outcome.equals("提交")) {
			leaveDto.setLeaveId(Integer.parseInt(id));
			leaveDto.setLeaveStatus(1);// 提交则更新成审批中
			leaveDao.updataLeaveStatus(leaveDto);
		}

		/**
		 * 5：在完成任务之后，判断流程是否结束 如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		// 流程结束了
		if (pi == null) {

			leaveDto.setLeaveId(Integer.parseInt(id));
			leaveDto.setLeaveStatus(2);
			leaveDao.updataLeaveStatus(leaveDto);
		}
	}

	// 根据taskid获取请假信息
	public LeaveDto getLeaveInfoByTaskid(String taskid) {
		// 使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskid)// 使用任务ID查询
				.singleResult();
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		String buniness_key = pi.getBusinessKey();
		// 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		if (StringUtils.isNotBlank(buniness_key)) {
			// 截取字符串，取buniness_key小数点的第2个值
			id = buniness_key.split("\\.")[1];
		}
		LeaveDto leaveDto = new LeaveDto();
		leaveDto.setLeaveId(Integer.parseInt(id));
		leaveDto = leaveDao.getleaveByid(leaveDto);
		return leaveDto;
	}

	public List<FlowListDto> gethistory(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		// 使用当前的任务ID，查询当前流程对应的历史任务ID
		// 使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		list = taskService.getProcessInstanceComments(processInstanceId);
		List<FlowListDto> flowList = new ArrayList<FlowListDto>();
		for (int i = 0; i < list.size(); i++) {
			FlowListDto flowListDto = new FlowListDto();
			flowListDto.setTime(list.get(i).getTime());
			flowListDto.setUserId(leaveDao.getAssignName(list.get(i).getUserId()));
			flowListDto.setFullMessage(list.get(i).getFullMessage());
			flowList.add(flowListDto);
		}
		return flowList;
	}

	/**
	 * 二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
	 */
	public List<FlowListDto> findOutComeListByTaskId(String taskId) {
		// 返回存放连线的名称集合
		List<FlowListDto> list = new ArrayList<FlowListDto>();
		// 1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 获取当前活动的id
		String activityId = pi.getActivityId();
		// 4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		// 5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvm : pvmList) {
				FlowListDto flowListDto = new FlowListDto();
				String name = (String) pvm.getProperty("name");
				if (StringUtils.isNotBlank(name)) {
					flowListDto.setName(name);
					list.add(flowListDto);
				} else {
					flowListDto.setName("提交");
					list.add(flowListDto);
				}
			}
		}
		return list;
	}

	public void processback(FlowListDto flowListDto) {
		Map<String, Object> variables;
		// 取得当前任务
		HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(flowListDto.getId())
				.singleResult();
		// 取得流程实例
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(currTask.getProcessInstanceId()).singleResult();
		if (instance == null) {
			log.error("流程已经结束");
		}
		variables = instance.getProcessVariables();
		// 取得流程定义
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(currTask.getProcessDefinitionId());
		if (definition == null) {
			log.error("流程定义未找到");
		}
		// 取得上一步活动
		ActivityImpl currActivity = (definition).findActivity(currTask.getTaskDefinitionKey());
		List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();
		// 清除当前活动的出口
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		// 建立新出口
		List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
		for (PvmTransition nextTransition : nextTransitionList) {
			PvmActivity nextActivity = nextTransition.getSource();
			ActivityImpl nextActivityImpl = (definition).findActivity(nextActivity.getId());
			TransitionImpl newTransition = currActivity.createOutgoingTransition();
			newTransition.setDestination(nextActivityImpl);
			newTransitions.add(newTransition);
		}
		// 完成任务
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId())
				.taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
		for (Task task : tasks) {
			taskService.complete(task.getId(), variables);
			historyService.deleteHistoricTaskInstance(task.getId());
		}
		// 恢复方向
		for (TransitionImpl transitionImpl : newTransitions) {
			currActivity.getOutgoingTransitions().remove(transitionImpl);
		}
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}

	public ProcessDefinition findProcessDefinitionBytaskId(String taskId) {
		// 使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询对象，对应表act_re_procdef
				.processDefinitionId(processDefinitionId)// 使用流程定义ID查询
				.singleResult();
		return pd;
	}

	public String viewImage(String deploymentId, String imageName, HttpServletResponse httpServletResponse) {
		InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
		httpServletResponse.setContentType("image/png;charset=UTF-8");
		try {
			OutputStream out = httpServletResponse.getOutputStream();
			// 把图片的输入流程写入resp的输出流中
			byte[] b = new byte[1024];
			for (int len = -1; (len = in.read(b)) != -1;) {
				out.write(b, 0, len);
			}
			// 关闭流
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
