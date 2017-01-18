# june_web_new

### JavaWeb new framework - springmvc+mybatis+shiro+websocket+activiti+logback+quartz+javamelody+...

===
Thinks a lot for all the Open Source Code lovers!
> 11. 2017-01-18 提供对智能问答的接口，并给出一个QAS的实现(还需要完善)
> 10. 2016-12-23 移除旧的datetimepicker引入时间日期选择器（bootstrap-datetimepicker）参考[bootstrap-datetimepicker](http://www.bootcss.com/p/bootstrap-datetimepicker/index.htm)；移除大部分的语音包简化js库
> 9. 2016-12-20 完成用户头像的处理；优化BaseController代码
> 8. 2016-12-20 设计文件上传表、引入富头像编辑器，实现用户头像修改；[SpringMVC文件上传](http://www.cnblogs.com/fjsnail/p/3491033.html)、[小文件MD5计算](http://blog.csdn.net/wangqiuyun/article/details/22941433)、[过G文件的MD5计算](http://www.cnblogs.com/yaowukonga/p/3523668.html)
 <br>![用户头像上传](https://github.com/junehappylove/img_lib/blob/master/june_web_new/richimage1.png) <br>
> 7. 2016-12-17 大量代码优化；加入用户登录验证码功能；注意验证码在开发(dev)模式下无效，界面中也不会展示，仅在测试(test)和生产(pro)环境下启用
 	<br>![开发环境登录](https://github.com/junehappylove/img_lib/blob/master/june_web_new/user_login_dev.png)
 		![生成环境或测试环境登录](https://github.com/junehappylove/img_lib/blob/master/june_web_new/user_login_pro_test.png)
 	<br>
> 6. 2016-12-15 加入maven自动打包发布功能
> 5. 2016-12-15 解决后台日期时间类型传递到前台总是展示成[[Object obj](http://www.cnblogs.com/aquriushu/p/5777844.html)]对象类型问题；代码大量优化
> 4. 2016-12-12 重新整理pom,实现maven自动构建打包发布到tomcat下，可以参考这三个帖子
 [maven tomcat plugin实现热部署](http://blog.csdn.net/a468903507/article/details/45392083,"maven tomcat plugin实现热部署")、
 [开发过程使用Tomcat Maven插件持续快捷部署Web项目](https://my.oschina.net/feichexia/blog/326893,"开发过程使用Tomcat Maven插件持续快捷部署Web项目") 、
 [Eclipse中的Maven项目一键部署到Tomcat服务器 - 支持多环境部署](http://blog.csdn.net/chwshuang/article/details/48499231,"Eclipse中的Maven项目一键部署到Tomcat服务器 - 支持多环境部署")
> 3. 2016-12-11 解决shrio的quartz1.6同项目本身quartz2.2的jar包冲突问题；
 	优化项目的配置文件；
 	对系统菜单，加入排序展示；
> 2. 2016-12-09 
 	添加javamelody监控；
 	引入ckeditor；
 	加入 druid sql监控（登录用户名密码 druid/druid）；
 	整理pom.xml
> 1. 2016-12-08 
 	将项目转成标准的maven web项目；
	新增日志记录配置，用户可以定义是否需要记录操作日志；
	日志记录中,针对每一个方法,进行执行耗时统计；
	新增字典管理表：dic_info、dic_datas、dic_cache。
 
 
 
## 技术选型 
 > 1. springmvc
 > 2. mybatis
 > 3. shiro
 > 4. websocket
 > 5. activiti
 > 6. logback
 > 7. quartz
 > 8. javamelody
 > 9. MethodLog(Spring AOP 方法日志记录)
 > 10. ...
 
### if you like , contact me by QQ *980154978* .
