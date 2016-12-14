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
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.BaseDTO;

public class DemoDto extends BaseDTO implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1367971827606798097L;
	@NotEmpty(message="{mustinput_error};aa;bb")
	private String text1;
	@NotEmpty(message="{mustinput_error};cc;dd")
	private String text2;
	@NotEmpty(message="{mustinput_error};ee;ff")
	private String checkbox1;
	private String checkbox2;
	private String identity;
	private Date startDate;
	private String select;
	private Date date1;
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public String getCheckbox1() {
		return checkbox1;
	}
	public void setCheckbox1(String checkbox1) {
		this.checkbox1 = checkbox1;
	}
	public String getCheckbox2() {
		return checkbox2;
	}
	public void setCheckbox2(String checkbox2) {
		this.checkbox2 = checkbox2;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Override
	protected String getDtoName() {
		return "DEMO";
	}
}
