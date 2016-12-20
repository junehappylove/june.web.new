/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.dao.back.system.file.BaseFile.java
 * 日期:2016年12月18日
 */
package com.june.dto.back.system.file;

import java.math.BigDecimal;

import com.june.common.PageDTO;

/**
 * BaseFile <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月18日 下午9:14:53
 * @version 1.0.2
 */
public class BaseFile extends PageDTO<BaseFile> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2L;
	
	private String file_md5;	//主键
	private String file_name;
	private String file_loc;
	private String file_type;
	private String file_filter;//文件关系过滤
	private BigDecimal file_size;
	private double file_width;
	private double file_height;
	private double file_time;

	/**
	 * @param file_md5
	 */
	public BaseFile(String file_md5) {
		super();
		this.file_md5 = file_md5;
	}

	/**
	 * 
	 */
	public BaseFile() {
		super();
	}

	public String getFile_md5() {
		return file_md5;
	}

	public void setFile_md5(String file_md5) {
		this.file_md5 = file_md5;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_loc() {
		return file_loc;
	}

	public void setFile_loc(String file_loc) {
		this.file_loc = file_loc;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_filter() {
		return file_filter;
	}

	public void setFile_filter(String file_filter) {
		this.file_filter = file_filter;
	}

	public BigDecimal getFile_size() {
		return file_size;
	}

	public void setFile_size(BigDecimal file_size) {
		this.file_size = file_size;
	}

	public double getFile_width() {
		return file_width;
	}

	public void setFile_width(double file_width) {
		this.file_width = file_width;
	}

	public double getFile_height() {
		return file_height;
	}

	public void setFile_height(double file_height) {
		this.file_height = file_height;
	}

	public double getFile_time() {
		return file_time;
	}

	public void setFile_time(double file_time) {
		this.file_time = file_time;
	}

	/* (non-Javadoc)
	 * @see com.june.common.AbstractDTO#getDtoName()
	 */
	@Override
	protected String getDtoName() {
		return "系统基本文件";
	}

}
