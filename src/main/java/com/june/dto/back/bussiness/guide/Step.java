/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.guide.Step.java
 * 日期:2016年7月4日
 */
package com.june.dto.back.bussiness.guide;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 图文展示步骤类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年7月4日 下午9:08:32
 */
public class Step implements Comparable<Step> {

	private int id = 0;				// 第几步
	private String image;		// 图片名称
	private String description;	// 图片描述
	@XmlTransient
	private String path = "";

	public Step() {
		super();
	}

	/**
	 * @param id
	 */
	public Step(int id) {
		super();
		this.id = id;
	}

	/**
	 * @param numb
	 * @param image
	 * @param description
	 */
	public Step(int id, String image, String info) {
		super();
		this.id = id;
		this.image = image;
		this.description = info;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "{" + this.id + "," + this.image + "," + this.description + "," + this.path + "}";
	}

	@Override
	public int compareTo(Step o) {
		if(o == null){
			return 0;
		}
		return id - o.getId();
	}

	/**
	 * 认为只要两个id相等即为同一个步骤
	 */
	@Override
	public boolean equals(Object obj) {
		Step s = (Step) obj;
		return this.id == s.getId();
	}
}
