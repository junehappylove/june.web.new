/**
 * JUNE软件有限公司<br>
 * june_web_new:com.june.utility.FtpTree.java
 * 日期:2016年10月21日
 */
package com.june.utility;

import java.util.List;

/**
 * TODO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年10月21日 下午5:09:09
 */

public class FtpTree{
	private String id;
	private String pid;
	private String name ;
	private String url;
	private String path;
	private String path_;
	private boolean isFile = false;//是否文件 
	private List<FtpTree> children;//她的孩子们

	public String getPath_() {
		return path_;
	}
	public void setPath_(String path_) {
		this.path_ = path_;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<FtpTree> getChildren() {
		return children;
	}
	public void setChildren(List<FtpTree> children) {
		this.children = children;
	}
	
	@Override
	public String toString() {
		return "["+this.url+"/"+this.name+"]";
	};
}
