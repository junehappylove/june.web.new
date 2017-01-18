package com.june.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * 自定义PropertyPlaceholderConfigurer返回properties内容 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月8日 下午5:36:40
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

}
