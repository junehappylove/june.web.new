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

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * StringUtil <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月21日 下午6:17:28
 * @version 1.0.0
 */
public class StringUtil extends StringUtils {
	public static String getInvokeString(Object bean, String method) {
		try {
			return BeanUtils.getProperty(bean, method);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 返回一个8位的随机码
	 * @return
	 * @date 2016年12月21日 下午6:21:03
	 * @writer junehappylove
	 */
	public static String randomCode(){
		return randomCode(8);
	}
	
	/**
	 * 返回一个指定长度的随机码数据
	 * @param length
	 * @return
	 * @date 2016年12月21日 下午6:21:40
	 * @writer junehappylove
	 */
	public static String randomCode(int length){
		Random random = new Random();
		String randomCode = "";
		for (int i = 0; i < length; i++) {
			randomCode += Integer.toString(random.nextInt(36), 36);
		}
		return randomCode;
	}
}