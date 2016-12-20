/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.dao.back.system.file.FileAppDTO.java
 * 日期:2016年12月18日
 */
package com.june.dto.back.system.file;

import com.june.common.PageDTO;

/**
 * FileAppDTO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月18日 下午9:20:08
 */
public class FileAppDTO extends PageDTO<FileAppDTO> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 4L;
	private String app_code;
	private String file_code;
	private String module_code;
	private String file_history;
	private String column_1;
	private String column_2;
	private String column_3;

	/**
	 * 
	 */
	public FileAppDTO() {
		super();
	}

	/**
	 * @param app_code
	 * @param file_code
	 * @param module_code
	 */
	public FileAppDTO(String app_code, String file_code, String module_code) {
		super();
		this.app_code = app_code;
		this.file_code = file_code;
		this.module_code = module_code;
	}

	public String getApp_code() {
		return app_code;
	}

	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	public String getFile_code() {
		return file_code;
	}

	public void setFile_code(String file_code) {
		this.file_code = file_code;
	}

	public String getModule_code() {
		return module_code;
	}

	public void setModule_code(String module_code) {
		this.module_code = module_code;
	}

	public String getFile_history() {
		return file_history;
	}

	public void setFile_history(String file_history) {
		this.file_history = file_history;
	}

	public String getColumn_1() {
		return column_1;
	}

	public void setColumn_1(String column_1) {
		this.column_1 = column_1;
	}

	public String getColumn_2() {
		return column_2;
	}

	public void setColumn_2(String column_2) {
		this.column_2 = column_2;
	}

	public String getColumn_3() {
		return column_3;
	}

	public void setColumn_3(String column_3) {
		this.column_3 = column_3;
	}

	/* (non-Javadoc)
	 * @see com.june.common.AbstractDTO#getDtoName()
	 */
	@Override
	protected String getDtoName() {
		return "文件关联";
	}

}
