/**
 * 中科方德软件有限公司<br>
 * IMASqd:com.cws.common.converter.TimestampConverter.java
 * 日期:2016年11月22日
 */
package com.june.common.converter;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * TODO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年11月22日 下午1:34:53
 */
public class DateConverter implements Converter {

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	@Override
	public Object convert(@SuppressWarnings("rawtypes") Class arg0, Object arg1) {
		if (arg1 == null) {
			return null;
		}
		if (!(arg1 instanceof String)) {
			throw new ConversionException("只支持字符串转换 !");
		}
		String str = (String) arg1;
		if (str.trim().equals("")) {
			return null;
		}
		SimpleDateFormat sd = null;
		if (str.length() == 10) {
			if (str.contains("-")) {
				sd = new SimpleDateFormat("yyyy-MM-dd");
			} else if (str.contains("/")) {
				sd = new SimpleDateFormat("yyyy/MM/dd");
			}
		} else {
			if (str.contains("-")) {
				sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else if (str.contains("/")) {
				sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			}
		}
		try {
			return sd.parse(str);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
