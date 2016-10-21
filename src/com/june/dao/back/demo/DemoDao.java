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
import java.util.Map;

import com.june.common.BaseDao;
import com.june.dto.back.demo.MenuDto;
import com.june.dto.back.demo.SchemaDto;

public interface DemoDao extends BaseDao<MenuDto> {

	public void delete();
	
	public List<SchemaDto> getAllSchemaData();
	
	public void createDatabase(Map<String , Object> map);
	
}
