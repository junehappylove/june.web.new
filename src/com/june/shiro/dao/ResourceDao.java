/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.shiro.dao;

import java.util.List;

import com.june.dto.back.login.ButtonDto;
import com.june.shiro.dto.Resource;

public interface ResourceDao {

	public List<Resource> getAll();
	
	public List<ButtonDto> getButtonResource();
}
