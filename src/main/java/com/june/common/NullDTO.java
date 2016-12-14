/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.common.NullDTO.java
 * 日期:2016年12月15日
 */
package com.june.common;

/**
 * TODO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 上午1:55:02
 */
public class NullDTO extends PageDTO {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 100L;

	/* (non-Javadoc)
	 * @see com.june.common.AbstractDTO#getDtoName()
	 */
	@Override
	protected String getDtoName() {
		return "未知";
	}

}
