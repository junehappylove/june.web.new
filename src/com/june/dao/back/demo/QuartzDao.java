/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dao.back.demo;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.demo.QuartzTriggerDto;

/**  
* @Description: 定时任务用dao
* @author caiyang
* @date 2016年3月31日 下午2:21:51 
* @version V1.0  
 */
public interface QuartzDao extends BaseDao<QuartzTriggerDto>{

	/**   
	 * @Description: 获取所有的job名称
	 * @author caiyang
	 * @param: @return      
	 * @return: List<String>      
	 * @throws   
	 */
	public List<QuartzTriggerDto> getAllJobName();
}
