/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.dto.back.system.base.SysQxsjDto.java
 * 日期:2016年12月15日
 */
package com.june.dto.back.system.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.june.common.PageDTO;

/**
 * SysQxsjDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 下午8:37:38
 */
public class SysQxsjDto extends PageDTO<SysQxsjDto> {

	private static final long serialVersionUID = 98L;
	protected static final Logger logger = LoggerFactory.getLogger(SysQxsjDto.class);
	
	@Override
	public String getDtoName() {
		return "权限设计";
	}
	
	private String qxsj_code;
	private String qxsj_name;
	private String qxsj_type;
	private String qxsj_menu;
	private int qxsj_sort;
	private String qxsj_used;
	private String qxsj_text;

	public String getQxsj_code() {
		return qxsj_code;
	}
	public void setQxsj_code(String qxsj_code) {
		this.qxsj_code = qxsj_code;
	}
	public String getQxsj_name() {
		return qxsj_name;
	}
	public void setQxsj_name(String qxsj_name) {
		this.qxsj_name = qxsj_name;
	}
	public String getQxsj_type() {
		return qxsj_type;
	}
	public void setQxsj_type(String qxsj_type) {
		this.qxsj_type = qxsj_type;
	}
	public String getQxsj_menu() {
		return qxsj_menu;
	}
	public void setQxsj_menu(String qxsj_menu) {
		this.qxsj_menu = qxsj_menu;
	}
	public int getQxsj_sort() {
		return qxsj_sort;
	}
	public void setQxsj_sort(int qxsj_sort) {
		this.qxsj_sort = qxsj_sort;
	}
	public String getQxsj_used() {
		return qxsj_used;
	}
	public void setQxsj_used(String qxsj_used) {
		this.qxsj_used = qxsj_used;
	}
	public String getQxsj_text() {
		return qxsj_text;
	}
	public void setQxsj_text(String qxsj_text) {
		this.qxsj_text = qxsj_text;
	}
	
	
	
	
	
}
