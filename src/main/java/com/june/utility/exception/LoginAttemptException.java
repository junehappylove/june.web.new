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

import org.apache.shiro.authc.AuthenticationException;

/**
 * 登录次数过多异常
 * LoginAttemptException <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午8:09:47
 * @version 1.0.0
 */
public class LoginAttemptException extends AuthenticationException {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2706768060158155015L;

	public LoginAttemptException(String message) {
		super(message);
	}
}
