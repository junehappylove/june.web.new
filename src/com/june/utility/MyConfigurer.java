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

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MyConfigurer extends PropertyPlaceholderConfigurer{
	
	  @Override  
	    protected void processProperties(  
	            ConfigurableListableBeanFactory beanFactory, Properties props)  
	            throws BeansException {  
	  
	        String UserName = props.getProperty("username");  
	        if (UserName != null ) {  
	            props.setProperty("username", DESUtil.getDesString(UserName));  
	        }  
	        String password = props.getProperty("password");  
	        if (password != null ) {  
	            props.setProperty("password", DESUtil.getDesString(password));  
	        }  
	        String driver = props.getProperty("driver"); 
	        if (driver!=null) {
	        	 props.setProperty("driver", DESUtil.getDesString(driver)); 
			}
	        String url = props.getProperty("url");
	        if (url!=null) {
	        	 props.setProperty("driver", DESUtil.getDesString(url)); 
			}
	        super.processProperties(beanFactory, props);  
	  
	    }  
	}  
