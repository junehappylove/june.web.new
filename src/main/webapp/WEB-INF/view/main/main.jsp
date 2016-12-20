<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>JUNE_WEB_NEW - 主页</title>
<%@include file="../common/import.jsp"%>
<meta name="keywords" content="后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description" content="基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="nav-close">
			<i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element"><!-- /upload/profile_small.jpg -->
						<span><img alt="image" class="img-circle" src="${ctx}${userInfo.userImage}"/></span>
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
							<span class="clear"> 
								<span class="block m-t-xs">
									<strong class="font-bold">${userInfo.userName}</strong>
								</span> 
								<span class="text-muted text-xs block">${userInfo.roleName}
									<b class="caret"></b>
								</span>
							</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<!-- <li><a class="J_menuItem" href="form_avatar.html">修改头像</a></li> -->
							<li><a class="J_menuItem" href="${ctx }/system/file/user/head">修改头像</a></li>
							<li><a class="J_menuItem" href="profile.html">个人资料</a></li>
							<li><a class="J_menuItem" href="contacts.html">联系我们</a></li>
							<li><a class="J_menuItem" href="mailbox.html">信箱</a></li>
							<li class="divider"></li>
							<li><a href="${ctx}/logout">安全退出</a></li>
						</ul>
					</div>
					<div class="logo-element">J+</div>
				</li>
				<c:forEach items="${firstMenu}" var="menu">
					<!-- 只有一级菜单的情况 -->
					<c:if test="${fun:length(menu.menus) == 0}">
						<li>
							<a class="J_menuItem" href="${ctx}${menu.url}">
								<i class="fa fa-columns"></i><span class="nav-label">${menu.menuName}</span>
							</a>
						</li>
					</c:if>
					<!-- 一级菜单下有二级菜单 -->
					<c:if test="${fun:length(menu.menus) > 0}">
						<li>
							<!-- 显示一级菜单 --> 
							<a href="#"> <i class="fa fa fa-bar-chart-o"></i>
								<span class="nav-label">${menu.menuName}</span> <span class="fa arrow"></span>
							</a> 
							<!-- 循环显示二级菜单 -->
							<ul class="nav nav-second-level">
								<!-- 二级菜单下无三级菜单 -->
								<c:forEach items="${menu.menus}" var="menu2">
									<c:if test="${fun:length(menu2.menus) == 0}">
										<li><a class="J_menuItem" href="${ctx}${menu2.url}">${menu2.menuName}</a></li>
									</c:if>
									<!-- 二级菜单下有三级菜单 -->
									<c:if test="${fun:length(menu2.menus) > 0}">
										<li><a href="#">${menu2.menuName} <span class="fa arrow"></span></a>
											<ul class="nav nav-third-level">
												<c:forEach items="${menu2.menus}" var="menu3">
													<li><a class="J_menuItem" href="${ctx}${menu3.url}">${menu3.menuName}</a></li>
												</c:forEach>
											</ul>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
					<form role="search" class="navbar-form-custom" method="post" action="search_results.html">
						<div class="form-group">
							<input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
						</div>
					</form>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li class="dropdown">
						<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#"> 
							<i class="fa fa-envelope"></i>
							<span class="label label-warning">16</span>
						</a>
						<ul class="dropdown-menu dropdown-messages">
							<li class="m-t-xs">
								<div class="dropdown-messages-box">
									<a href="profile.html" class="pull-left"> 
										<img alt="image" class="img-circle" src="img/a7.jpg">
									</a>
									<div class="media-body">
										<small class="pull-right">46小时前</small> <strong>小四</strong>
										这个在日本投降书上签字的军官，建国后一定是个不小的干部吧？ <br> <small
											class="text-muted">3天前 2014.11.8</small>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="dropdown-messages-box">
									<a href="profile.html" class="pull-left"> <img alt="image"
										class="img-circle" src="img/a4.jpg">
									</a>
									<div class="media-body ">
										<small class="pull-right text-navy">25小时前</small> <strong>国民岳父</strong>
										如何看待“男子不满自己爱犬被称为狗，刺伤路人”？——这人比犬还凶 <br> <small
											class="text-muted">昨天</small>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="text-center link-block">
									<a class="J_menuItem" href="mailbox.html"> <i
										class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
									</a>
								</div>
							</li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="#"> <i class="fa fa-bell"></i> <span
							class="label label-primary">8</span>
					</a>
						<ul class="dropdown-menu dropdown-alerts">
							<li><a href="mailbox.html">
									<div>
										<i class="fa fa-envelope fa-fw"></i> 您有16条未读消息 <span
											class="pull-right text-muted small">4分钟前</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="profile.html">
									<div>
										<i class="fa fa-qq fa-fw"></i> 3条新回复 <span
											class="pull-right text-muted small">12分钟钱</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li>
								<div class="text-center link-block">
									<a class="J_menuItem" href="notifications.html"> <strong>查看所有
									</strong> <i class="fa fa-angle-right"></i>
									</a>
								</div>
							</li>
						</ul></li>
					<li class="dropdown hidden-xs"><a class="right-sidebar-toggle"
						aria-expanded="false"> <i class="fa fa-tasks"></i> 主题
					</a></li>
				</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
				<div class="page-tabs-content">
					<a href="javascript:;" class="active J_menuTab"
						data-id="index_v1.html">首页</a>
				</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="${ctx}/logout"
					class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="" frameborder="0" data-id="index_v1.html" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					&copy; 2014-2015 <a href="http://www.zi-han.net/" target="_blank">zihan's
						blog</a>
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<ul class="nav nav-tabs navs-3">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> 主题
					</a></li>
					<li class=""><a data-toggle="tab" href="#tab-2"> 通知 </a></li>
					<li><a data-toggle="tab" href="#tab-3"> 项目进度 </a></li>
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu"
											class="onoffswitch-checkbox" id="collapsemenu"> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar"
											class="onoffswitch-checkbox" id="fixednavbar"> <label
											class="onoffswitch-label" for="fixednavbar"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout"
											class="onoffswitch-checkbox" id="boxedlayout"> <label
											class="onoffswitch-label" for="boxedlayout"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">
										默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">
										蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">
										黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>
					<div id="tab-2" class="tab-pane">

						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 最新通知
							</h3>
							<small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>
						</div>

						<div>

							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a1.jpg">

										<div class="m-t-xs">
											<i class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i>
										</div>
									</div>
									<div class="media-body">

										据天津日报报道：瑞海公司董事长于学伟，副董事长董社轩等10人在13日上午已被控制。 <br> <small
											class="text-muted">今天 4:21</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a2.jpg">
									</div>
									<div class="media-body">
										HCY48之音乐大魔王会员专属皮肤已上线，快来一键换装拥有他，宣告你对华晨宇的爱吧！ <br> <small
											class="text-muted">昨天 2:45</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a3.jpg">

										<div class="m-t-xs">
											<i class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i>
										</div>
									</div>
									<div class="media-body">
										写的好！与您分享 <br> <small class="text-muted">昨天 1:10</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a4.jpg">
									</div>

									<div class="media-body">
										国外极限小子的炼成！这还是亲生的吗！！ <br> <small class="text-muted">昨天
											8:37</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a8.jpg">
									</div>
									<div class="media-body">

										一只流浪狗被收留后，为了减轻主人的负担，坚持自己觅食，甚至......有些东西，可能她比我们更懂。 <br> <small
											class="text-muted">今天 4:21</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a7.jpg">
									</div>
									<div class="media-body">
										这哥们的新视频又来了，创意杠杠滴，帅炸了！ <br> <small class="text-muted">昨天
											2:45</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a3.jpg">

										<div class="m-t-xs">
											<i class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i> <i
												class="fa fa-star text-warning"></i>
										</div>
									</div>
									<div class="media-body">
										最近在补追此剧，特别喜欢这段表白。 <br> <small class="text-muted">昨天
											1:10</small>
									</div>
								</a>
							</div>
							<div class="sidebar-message">
								<a href="#">
									<div class="pull-left text-center">
										<img alt="image" class="img-circle message-avatar"
											src="img/a4.jpg">
									</div>
									<div class="media-body">
										我发起了一个投票 【你认为下午大盘会翻红吗？】 <br> <small class="text-muted">星期一
											8:37</small>
									</div>
								</a>
							</div>
						</div>

					</div>
					<div id="tab-3" class="tab-pane">

						<div class="sidebar-title">
							<h3>
								<i class="fa fa-cube"></i> 最新任务
							</h3>
							<small><i class="fa fa-tim"></i> 您当前有14个任务，10个已完成</small>
						</div>

						<ul class="sidebar-list">
							<li><a href="#">
									<div class="small pull-right m-t-xs">9小时以后</div>
									<h4>市场调研</h4> 按要求接收教材；

									<div class="small">已完成： 22%</div>
									<div class="progress progress-mini">
										<div style="width: 22%;"
											class="progress-bar progress-bar-warning"></div>
									</div>
									<div class="small text-muted m-t-xs">项目截止： 4:00 -
										2015.10.01</div>
							</a></li>
							<li><a href="#">
									<div class="small pull-right m-t-xs">9小时以后</div>
									<h4>可行性报告研究报上级批准</h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的
									开发进度作出一个合理的比对

									<div class="small">已完成： 48%</div>
									<div class="progress progress-mini">
										<div style="width: 48%;" class="progress-bar"></div>
									</div>
							</a></li>
							<li><a href="#">
									<div class="small pull-right m-t-xs">9小时以后</div>
									<h4>立项阶段</h4> 东风商用车公司 采购综合综合查询分析系统项目进度阶段性报告武汉斯迪克科技有限公司

									<div class="small">已完成： 14%</div>
									<div class="progress progress-mini">
										<div style="width: 14%;"
											class="progress-bar progress-bar-info"></div>
									</div>
							</a></li>
							<li><a href="#"> <span
									class="label label-primary pull-right">NEW</span>
									<h4>设计阶段</h4> <!--<div class="small pull-right m-t-xs">9小时以后</div>-->
									项目进度报告(Project Progress Report)
									<div class="small">已完成： 22%</div>
									<div class="small text-muted m-t-xs">项目截止： 4:00 -
										2015.10.01</div>
							</a></li>
							<li><a href="#">
									<div class="small pull-right m-t-xs">9小时以后</div>
									<h4>拆迁阶段</h4> 科研项目研究进展报告 项目编号: 项目名称: 项目负责人:

									<div class="small">已完成： 22%</div>
									<div class="progress progress-mini">
										<div style="width: 22%;"
											class="progress-bar progress-bar-warning"></div>
									</div>
									<div class="small text-muted m-t-xs">项目截止： 4:00 -
										2015.10.01</div>
							</a></li>
							<li><a href="#">
									<div class="small pull-right m-t-xs">9小时以后</div>
									<h4>建设阶段</h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的
									开发进度作出一个合理的比对

									<div class="small">已完成： 48%</div>
									<div class="progress progress-mini">
										<div style="width: 48%;" class="progress-bar"></div>
									</div>
							</a></li>
							<li><a href="#">
									<div class="small pull-right m-t-xs">9小时以后</div>
									<h4>获证开盘</h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的
									开发进度作出一个合理的比对

									<div class="small">已完成： 14%</div>
									<div class="progress progress-mini">
										<div style="width: 14%;"
											class="progress-bar progress-bar-info"></div>
									</div>
							</a></li>

						</ul>

					</div>
				</div>

			</div>
		</div>
		<!--右侧边栏结束-->
		<!-- mini聊天窗口开始 -->
		<div class="small-chat-box fadeInRight animated">
			<div class="heading" draggable="true">
				<small class="chat-date pull-right"> 2016.9.1 </small> 智能问答/聊天系统
				<span class="badge badge-info pull-left">0</span>
			</div>
			<div id="content" class="content" style="overflow:auto;">
				<div id="chat_content">
					<!-- 这里填充聊天的内容 -->
				</div>
			</div>
			<div class="form-chat">
				<div class="input-group input-group-sm">
					<input type="text" id="message_send" name="message_send" class="form-control"> 
					<span class="input-group-btn">
						<button id="send_btn" class="btn btn-primary" type="button">发送</button>
					</span>
				</div>
			</div>
		</div>
		<div id="small-chat">
			<span class="badge badge-warning pull-right" id="chat_num">0</span> 
			<a class="open-small-chat"> <i id="mini_chat_close" class="fa fa-comments"></i> </a>
		</div>
		<script type="text/javascript">
			var ws = 'ws://' + window.location.host + '${ctx}/minichat';
			var url = "http://" + window.location.host + "${ctx}/minichat";
			var sock = new SockJS(url);//采用SockJS，跨浏览器支持，不直接使用html5的websocket api
			var number = 0;
			/* if ('WebSocket' in window) {
				sock = new WebSocket(ws);
			} else if ('MozWebSocket' in window) {
				sock = new MozWebSocket(ws);
			} else {
				sock = new SockJS(url);
			} */
			sock.onopen = function() {
				console.log('open');
			};
			sock.onmessage = function(e) {
				console.log('message:'+ e.data);
				tochatview(e.data,1);
				chatnum();
			};
			sock.onclose = function() {
				console.log('close');
			};
			sock.onerror = function(e){
				console.log('error' + e.data);
			};
			function sendMessage(mes){
				tochatview(mes,0);
				sock.send(mes);
				chatnum();
			}
			function exit(){
				sock.close();
			}
			
			$(function(){
				$("#send_btn").bind("click",function(){
					messend();
				});
				document.onkeydown = function(event) {
					var e = event || window.event
							|| arguments.callee.caller.arguments[0];
					if (e && e.keyCode == 27) { // 按 Esc 
						//关闭聊天框
						$("#mini_chat_close").click();
						$("#message_send").val('');
					}
					if (e && e.keyCode == 113) { // 按 F2 
						//打开聊天窗口
						$("#mini_chat_close").click();
						$("#message_send").val('');
						$("#message_send").focus();
					}
					if (e && e.keyCode == 13) { // enter 键
						//发送消息
						messend();
					}
				};
			});
			
			function messend(){
				var con = $("input[name='message_send']").val();
				sendMessage(con);
				// 移动聊天的最底部
				var height = $('#chat_content').height();
				//var chat = $('#content').get(0).scrollHeight;
				//var scrollTop = $('#content').scrollTop();
				//console.log(height+" "+chat+" "+scrollTop);
				$('#content').scrollTop(height);
			}
			
			function tochatview(con, flag) {
				var obj = $("#chat_content");
				var html1 = '<div class="left"><div class="author-name">Server<small class="chat-date"> 10:02 </small></div><div class="chat-message active">'
						+ con + '</div></div>';
				var html2 = '<div class="right"><div class="author-name">Client<small class="chat-date"> 11:24 </small></div><div class="chat-message">'
						+ con + '</div></div>';
				if (flag == 1) {
					obj.append(html1);
					//$("#message_send").val('');
					//$("#message_send").focus();
				} else {
					obj.append(html2);
					$("#message_send").val('');
					$("#message_send").focus();
				}
			}
			function chatnum() {
				number++;
				$("#chat_num").html(number);
			}
		</script>
	</div>
</body>
</html>