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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.systemset.basicset.rolemanagement.RoleManagementDao;
import com.june.dto.back.systemset.basicset.RoleInfoDto;
import com.june.utility.exception.CustomException;
  
  
@Service("simpleService")  
public class SimpleService extends BaseService<RoleManagementDao, RoleInfoDto>{  
      
    private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);  
     @Autowired
     RoleManagementDao roleManagementDao;
    public void testMethod1(){  
        //这里执行定时调度业务  
    	roleManagementDao.deleteRoleById("3");
    	throw new CustomException("test");
        //logger.info("testMethod1.......1");  
    }  
      
    public void testMethod2(){  
        logger.info("testMethod2.......2");   
    }  
}  
