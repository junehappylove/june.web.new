/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;  
  
@PersistJobDataAfterExecution  
@DisallowConcurrentExecution// 不允许并发执行  
public class MyQuartzJobBean1 extends QuartzJobBean {  
  
    private static final Logger logger = LoggerFactory.getLogger(MyQuartzJobBean1.class);  
  
    @Autowired
    SimpleService simpleService;
    @Override  
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {  
    	logger.debug("executeInternal");
        simpleService.testMethod1();  
    }  
  
}
