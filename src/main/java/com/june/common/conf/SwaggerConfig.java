/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.common.conf.SwaggerConfig.java
 * 日期:2017年1月21日
 */
package com.june.common.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * 自定义的丝袜哥配置 SwaggerConfig <br>
 * Loads the spring beans required by the framework
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月21日 上午12:30:28
 * @version 1.0.0
 */
@Configuration
@EnableWebMvc
@EnableSwagger
//@ComponentScan(basePackages = "com.june.controller.back.common.*")//然而并没有卵用
public class SwaggerConfig {
	private final static String NAME = "june_web_new";
	private final static String DESC = "JUNE WEB 基础框架系统";
	private final static String SERV = "NULL"; //一个URL的API服务条款。
	private final static String CONT = "wjw.happy.love@163.com";
	private final static String LICS = "Apache License";
	private final static String URL = "https://github.com/junehappylove/june_web_new/blob/master/LICENSE";

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

//	@Autowired
//	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
//		this.springSwaggerConfig = springSwaggerConfig;
//	}

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(springSwaggerConfig).apiInfo(apiinfo()).includePatterns(".*?");// $NON-NLS-1$
	}

	private ApiInfo apiinfo() {
		return new ApiInfo(NAME, DESC, SERV, CONT, LICS, URL);
	}
}
