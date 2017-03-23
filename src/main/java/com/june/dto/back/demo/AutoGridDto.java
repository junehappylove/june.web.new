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

public class AutoGridDto extends PageDTO<AutoGridDto> implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7394002833762518397L;
	private String name;
	private String sex;
	private String age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	protected String getDtoName() {
		return "AutoGridDto";
	}
}
