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
import java.util.List;

import com.june.common.PageDTO;

public class TreeGridDto extends PageDTO<TreeGridDto> implements Serializable{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 4936747688639597710L;
	private String id; //节点id
    private String pid;//父节点id
	private String name;
	private String persons;
	private String begin;
	private String end;
	private List<TreeGridDto> children;
	private String iconCls;//节点的图标
	private String state; //节点的状态，展开(open)还是闭合(closed)，
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersons() {
		return persons;
	}
	public void setPersons(String persons) {
		this.persons = persons;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public List<TreeGridDto> getChildren() {
		return children;
	}
	public void setChildren(List<TreeGridDto> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Override
	protected String getDtoName() {
		return "TreeGridDto";
	}
}
