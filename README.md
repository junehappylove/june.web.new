# june.web.new(june_web_new)

## 项目介绍 

JavaWeb new framework - springmvc+mybatis+shiro+websocket+activiti+logback+quartz+javamelody+...

## 预告

> 1. 缓存管理		ok
> 1. 技术文档		完善中...
> 1. 发送邮件 -- 搭建邮件服务器；实现邮件客户端
> 1. 参考 [zihan blog](http://www.zi-han.net/) 中提到的相关工具和技术 
> 1. 引入图库		
> 1. fastdfs	
> 1. 上传		
> 1. 富文本编辑	
> 1. 嵌入百度地图	
> 1. 项目的字典数据（树，下拉）要放到redis中，用到就取出来，不用每次都去查数据；监控数据有变化就跟新内存，否则就一直放内存里面，方便项目中字典数据的使用；
> 1. ...

## 项目日志

>22. 2017-06-08 系统日志展示
    添加系统日志功能；暂时屏蔽缓存注解；添加通用初始页面类JspPage

>21. 2017-06-02 添加系统日志，新建dto项目
    后期打算模块化分离，将各个模块单独项目，先对dto分离试点。
    
>20. 2017-03-26 项目正式更名为 june.web.new

>19. 2017-03-23 提供对ehcache的支持，项目使用中，如果提示缓存的错误，请在缓存文件配置中添加相应的缓存属性，参考:ehcache.xml
	pom.xml采用私服，需要自己参考修改

>18. 2017-03-06 提供缓存执行，使用Spring cache以注解方式使用，这样只需要为特定方法添加缓存注解即可其他无需做任何修改,可以参考LoginService这个类处理缓存数据

>17. 2017-02-22 完善按钮权限系统实现,tag按钮生成黑科技，自带权限

>16. 2017-02-21 引入自定义按钮标签，简化按钮开发
	项目分包，单独提出util包和tag包

>15. 2017-02-18 引入菜单树(使用ztree，异步加载)、实现菜单配置权限按钮功能
	可以直接配置相关按钮，之后在转入角色管理配置相关菜单的具体权限信息

> 14. 2017-01-22 系统采用swagger2+springfox2.5.0重新部署restful接口部分，解决上一版本中关于上传文件无法展示上传按钮问题
	项目同时升级采用tomcat8部署

> 13. 2017-01-21 系统中集成[swagger](http://swagger.io/ "swagger"),关于SpringMVC整合swagger，可以参考[Restful形式接口文档生成之Swagger与SpringMVC整合手记](http://blog.csdn.net/xyw591238/article/details/51385233 "Swagger与SpringMVC整合")、[Swagger简介](http://blog.csdn.net/wangnan9279/article/details/44541665 "Swagger简介")这两篇文章，网上的整合很多，几乎都是千篇一律，这2篇文章也不出所外，只是相对较好一点儿;推荐看后一篇，不错，没毛病！    
	整合中还需要用到[swagger ui](https://github.com/swagger-api/swagger-ui/releases "swagger界面")

> 12. 2017-01-20 对工具类的大量修改与完善；完成简单的人名、地名、年代的人机对话,有待训练，还很不"智能"
	![人机对话1](https://github.com/junehappylove/img_lib/blob/master/june_web_new/image2.png "人机对话1")
	![人机对话2](https://github.com/junehappylove/img_lib/blob/master/june_web_new/image3.png "人机对话2")
	![人机对话3](https://github.com/junehappylove/img_lib/blob/master/june_web_new/image4.png "人机对话3")

> 11. 2017-01-18 提供对智能问答的接口，并给出一个QAS的实现  
	关于人机智能问答，是另一方面需要讨论的问题了，需要的童鞋请自行百度[`杨尚川`](http://yangshangchuan.iteye.com/ "杨尚川博客");本项目的问答部分就是搭建他的一套开源问答系统；
	还可以参考[`问答系统的基本原理`](http://blog.csdn.net/guotong1988/article/details/50787914 "杨尚川的QA系统的基本原理")
	目前暂不考虑集成到本项目中，因为试用效果并不太理想。   
	题外话，请自行学好高等数学在做深入研究    
	再次感谢杨先生对开源事业的支持，谢谢！

> 10. 2016-12-23 移除旧的datetimepicker引入时间日期选择器（bootstrap-datetimepicker）参考[`bootstrap-datetimepicker`](http://www.bootcss.com/p/bootstrap-datetimepicker/index.htm)；移除大部分的语音包简化js库

> 9. 2016-12-20 完成用户头像的处理；优化BaseController代码

> 8. 2016-12-20 设计文件上传表、引入富头像编辑器，实现用户头像修改；[`SpringMVC文件上传`](http://www.cnblogs.com/fjsnail/p/3491033.html)、[`小文件MD5计算`](http://blog.csdn.net/wangqiuyun/article/details/22941433)、[`过G文件的MD5计算`](http://www.cnblogs.com/yaowukonga/p/3523668.html)   
 ![用户头像上传](https://github.com/junehappylove/img_lib/blob/master/june_web_new/richimage1.png "用户头像上传")   

> 7. 2016-12-17 大量代码优化；加入用户登录验证码功能；注意验证码在开发(dev)模式下无效，界面中也不会展示，仅在测试(test)和生产(pro)环境下启用   
 	![开发环境登录](https://github.com/junehappylove/img_lib/blob/master/june_web_new/user_login_dev.png "开发环境登录")
 	![生成环境或测试环境登录](https://github.com/junehappylove/img_lib/blob/master/june_web_new/user_login_pro_test.png "生成环境或测试环境登录")   

> 6. 2016-12-15 加入maven自动打包发布功能

> 5. 2016-12-15 解决后台日期时间类型传递到前台总是展示成[[`Object obj`](http://www.cnblogs.com/aquriushu/p/5777844.html)]对象类型问题；代码大量优化

> 4. 2016-12-12 重新整理pom,实现maven自动构建打包发布到tomcat下，可以参考这三个帖子
 [`maven tomcat plugin实现热部署`](https://github.com/junehappylove/june_web_new/wiki/maven-tomcat-plugin%E5%AE%9E%E7%8E%B0%E7%83%AD%E9%83%A8%E7%BD%B2 "maven tomcat plugin实现热部署")、
 [`开发过程使用Tomcat Maven插件持续快捷部署Web项目`](https://github.com/junehappylove/june_web_new/wiki/%E5%BC%80%E5%8F%91%E8%BF%87%E7%A8%8B%E4%BD%BF%E7%94%A8Tomcat-Maven%E6%8F%92%E4%BB%B6%E6%8C%81%E7%BB%AD%E5%BF%AB%E6%8D%B7%E9%83%A8%E7%BD%B2Web%E9%A1%B9%E7%9B%AE "开发过程使用Tomcat Maven插件持续快捷部署Web项目") 、
 [`Eclipse中的Maven项目一键部署到Tomcat服务器 - 支持多环境部署`](https://github.com/junehappylove/june_web_new/wiki/Eclipse%E4%B8%AD%E7%9A%84Maven%E9%A1%B9%E7%9B%AE%E4%B8%80%E9%94%AE%E9%83%A8%E7%BD%B2%E5%88%B0Tomcat%E6%9C%8D%E5%8A%A1%E5%99%A8-%E6%94%AF%E6%8C%81%E5%A4%9A%E7%8E%AF%E5%A2%83%E9%83%A8%E7%BD%B2 "Eclipse中的Maven项目一键部署到Tomcat服务器 - 支持多环境部署")

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
	新增字典管理表：dic\_info、dic\_datas、dic_cache。

## 技术选型 

 > 1. springmvc
 > 1. mybatis
 > 1. shiro
 > 1. websocket
 > 1. activiti
 > 1. logback
 > 1. quartz
 > 1. javamelody
 > 1. MethodLog(Spring AOP 方法日志记录)
 > 1. Swagger2
 > 1. ...

## 基础环境

> - jdk 1.8
> - mysql 5.6
> - ...

## 联系 

### if you like, please contact me by QQ *980154978* or by email *wjw.happy.love@163.com*.

## 捐赠

>* 支付宝: wjw.happy.love@163.com
>* 微信: junehappylove
>* 财付通: 980154978

----

*Thinks a lot for all the Open Source Code lovers!*
