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

import com.june.common.PageDTO;

public class ComboxDto extends PageDTO<ComboxDto> {
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -1712554264682741614L;
	private String code;//系统代码号
	private String name;//系统代码名称
	private String codeType;//系统代码类型
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	@Override
	protected String getDtoName() {
		return "ComboxDto";
	}
}
