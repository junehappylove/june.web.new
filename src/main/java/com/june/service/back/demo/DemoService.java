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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.demo.DemoDao;
import com.june.dto.back.demo.MenuDto;
import com.june.dto.back.demo.SchemaDto;
import com.june.util.exception.CustomException;

@Service
public class DemoService extends BaseService<DemoDao, MenuDto>{

	@Autowired
	DemoDao demoDao;
	
	public void delete()
	{
		demoDao.delete();
		throw new CustomException("删除异常");
	}
	
	public List<SchemaDto> getAllSchemaData()
	{
		return demoDao.getAllSchemaData();
	}
	
	public MenuDto getMenus(MenuDto menuDto)
	{
		List<MenuDto> list = demoDao.getPageList(menuDto);
		int total = demoDao.getTotal(menuDto);
		menuDto.setRows(list);
		menuDto.setTotal(total);
		return menuDto;
	}
	
	public int getTotal(MenuDto menuDto)
	{
		int total = demoDao.getTotal(menuDto);
		return total;
	}
	
	//创建一个新的schema
	public void createDatabase(Map<String , Object> map)
	{
		demoDao.createDatabase(map);
	}
}
