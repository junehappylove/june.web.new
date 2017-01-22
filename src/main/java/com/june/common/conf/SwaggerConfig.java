/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.common.conf.SwaggerConfig.java
 * 日期:2017年1月21日
 */
package com.june.common.conf;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
/* //swagger 1 中的配置
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;//*/
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 自定义的丝袜哥配置 SwaggerConfig <br>
 * Loads the spring beans required by the framework
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月21日 上午12:30:28
 * @version 1.0.0
 */
@Configuration // 配置注解
// @EnableSwagger
@EnableWebMvc // 启用Mvc //非springboot框架需要引入注解@EnableWebMvc
@EnableSwagger2 // 启用Swagger2，毕竟SpringFox的核心依旧是Swagger
// @ComponentScan(basePackages = "com.june.controller.back.common.*")//然而并没有卵用
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	final static String PROJ = "june web new";// $NON-NLS-1$
	final static String DESC = "JUNE WEB 基础框架系统API接口";// $NON-NLS-1$
	final static String VERS = "2.0";// $NON-NLS-1$
	final static String NAME = "Mr.Wang";// $NON-NLS-1$
	final static String URL = "";// $NON-NLS-1$
	// 一个URL的API服务条款。
	final static String SERV = "<a href=\"http://127.0.0.1:8090/june_web_new/swagger/index.html\" style=\"color: rgb(59, 115, 175); text-decoration: none; border-radius: 0px !important; border: 0px !important; bottom: auto !important; float: none !important; height: auto !important; left: auto !important; margin: 0px !important; outline: 0px !important; overflow: visible !important; padding: 0px !important; position: static !important; right: auto !important; top: auto !important; vertical-align: baseline !important; width: auto !important; box-sizing: content-box !important; min-height: inherit !important; background: none !important;\">API接口"; // $NON-NLS-1$
	final static String CONT = "wjw.happy.love@163.com";// $NON-NLS-1$
	final static String LICS = "Apache License";// $NON-NLS-1$
	final static String L_URL = "https://github.com/junehappylove/june_web_new/blob/master/LICENSE";// $NON-NLS-1$

	// @Autowired
	// private SpringSwaggerConfig springSwaggerConfig;
	/*
	 * @Bean public SwaggerSpringMvcPlugin customImplementation() { return new
	 * SwaggerSpringMvcPlugin(springSwaggerConfig).apiInfo(apiinfo()).
	 * includePatterns(".*?");// $NON-NLS-1$ }
	 */

	// *
	@Bean
	public Docket customDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()// 选择哪些路径和API会生成document
				.apis(RequestHandlerSelectors.any())// 对所有api进行监控
				.paths(PathSelectors.any())// 对所有路径进行监控
				.build().apiInfo(apiInfo());
	}// */

	private ApiInfo apiInfo() {
		return new ApiInfo(PROJ, DESC, VERS, SERV, new Contact(NAME, URL, CONT), LICS, L_URL);
	}

	/**
	 * 下边的都是配置映射的，这个，我没办法给你们解释，水平不到.....
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");// $NON-NLS-1$
																												// $NON-NLS-2$
		registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars");// $NON-NLS-1$
																												// $NON-NLS-2$
	}

}
