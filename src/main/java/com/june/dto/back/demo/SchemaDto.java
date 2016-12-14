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

import com.june.common.AbstractDTO;

public class SchemaDto extends AbstractDTO implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -6148421148463946324L;
	private String schemaName;

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	@Override
	protected String getDtoName() {
		return "租户(Schema)";
	}
}
