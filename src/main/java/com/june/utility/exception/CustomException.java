/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.utility.exception;

/**
 * 自定义runtime异常 CustomException <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午8:08:59
 * @version 1.0.0
 */
public class CustomException extends RuntimeException {
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -664093493898556859L;

	/**
	 * 自定义runtime异常
	 * 
	 * @param message
	 */
	public CustomException(String message) {
		super(message);
	}
}
