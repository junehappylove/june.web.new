/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dto.back.common;

import java.io.Serializable;
import java.util.List;

import com.june.common.PageDTO;/**  
* @Description: 树形结构用dto
* @author	caiyang
* @date 2015年10月9日 上午8:59:54 
* @version V1.0  
 */
public class TreeDto extends PageDTO<TreeDto> implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String id; //节点id
    private String pId;//父节点id
    private String name ; //节点显示的内容
    private String open; //节点的状态，展开(open)还是闭合(closed)，
    private boolean checked;//节点是否勾选
    private String isParent = "false";
	private List<TreeDto> children;
	private String roleId;
	private String filter;//过滤条件
	private String nocheck;
	
	
	public String getNocheck() {
		return nocheck;
	}
	public void setNocheck(String nocheck) {
		this.nocheck = nocheck;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getId() {
		id = id==null?null:id.trim();
		return id;
	}
	public void setId(String id) {
		id = id==null?null:id.trim();
		this.id = id;
	}
	public List<TreeDto> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDto> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getName() {
		name = name==null?null:name.trim();
		return name;
	}
	public void setName(String name) {
		name = name==null?null:name.trim();
		this.name = name;
	}
	
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getpId() {
		pId = pId==null?null: pId.trim();
		return pId;
	}
	public void setpId(String pId) {
		pId = pId==null?null: pId.trim();
		this.pId = pId;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
