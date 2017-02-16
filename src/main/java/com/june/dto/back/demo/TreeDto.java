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

import com.june.common.BaseDTO;

/**
 * 树形结构用dto
 * TreeDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月16日 下午6:47:19
 * @version 1.0.0
 */
public class TreeDto extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 20170216L;
	private String id; //节点id
    private String pid;//父节点id
    private String name ; //节点显示的内容
    private boolean open; //节点的状态，展开(open)还是闭合(closed)，
    private boolean checked;//节点是否勾选
    private String isParent;
	private List<TreeDto> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	@Override
	protected String getDtoName() {
		return "Tree树";
	}

}
