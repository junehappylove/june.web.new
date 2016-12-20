package com.june.common.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * JsonDateValueProcessor <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月20日 下午9:24:13
 * @version 1.0.0
 */
public class JsonDateValueProcessor implements JsonValueProcessor{

	 private String format ="yyyy-MM-dd";  
	 public JsonDateValueProcessor() {  
	        super();  
	    }  
	      
	    public JsonDateValueProcessor(String format) {  
	        super();  
	        this.format = format;  
	    }  
	
	    @Override  
	    public Object processArrayValue(Object paramObject,  
	            JsonConfig paramJsonConfig) {  
	        return process(paramObject);  
	    }  

	    @Override  
	    public Object processObjectValue(String paramString, Object paramObject,  
	            JsonConfig paramJsonConfig) {  
	        return process(paramObject);  
	    }  
	    
	    private Object process(Object value){  
	        if(value instanceof Date){    
	            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);    
	            return sdf.format(value);  
	        }    
	        return value == null ? "" : value.toString();    
	    }  

}
