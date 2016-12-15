## 关于配置文件的说明
> 1. 总共有四分配置文件；一份直接在src/main/resources下，是为了在eclipse下发布web应用到tomcat下方便设置的；
> 2. 剩余三份在文件夹dev、prp和test中 --> 分别代表开发、生成和测试环境下的系统应用配置文件；
> 3. 只有conf/config.properties文件中的属性propertiesName是不一样的，其他内容全部一样；
> 4. 注意配置文件修改需要修改四处位置；
> 5. maven 发布注意：如果有些包并不是在公共仓库中，那么就需要搭建mvaen库然后再User Setting 中配置好maven的配置文件
> 6. [参考这个帖子有很大价值](http://blog.csdn.net/chwshuang/article/details/52923268)	

	6.1.研发环境打包指令
	
	名称		指令		说明
	Name	dev-dev-package	显示名称
	Base directory	${workspace_loc:/june_web_new}	项目绝对地址目录
	Goals	clean package -Ddev	maven执行指令 指令中的-D表示参数，如果是研发环境就是dev，生产是pro
	Skip Tests	选中	选中表示跳过测试

	6.2. 研发环境部署指令
	
	名称		指令		说明
	Name	dev-dev-deploy	显示名称
	Base directory	${workspace_loc:/june_web_new}	项目绝对地址目录
	Goals	cargo:redeploy -Ddev	maven执行指令 指令中的-D表示参数，如果是研发环境就是dev，生产是pro
	Skip Tests	选中	选中表示跳过测试

	6.3. 测试环境打包指令
	
	名称		指令		说明
	Name	dev-test-package	显示名称
	Base 	directory	${workspace_loc:/june_web_new}	项目绝对地址目录
	Goals	clean package -Dtest	maven执行指令 指令中的-D表示参数，如果是研发环境就是dev，生产是pro
	Skip Tests	选中	选中表示跳过测试

	6.4. 测试环境部署指令
	
	名称		指令		说明
	Name	dev-test-deploy	显示名称
	Base directory	${workspace_loc:/june_web_new}	项目绝对地址目录
	Goals	cargo:redeploy -Dtest	maven执行指令 指令中的-D表示参数，如果是研发环境就是dev，生产是pro
	Skip Tests	选中	选中表示跳过测试

	6.5. 生产环境打包指令
	
	名称		指令		说明
	Name	dev-pro-package	显示名称
	Base directory	${workspace_loc:/june_web_new}	项目绝对地址目录
	Goals	clean package -Dpro	maven执行指令 指令中的-D表示参数，如果是研发环境就是dev，生产是pro
	Skip Tests	选中	选中表示跳过测试


