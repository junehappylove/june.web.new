/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class SysCodeInit implements ApplicationListener<ContextRefreshedEvent> {

	private final transient Logger logger = Logger.getLogger(SysCodeInit.class);
	private static Map<String, Object> sysCodeMap = new HashMap<String, Object>();
	
	
	
	public static Map<String, Object> getSysCodeMap() {
		return sysCodeMap;
	}

	public static void setSysCodeMap(Map<String, Object> sysCodeMap) {
		SysCodeInit.sysCodeMap = sysCodeMap;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		logger.debug("onApplicationEvent");
	}
}
