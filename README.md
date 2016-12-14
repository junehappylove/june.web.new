# june_web_new
JavaWeb new framework - springmvc+mybatis+shiro+websocket+activiti+logback+quartz+javamelody+...
===
Thinks a lot for all the open source code lovers!

 > 1. 2016-12-08 
 	将项目转成标准的maven web项目；
	新增日志记录配置，用户可以定义是否需要记录操作日志；
	日志记录中,针对每一个方法,进行执行耗时统计；
	新增字典管理表：dic_info、dic_datas、dic_cache。
 > 2. 2016-12-09 
 	添加javamelody监控；
 	引入ckeditor；
 	加入 druid sql监控（登录用户名密码 druid/druid）；
 	整理pom.xml
 > 3. 2016-12-11 解决shrio的quartz1.6同项目本身quartz2.2的jar包冲突问题；
 	优化项目的配置文件；
 	对系统菜单，加入排序展示；
 > 4. 2016-12-12 重新整理pom,实现maven自动构建打包发布到tomcat下，可以参考这三个帖子
 [maven tomcat plugin实现热部署](http://blog.csdn.net/a468903507/article/details/45392083,"maven tomcat plugin实现热部署")、
 [开发过程使用Tomcat Maven插件持续快捷部署Web项目](https://my.oschina.net/feichexia/blog/326893,"开发过程使用Tomcat Maven插件持续快捷部署Web项目") 、
 [Eclipse中的Maven项目一键部署到Tomcat服务器 - 支持多环境部署](http://blog.csdn.net/chwshuang/article/details/48499231,"Eclipse中的Maven项目一键部署到Tomcat服务器 - 支持多环境部署")
 > 5. 2016-12-15 解决后台期类型传递到前台总是展示成[Object obj]对象类型问题；代码大量优化
 
## 技术选择 
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
 
### if you like , contact me by QQ =980154978= .
