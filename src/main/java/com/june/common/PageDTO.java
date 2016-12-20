package com.june.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统分页类
 * PageDTO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月20日 下午9:21:51
 * @version 1.0.0
 * @param <T>
 */
public abstract class PageDTO<T extends BaseDTO> extends BaseDTO {
	protected static final Logger logger = LoggerFactory.getLogger(PageDTO.class);
	private static final long serialVersionUID = 103L;
	//总条数
	private int  total;
	//当前页
	private int currpage = 1;
	//每页显示条数
	private int pageSize = 10;
	//
	private int start;
	// 总是与start相等
	private int startadd;//add by wjw 
	
	private List<T> rows;
	
	public void setOneRow(T t){
		if(rows==null){
			rows = new ArrayList<T>();
		}
		rows.add(t);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		start = (currpage-1)*pageSize;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	public int getStartadd() {
		startadd = start;
		return startadd;
	}

	public void setStartadd(int startadd) {
		this.startadd = startadd;
	}

	public int getCurrpage() {
		return currpage;
	}

	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
