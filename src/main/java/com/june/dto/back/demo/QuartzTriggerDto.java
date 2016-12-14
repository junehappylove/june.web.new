/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dto.back.demo;

import java.io.Serializable;

import com.june.common.PageDTO;

public class QuartzTriggerDto extends PageDTO<QuartzTriggerDto> implements Serializable{

	  /**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 3014238958045388646L;

	/** 任务名称 */
    private String schedName;
    
    /** 触发器名称 */
    private String triggerName;
    
    /** 触发器分组 */
    private String triggerGroup;
    
    /**job名称  */
    private String jobName;
    
    /**job分组  */
    private String jobGroup;
    
    /** 描述 */
    private String description;
    
    /**下次启动时间  */
    private String nextFireTime;
    
    /**上次启动时间  */
    private String prevFireTime;
    
    /**优先级  */
    private Integer priority;
    
    /**触发器的状态  */
    private String triggerState;
    
    /**触发器类别  */
    private String triggerType;
    
    /**开始时间  */
    private String startTime;
    
    /** 结束时间 */
    private String endTime;
    
    /**  */
    private String calendarName;
    
    /**  */
    private Integer misfireInstr;
    
    /**  */
    private Long[] jobData;
    
    private int repeat;
    
    private long interval;
    
    private String cronExpress;
    /**
     * 获取
     * 
     * @return 
     */
     public String getSchedName() {
        return this.schedName;
     }
     
    /**
     * 设置
     * 
     * @param schedName
     *          
     */
     public void setSchedName(String schedName) {
        this.schedName = schedName;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getTriggerName() {
        return this.triggerName;
     }
     
    /**
     * 设置
     * 
     * @param triggerName
     *          
     */
     public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getTriggerGroup() {
        return this.triggerGroup;
     }
     
    /**
     * 设置
     * 
     * @param triggerGroup
     *          
     */
     public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getJobName() {
        return this.jobName;
     }
     
    /**
     * 设置
     * 
     * @param jobName
     *          
     */
     public void setJobName(String jobName) {
        this.jobName = jobName;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getJobGroup() {
        return this.jobGroup;
     }
     
    /**
     * 设置
     * 
     * @param jobGroup
     *          
     */
     public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getDescription() {
        return this.description;
     }
     
    /**
     * 设置
     * 
     * @param description
     *          
     */
     public void setDescription(String description) {
        this.description = description;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getNextFireTime() {
        return this.nextFireTime;
     }
     
    /**
     * 设置
     * 
     * @param nextFireTime
     *          
     */
     public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getPrevFireTime() {
        return this.prevFireTime;
     }
     
    /**
     * 设置
     * 
     * @param prevFireTime
     *          
     */
     public void setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public Integer getPriority() {
        return this.priority;
     }
     
    /**
     * 设置
     * 
     * @param priority
     *          
     */
     public void setPriority(Integer priority) {
        this.priority = priority;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getTriggerState() {
        return this.triggerState;
     }
     
    /**
     * 设置
     * 
     * @param triggerState
     *          
     */
     public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getTriggerType() {
        return this.triggerType;
     }
     
    /**
     * 设置
     * 
     * @param triggerType
     *          
     */
     public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getStartTime() {
        return this.startTime;
     }
     
    /**
     * 设置
     * 
     * @param startTime
     *          
     */
     public void setStartTime(String startTime) {
        this.startTime = startTime;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getEndTime() {
        return this.endTime;
     }
     
    /**
     * 设置
     * 
     * @param endTime
     *          
     */
     public void setEndTime(String endTime) {
        this.endTime = endTime;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public String getCalendarName() {
        return this.calendarName;
     }
     
    /**
     * 设置
     * 
     * @param calendarName
     *          
     */
     public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public Integer getMisfireInstr() {
        return this.misfireInstr;
     }
     
    /**
     * 设置
     * 
     * @param misfireInstr
     *          
     */
     public void setMisfireInstr(Integer misfireInstr) {
        this.misfireInstr = misfireInstr;
     }
    
    /**
     * 获取
     * 
     * @return 
     */
     public Long[] getJobData() {
        return this.jobData;
     }
     
    /**
     * 设置
     * 
     * @param jobData
     *          
     */
     public void setJobData(Long[] jobData) {
        this.jobData = jobData;
     }

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public String getCronExpress() {
		return cronExpress;
	}

	public void setCronExpress(String cronExpress) {
		this.cronExpress = cronExpress;
	}

	@Override
	protected String getDtoName() {
		return "触发器";
	}
}
