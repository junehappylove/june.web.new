/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.guide.ImageXML.java
 * 日期:2016年7月4日
 */
package com.june.dto.back.bussiness.guide;

import java.util.List;

/**
 * 一个操作指南图文描述步骤XML文件类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年7月4日 下午9:07:33
 */
public class ImageXML_ {
	private List<Step> step;// 步骤集合 有且仅有一步
	private int size; 		// 共有几步
	private String path; 	// 存放FTP的路径

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSize() {
		size = step == null ? 0 : step.size();
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Step> getStep() {
		return step;
	}

	public void setStep(List<Step> step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "[" + this.path + "," + this.size + "," + this.step.toString() + "]";
	}

	/**
	 * 
	 */
	public ImageXML_() {
	}

}
