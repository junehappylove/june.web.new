/**
 * JUNE软件有限公司<br>
 * com.june.common.validate.TimestampConverter.java
 * 日期:2016年4月28日
 */
package com.june.common.validate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.springframework.core.convert.converter.Converter;

/**
 * TODO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年4月28日 下午5:23:42
 */
public class TimestampConverter implements Converter<String, Timestamp>{

	@Override
	public Timestamp convert(String arg0) {
		if (arg0 == null) {
			return null;
		}
		if (!(arg0 instanceof String)) {
			throw new ConversionException("只支持字符串转换 !");
		}
		String str = (String) arg0;
		if (str.trim().equals("")) {
			return null;
		}
		SimpleDateFormat sd = null;
		if (str.length() == 10) {
			sd = new SimpleDateFormat("yyyy-MM-dd");
		}else{
			sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			Date date = sd.parse(str);
			sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = sd.format(date);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			ts = Timestamp.valueOf(str);
			return ts;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
