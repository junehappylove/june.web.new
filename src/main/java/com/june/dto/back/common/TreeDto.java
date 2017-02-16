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

import java.util.List;

import com.june.common.PageDTO;

/**
 * 树形结构用dto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 上午2:37:43
 */
public class TreeDto extends PageDTO<TreeDto> {

	private static final long serialVersionUID = 20170216L;
	private String id; 		// 节点id
	private String pid;		// 父节点id
	private String name; 	// 节点显示的内容
	private String open; 	// 节点的状态，展开(open)还是闭合(closed)，
	private boolean checked;// 节点是否勾选
	private String isParent = "false";
	private List<TreeDto> children;
	private String filter;	// 过滤条件
	private String nocheck;
	private String url;//附加信息
	private String roleId;//附加信息

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
		id = id == null ? null : id.trim();
		return id;
	}

	public void setId(String id) {
		id = id == null ? null : id.trim();
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
		name = name == null ? null : name.trim();
		return name;
	}

	public void setName(String name) {
		name = name == null ? null : name.trim();
		this.name = name;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getPid() {
		pid = pid == null ? null : pid.trim();
		return pid;
	}

	public void setPid(String pid) {
		pid = pid == null ? null : pid.trim();
		this.pid = pid;
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

	@Override
	protected String getDtoName() {
		return "树";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
