/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.shiro.service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.june.common.Constants;
import com.june.dto.back.login.ButtonDto;
import com.june.shiro.dao.ResourceDao;
import com.june.shiro.dto.Resource;

/**
 * 
 * ChainDefinitionSectionMetaSource <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月18日 下午7:54:11
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{  
	 
    @Autowired  
    private ResourceDao resourceDao;  
 
    private String filterChainDefinitions;
 
    /**
     * 默认premission字符串  
     */ 
    public static final String PREMISSION_STRING="authc,perms[{0}]";  
 
    @Override
    public Section getObject() throws BeansException {
        //获取所有菜单Resource  
        List<Resource> list = resourceDao.getAll();  
        //获取所有的页面业务resource
        List<ButtonDto> buttonList = resourceDao.getButtonResource();
        Ini ini = new Ini();  
        //加载默认的url  
        ini.load(filterChainDefinitions);  
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);  
        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,  
        //里面的键就是链接URL,值就是存在什么条件才能访问该链接  
        for (Iterator<Resource> it = list.iterator(); it.hasNext();) {  
            Resource resource = it.next();  
            //如果不为空值添加到section中  
            if(StringUtils.isNotEmpty(resource.getMenuUrl()) ) {  
                //判断是一级菜单或者是共通的二级菜单
            	if (resource.getMenuUrl().contains("/SecondMenu/init/")||resource.getMenuUrl().contains("/frame/")) {
                	section.put(resource.getMenuUrl()+"*",  MessageFormat.format(PREMISSION_STRING,Constants.AUTHORITY));
				}else{
					section.put(resource.getMenuUrl(),  MessageFormat.format(PREMISSION_STRING,Constants.AUTHORITY)); 
				}
            }  
 
        }  
        
        //将业务url权限添加到section
        for (Iterator<ButtonDto> it = buttonList.iterator();it.hasNext();) {
			ButtonDto buttonDto = it.next();
			if (StringUtils.isNotEmpty(buttonDto.getButtonUrl())) {
				section.put(buttonDto.getButtonUrl(), MessageFormat.format(PREMISSION_STRING, Constants.AUTHORITY));
			}
		}
 
        return section;  
    }  
 
    /**
     * 通过filterChainDefinitions对默认的url过滤定义  
     *   
     * @param filterChainDefinitions 默认的url过滤定义  
     */ 
    public void setFilterChainDefinitions(String filterChainDefinitions) {  
        this.filterChainDefinitions = filterChainDefinitions;  
    }  
 
    public Class<?> getObjectType() {  
        return this.getClass();  
    }  
 
    public boolean isSingleton() {  
        return false;  
    }  
 
}  