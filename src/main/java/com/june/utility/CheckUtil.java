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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: 校验类
 * @author caiyang
 * @date 2015年9月23日 上午9:59:02
 * @version V1.0
 */
public final class CheckUtil {

	/**
	 * 构造方法
	 */
	private CheckUtil() {
	}

	public static boolean isEmpty(String itemValue) {
		if (StringUtils.isEmpty(itemValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 数字校验
	 * 
	 * @param number
	 *            待校验数字
	 * @param intMax
	 *            最大整数位
	 * @param decMax
	 *            最大小数位(没有小数位写0)
	 * @return 校验结果 满足true 不满足false
	 */
	public static boolean isNumber(String number, int intMax, int decMax) {
		boolean flag = false;
		if (StringUtils.isEmpty(number)) {
			flag = true;
		} else if (decMax == 0) {
			flag = number.matches("^[0-9]+$") && number.length() <= intMax;
		} else {
			String[] num = number.split("\\.");
			if (num.length < 2) {
				flag = number.matches("^[0-9]+\\.{0,1}[0-9]*") && num[0].length() <= intMax;
			} else {
				flag = number.matches("^[0-9]+\\.{0,1}[0-9]*") && num[0].length() <= intMax
						&& num[1].length() <= decMax;
			}
		}

		return flag;
	}

	/**
	 * 字节数校验（中文两字节）
	 * 
	 * @param str
	 *            待校验字符串
	 * @param bitMax
	 *            最大字节数
	 * @param equal
	 *            是否定长
	 * @return 校验结果 满足true 不满足false
	 */
	public static boolean byteCount(String str, int bitMax, boolean equal) {
		boolean flag = false;
		if (StringUtils.isEmpty(str)) {
			flag = true;
		} else {
			String temp = str;
			int lng = temp.replaceAll("[^\\x00-\\xff]", "**").length();
			if (equal) {
				flag = lng == bitMax;
			} else {
				flag = lng <= bitMax;
			}
		}

		return flag;
	}

	/**
	 * 日期校验
	 * 
	 * @param str
	 *            待校验日期
	 * @param format
	 *            日期格式
	 * @return 校验结果 满足true 不满足false
	 */
	public static boolean isDate(String str, String format) {
		boolean flag = false;
		if (StringUtils.isEmpty(str)) {
			flag = true;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				sdf.setLenient(false);
				sdf.parse(str);
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		}

		return flag;
	}

	/**
	 * 电话校验
	 * 
	 * @param tel
	 *            待校验电话
	 * @return 校验结果 满足true 不满足false
	 */
	public static boolean isTel(String tel) {
		boolean flag = false;
		if (StringUtils.isEmpty(tel)) {
			flag = true;
		} else {
			flag = tel.matches("^[0-9-+\\(\\) ]*$");
		}

		return flag;
	}

	/**
	 * 邮箱校验
	 * 
	 * @param mail
	 *            待校验邮箱
	 * @return 校验结果 满足true 不满足false
	 */
	public static boolean isMail(String mail) {
		boolean flag = false;
		if (StringUtils.isEmpty(mail)) {
			flag = true;
		} else {
			flag = mail.matches("^([_a-zA-Z0-9-]+)(\\.[_a-zA-Z0-9-]+)*@([_a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,3})$");
		}

		return flag;
	}

	/**
	 * 必填验证(Excel导入用)
	 * 
	 * @param itemValue
	 *            验证值
	 * @param itemName
	 *            验证名
	 * @param index
	 *            行
	 * @param msgList
	 *            异常信息列表
	 * @throws Exception
	 *             异常
	 */
	public static void checkMust(String itemValue, String itemName, String index, ArrayList<String> errList)
			throws Exception {
		if (StringUtils.isEmpty(itemValue)) {
			errList.add(MessageUtil.$VALUE("excel_mustinput_error", new String[] { index, itemName }));
		}
	}

	/**
	 * 长度验证(Excel导入用)
	 * 
	 * @param itemValue
	 *            验证值
	 * @param itemName
	 *            验证名
	 * @param bitMax
	 *            最大长度
	 * @param equal
	 *            是否定长
	 * @param index
	 *            行
	 * @param msgList
	 *            异常信息列表
	 * @throws Exception
	 *             异常
	 */
	public static void checkLength(String itemValue, String itemName, int bitMax, boolean equal, String index,
			ArrayList<String> errList) throws Exception {
		if (!byteCount(itemValue, bitMax, equal)) {
			errList.add(MessageUtil.$VALUE("excel_length_error", new String[] { index, itemName }));
		}
	}

	/**
	 * 数字验证(Excel导入用)
	 * 
	 * @param itemValue
	 *            验证值
	 * @param itemName
	 *            验证名
	 * @param intMax
	 *            整数部分最大长度
	 * @param decMax
	 *            小数部分最大长度
	 * @param index
	 *            行
	 * @param msgList
	 *            异常信息列表
	 * @throws Exception
	 *             异常
	 */
	public static void checkNumber(String itemValue, String itemName, int intMax, int decMax, String index,
			ArrayList<String> errList) throws Exception {
		if (!isNumber(itemValue, intMax, decMax)) {
			errList.add(MessageUtil.$VALUE("excel_type_error", new String[] { index, itemName }));
		}
	}

	/**
	 * 日期验证(Excel导入用)
	 * 
	 * @param itemValue
	 *            验证值
	 * @param itemName
	 *            验证名
	 * @param format
	 *            日期格式
	 * @param index
	 *            行
	 * @param msgList
	 *            异常信息列表
	 * @throws Exception
	 *             异常
	 */
	public static void checkDate(String itemValue, String itemName, String format, String index, ArrayList<String> errList)
			throws Exception {
		if (!isDate(itemValue, format)) {
			errList.add(MessageUtil.$VALUE("excel_type_error", new String[] { index, itemName }));
		}
	}

	/**
	 * 电话验证(Excel导入用)
	 * 
	 * @param itemValue
	 *            验证值
	 * @param itemName
	 *            验证名
	 * @param index
	 *            行
	 * @param msgList
	 *            异常信息列表
	 * @throws Exception
	 *             异常
	 */
	public static void checkTel(String itemValue, String itemName, String index, ArrayList<String> errList) throws Exception {
		if (!isTel(itemValue)) {
			errList.add(MessageUtil.$VALUE("excel_type_error", new String[] { index, itemName }));
		}
	}

	/**
	 * 邮箱验证(Excel导入用)
	 * 
	 * @param itemValue
	 *            验证值
	 * @param itemName
	 *            验证名
	 * @param index
	 *            行
	 * @param msgList
	 *            异常信息列表
	 * @throws Exception
	 *             异常
	 */
	public static void checkMail(String itemValue, String itemName, String index, ArrayList<String> errList) throws Exception {
		if (!isMail(itemValue)) {
			errList.add(MessageUtil.$VALUE("excel_type_error", new String[] { index, itemName }));
		}
	}
}