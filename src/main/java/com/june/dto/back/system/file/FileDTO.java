/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.dao.back.system.file.FileDTO.java
 * 日期:2016年12月18日
 */
package com.june.dto.back.system.file;

import com.june.common.PageDTO;

/**
 * FileDTO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月18日 下午9:18:09
 */
public class FileDTO extends PageDTO<FileDTO> {

	private static final long serialVersionUID = 3L;
	
	private String file_code;
	private String file_md5;
	private String file_name;
	private String file_classic;
	private String file_type;
	private String file_text;
	private String file_delete;
	private String file_common;
	private BaseFile baseFile;
	
	public String getFile_md5() {
		return file_md5;
	}

	public void setFile_md5(String file_md5) {
		this.file_md5 = file_md5;
	}

	public BaseFile getBaseFile() {
		return baseFile;
	}

	public void setBaseFile(BaseFile baseFile) {
		this.baseFile = baseFile;
	}

	public String getFile_code() {
		return file_code;
	}

	public void setFile_code(String file_code) {
		this.file_code = file_code;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_classic() {
		return file_classic;
	}

	public void setFile_classic(String file_classic) {
		this.file_classic = file_classic;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_text() {
		return file_text;
	}

	public void setFile_text(String file_text) {
		this.file_text = file_text;
	}

	public String getFile_delete() {
		return file_delete;
	}

	public void setFile_delete(String file_delete) {
		this.file_delete = file_delete;
	}

	public String getFile_common() {
		return file_common;
	}

	public void setFile_common(String file_common) {
		this.file_common = file_common;
	}

	@Override
	protected String getDtoName() {
		return "系统文件";
	}

}
