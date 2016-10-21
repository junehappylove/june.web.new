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

public class FastDFSException extends RuntimeException {
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -7940543048941866246L;

	/**
	 * @Description: 自定义fastdfs异常 @author caiyang @param: @param
	 * message @param: @return @return: NoAvaliableTrackerException @throws
	 */
	public FastDFSException(String message) {
		super(message);
	}
}
