/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.utility.JsonUtil.java
 * 日期:2017年1月19日
 */
package com.june.utility;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.KeyValue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JsonUtil工具类，采用fastjson实现 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午10:13:27
 * @version 1.0.0
 */
public class JsonUtil {
	
	private static final SerializeConfig config;  
	  
    static {  
        config = new SerializeConfig();  
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式  
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式  
    }  
  
    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段  
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null  
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null  
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null  
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null  
    };  
      
  
    public static String toJSONString(Object object) {  
        return JSON.toJSONString(object, config, features);  
    }  
      
    public static String toJSONNoFeatures(Object object) {  
        return JSON.toJSONString(object, config);  
    }  
      
    public static Object toBean(String text) {  
        return JSON.parse(text);  
    }  
  
    public static <T> T toBean(String text, Class<T> clazz) {  
        return JSON.parseObject(text, clazz);  
    }  
  
    // 转换为数组  
    public static <T> Object[] toArray(String text) {  
        return toArray(text, null);  
    }  
  
    // 转换为数组  
    public static <T> Object[] toArray(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz).toArray();  
    }  
  
    // 转换为List  
    public static <T> List<T> toList(String text, Class<T> clazz) {  
        return JSON.parseArray(text, clazz);  
    }  
  
    /**  
     * 将javabean转化为序列化的json字符串  
     * @param keyvalue  
     * @return  
     */  
    public static Object beanToJson(KeyValue keyvalue) {  
        String textJson = JSON.toJSONString(keyvalue);  
        Object objectJson  = JSON.parse(textJson);  
        return objectJson;  
    }  
      
    /**  
     * 将string转化为序列化的json字符串  
     * @param keyvalue  
     * @return  
     */  
    public static Object textToJson(String text) {  
        Object objectJson  = JSON.parse(text);  
        return objectJson;  
    }  
      
    /**  
     * json字符串转化为map  
     * @param s  
     * @return  
     */  
    public static Map<String, Object> stringToCollect(String s) {  
        Map<String, Object> m = JSONObject.parseObject(s);  
        return m;  
    }  
      
    /**  
     * 将map转化为string  
     * @param m  
     * @return  
     */  
    public static String collectToString(Map<String, Object> m) {  
        String s = JSONObject.toJSONString(m);  
        return s;  
    }
}
