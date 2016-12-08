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
* @Description: 自定义runtime异常
* @author caiyang
* @date 2015年9月21日 下午4:16:17 
* @version V1.0  
 */
public class CustomException extends RuntimeException{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -664093493898556859L;

	/**   
	 * @Description: 自定义runtime异常
	 * @author caiyang
	 * @param: @param message
	 * @param: @return      
	 * @return: RuntimeException      
	 * @throws   
	 */
	public CustomException(String message)
	{
		super(message);
	}
}
