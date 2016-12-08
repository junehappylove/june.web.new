/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.service.back.demo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.june.common.BaseService;
import com.june.dao.back.demo.QuartzDao;
import com.june.dto.back.demo.QuartzTriggerDto;
import com.june.utility.DateUtil;
import com.june.utility.StringUtil;
import com.june.utility.exception.CustomException;

/**
 * @Description: 定时任务用service
 * @author caiyang
 * @date 2016年3月31日 下午2:21:36
 * @version V1.0
 */
// @Service
public class QuartzService extends BaseService<QuartzDao, QuartzTriggerDto> {

	@Autowired
	private QuartzDao quartzDao;

	@Autowired
	private Scheduler scheduler;

	/**
	 * @Description: 获取任务列表 @author caiyang @param: @param
	 *               quartzTriggerDto @param: @return @return:
	 *               QuartzTriggerDto @throws
	 */
	public QuartzTriggerDto getPageList(QuartzTriggerDto quartzTriggerDto) {
		List<QuartzTriggerDto> list = quartzDao.getPageList(quartzTriggerDto);
		int total = quartzDao.getTotal(quartzTriggerDto);
		quartzTriggerDto.setRows(list);
		quartzTriggerDto.setTotal(total);
		return quartzTriggerDto;
	}

	/**
	 * @Description: 暂停任务 @author caiyang @param: @param
	 *               triggerName @param: @param groupName @return: void @throws
	 */
	public void pause(String triggerName, String groupName) {
		try {
			scheduler.pauseTrigger(new TriggerKey(triggerName, groupName));
		} catch (SchedulerException e) {
			throw new CustomException("暂停任务出现异常！");
		} // 停止触发器
	}

	/**
	 * @Description: 恢复任务 @author caiyang @param: @param
	 *               triggerName @param: @param groupName @return: void @throws
	 */
	public void resume(String triggerName, String groupName) {
		try {
			scheduler.resumeTrigger(new TriggerKey(triggerName, groupName));// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 删除任务 @author caiyang @param: @param
	 *               triggerName @param: @param groupName @return: void @throws
	 */
	public boolean remove(String triggerName, String groupName) {
		TriggerKey triggerKey = new TriggerKey(triggerName, groupName);
		try {
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			return scheduler.unscheduleJob(triggerKey);// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void addSimpleTrigger(QuartzTriggerDto quartzTriggerDto) {
		// 名称
		String name = quartzTriggerDto.getTriggerName() + "&" + UUID.randomUUID().toString();
		// trigger分组
		String group = Scheduler.DEFAULT_GROUP;
		TriggerKey triggerKey = new TriggerKey(name, group);
		// 实例化SimpleTrigger
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setJobName(quartzTriggerDto.getJobName());
		trigger.setKey(triggerKey);
		trigger.setRepeatInterval(1000L);

		// 设置开始时间
		trigger.setStartTime(DateUtil.convert2Date(quartzTriggerDto.getStartTime()));

		// 设置结束时间
		if (StringUtil.isNotEmpty(quartzTriggerDto.getEndTime())) {
			trigger.setStartTime(DateUtil.convert2Date(quartzTriggerDto.getEndTime()));
		}

		// 设置执行次数
		if (StringUtil.isNotEmpty(String.valueOf(quartzTriggerDto.getRepeat())) && quartzTriggerDto.getRepeat() > 0) {
			trigger.setRepeatCount(quartzTriggerDto.getRepeat());
		}

		// 设置执行时间间隔
		if (StringUtil.isNotEmpty(String.valueOf(quartzTriggerDto.getInterval()))
				&& quartzTriggerDto.getInterval() > 0) {
			trigger.setRepeatInterval(quartzTriggerDto.getInterval());
		}
		JobKey jobKey = new JobKey(quartzTriggerDto.getJobName(), Scheduler.DEFAULT_GROUP);
		try {
			scheduler.addJob(scheduler.getJobDetail(jobKey), true);
			if (scheduler.checkExists(triggerKey)) {
				scheduler.rescheduleJob(triggerKey, trigger);
			} else {
				scheduler.scheduleJob(trigger);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @Description: 获取所有的job名称 @author caiyang @param: @return @return: List
	 *               <String> @throws
	 */
	public List<QuartzTriggerDto> getAllJobName() {
		return quartzDao.getAllJobName();
	}

	/**
	 * @Description: 添加cron trigger @author caiyang @param: @param
	 *               quartzTriggerDto @return: void @throws
	 */
	public void addCrontrigger(QuartzTriggerDto quartzTriggerDto) {
		try {
			if (isValidExpression(new CronExpression(quartzTriggerDto.getCronExpress()))) {
				String name = quartzTriggerDto.getTriggerName() + "&" + UUID.randomUUID().toString();
				CronExpression cronExpression = new CronExpression(quartzTriggerDto.getCronExpress());
				CronTriggerImpl trigger = new CronTriggerImpl();
				trigger.setCronExpression(cronExpression);
				TriggerKey triggerKey = new TriggerKey(name, Scheduler.DEFAULT_GROUP);
				trigger.setJobName(quartzTriggerDto.getJobName());
				trigger.setKey(triggerKey);
				JobKey jobKey = new JobKey(quartzTriggerDto.getJobName(), Scheduler.DEFAULT_GROUP);
				try {
					scheduler.addJob(scheduler.getJobDetail(jobKey), true);
					if (scheduler.checkExists(triggerKey)) {
						scheduler.rescheduleJob(triggerKey, trigger);
					} else {
						scheduler.scheduleJob(trigger);
					}
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 立即执行一次job @author caiyang @param: @param
	 *               quartzTriggerDto @return: void @throws
	 */
	public void runTrigger(QuartzTriggerDto quartzTriggerDto) {
		JobKey jobKey = new JobKey(quartzTriggerDto.getJobName(), quartzTriggerDto.getJobGroup());
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 校验cron表达式是否正确 @author caiyang @param: @param
	 *               cronExpression @param: @return @return: boolean @throws
	 */
	private boolean isValidExpression(final CronExpression cronExpression) {

		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setCronExpression(cronExpression);

		Date date = trigger.computeFirstFireTime(null);

		return date != null && date.after(new Date());
	}

}
