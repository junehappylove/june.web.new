/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.common.converter.DateJsonValueProcessor.java
 * 日期:2016年12月15日
 */
package com.june.common.converter;

import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON-DATE转换器 <br>
 * 解决日期类型传递到前台显示[Object obj]对象类型问题
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月15日 上午5:20:02
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
	private String format;
	
	public DateJsonValueProcessor(String format) {
		super();
		this.format = format;
	}

	/* (non-Javadoc)
	 * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
	 */
	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
	 */
	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		if(arg1 == null){
			return "";
		}
		if(arg1 instanceof java.sql.Timestamp){
			String str=new SimpleDateFormat(format).format((java.sql.Timestamp)arg1);
            return str;
		}
		if(arg1 instanceof java.util.Date){
			String str=new SimpleDateFormat(format).format((java.util.Date)arg1);
            return str;
		}
		return arg1.toString();
	}

}
