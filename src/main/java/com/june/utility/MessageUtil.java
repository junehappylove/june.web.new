/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.utility;

import java.text.MessageFormat;

//import org.springframework.beans.factory.annotation.Autowired;

import com.june.common.CustomizedPropertyPlaceholderConfigurer;

public class MessageUtil {
	// 配置文件类注入
	//@Autowired
	//private static CustomizedPropertyPlaceholderConfigurer customizedPropertyPlaceholderConfigurer;

	// 根据id获取配置文件消息
	private static String getMessage(String msgId) {
		return (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty(msgId);
	}

	/**
	 * 设定message
	 * 
	 * @param message
	 *            MessagesDTO
	 * @param messageParam
	 *            String[]
	 * @return String
	 * @throws SpaceParameterException
	 *             异常信息
	 */
	private static String setMessageByParam(String message, String[] messageParam) throws Exception {
		MessageFormat messageFormat = new MessageFormat(message);
		String messageValue = messageFormat.format(messageParam);
		return messageValue;
	}

	/**
	 * 根据消息id获取配置文件中的消息内容
	 * 
	 * @param msgId
	 * @param param
	 *            消息内容中替换的参数
	 * @throws Exception
	 * @return: String 获取不到原路返回msgId
	 */
	public static String $VALUE(String msgId, String... param) {
		try {
			String message = getMessage(msgId);
			message = StringUtil.isNotBlank(message) ? message : msgId;
			return setMessageByParam(message, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取资源文件(config.properties)中的属性值
	 * 
	 * @param key
	 * @return: String
	 */
	public static String $KEY(String key) {
		return getMessage(key);
	}
}
