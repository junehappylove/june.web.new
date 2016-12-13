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
 * 定时任务用service
 *  <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月11日 上午12:24:13
 */
// @Service
public class QuartzService extends BaseService<QuartzDao, QuartzTriggerDto> {

	@Autowired
	private QuartzDao quartzDao;

	@Autowired
	private Scheduler scheduler;

	/**
	 * 获取任务列表
	 * @param quartzTriggerDto
	 * @return
	 * @date 2016年12月11日 上午12:25:29
	 * @writer june
	 */
	public QuartzTriggerDto getPageList(QuartzTriggerDto quartzTriggerDto) {
		List<QuartzTriggerDto> list = quartzDao.getPageList(quartzTriggerDto);
		int total = quartzDao.getTotal(quartzTriggerDto);
		quartzTriggerDto.setRows(list);
		quartzTriggerDto.setTotal(total);
		return quartzTriggerDto;
	}

	/**
	 * 暂停任务
	 * @param triggerName
	 * @param groupName
	 * @date 2016年12月11日 上午12:24:31
	 * @writer june
	 */
	public void pause(String triggerName, String groupName) {
		try {
			scheduler.pauseTrigger(new TriggerKey(triggerName, groupName));
		} catch (SchedulerException e) {
			throw new CustomException("暂停任务出现异常！");
		} // 停止触发器
	}

	/**
	 * 恢复任务
	 * @param triggerName
	 * @param groupName
	 * @date 2016年12月11日 上午12:24:38
	 * @writer june
	 */
	public void resume(String triggerName, String groupName) {
		try {
			scheduler.resumeTrigger(new TriggerKey(triggerName, groupName));// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除任务
	 * @param triggerName
	 * @param groupName
	 * @return
	 * @date 2016年12月11日 上午12:24:44
	 * @writer june
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
			e.printStackTrace();
		}

	}

	/**
	 * 获取所有的job名称
	 * @return
	 * @date 2016年12月11日 上午12:24:54
	 * @writer june
	 */
	public List<QuartzTriggerDto> getAllJobName() {
		return quartzDao.getAllJobName();
	}

	/**
	 * 添加cron trigger
	 * @param quartzTriggerDto
	 * @date 2016年12月11日 上午12:25:01
	 * @writer june
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
					e.printStackTrace();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 立即执行一次job
	 * @param quartzTriggerDto
	 * @date 2016年12月11日 上午12:25:09
	 * @writer june
	 */
	public void runTrigger(QuartzTriggerDto quartzTriggerDto) {
		JobKey jobKey = new JobKey(quartzTriggerDto.getJobName(), quartzTriggerDto.getJobGroup());
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 校验cron表达式是否正确
	 * @param cronExpression
	 * @return
	 * @date 2016年12月11日 上午12:25:15
	 * @writer june
	 */
	private boolean isValidExpression(final CronExpression cronExpression) {

		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setCronExpression(cronExpression);

		Date date = trigger.computeFirstFireTime(null);

		return date != null && date.after(new Date());
	}

}
