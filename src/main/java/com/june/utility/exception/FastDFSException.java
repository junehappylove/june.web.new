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
 * 
 * FastDFSException <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午8:09:22
 * @version 1.0.0
 */
public class FastDFSException extends RuntimeException {
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -7940543048941866246L;

	/**
	 * 自定义fastdfs异常
	 * 
	 * @param message 异常信息
	 */
	public FastDFSException(String message) {
		super(message);
	}
}
