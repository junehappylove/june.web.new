/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.dto.back.system.base.FunctionDto.java
 * 日期:2017年2月17日
 */
package com.june.dto.back.system.base;

import com.june.common.PageDTO;

/**
 * FunctionDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月17日 下午4:56:11
 * @version 1.0.0
 */
public class FunctionDto extends PageDTO<FunctionDto> {

	private static final long serialVersionUID = 20170217L;
	
	private String btn_id;
	private String btn_1_id;
	private String btn_2_id;
	private String btn_3_id;
	private String btn_page_id;
	private String btn_name;
	private String btn_func;
	private String btn_url;

	public String getBtn_id() {
		return btn_id;
	}

	public void setBtn_id(String btn_id) {
		this.btn_id = btn_id;
	}

	public String getBtn_1_id() {
		return btn_1_id;
	}

	public void setBtn_1_id(String btn_1_id) {
		this.btn_1_id = btn_1_id;
	}

	public String getBtn_2_id() {
		return btn_2_id;
	}

	public void setBtn_2_id(String btn_2_id) {
		this.btn_2_id = btn_2_id;
	}

	public String getBtn_3_id() {
		return btn_3_id;
	}

	public void setBtn_3_id(String btn_3_id) {
		this.btn_3_id = btn_3_id;
	}

	public String getBtn_page_id() {
		return btn_page_id;
	}

	public void setBtn_page_id(String btn_page_id) {
		this.btn_page_id = btn_page_id;
	}

	public String getBtn_name() {
		return btn_name;
	}

	public void setBtn_name(String btn_name) {
		this.btn_name = btn_name;
	}

	public String getBtn_func() {
		return btn_func;
	}

	public void setBtn_func(String btn_func) {
		this.btn_func = btn_func;
	}

	public String getBtn_url() {
		return btn_url;
	}

	public void setBtn_url(String btn_url) {
		this.btn_url = btn_url;
	}

	@Override
	protected String getDtoName() {
		return "功能";
	}
}
