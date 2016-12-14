<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/import.jsp"%>
<title>JUNE_WEB_NEW</title>
<script type="text/javascript" src="${ctx}/js/login/login.js"></script>
</head>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">R++</h1>
            </div>
            <h3>角色选择</h3>
            <form class="m-t" role="form" method="post" id="roleForm">
				<input type="hidden" value="" name="roleName" id="roleName" />
				<fieldset>
					<div class="form-group">
						<!-- Select Basic -->
						<div class="controls">
							<select class="selectpicker" data-style="btn-success" id="roleId"
								name="roleId">
								<c:forEach items="${roleList}" var="row" varStatus="status">
									<option value="${row.roleId}">${row.roleName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<input name="" type="button" class="btn-primary btn-lg" value="确定"
							onclick="sureClick()" /> <input name="" type="button"
							class="btn-primary btn-lg" value="退出" onclick="logoutClick()" />
					</div>
				</fieldset>
				<!--  <div class="form-group">
                    <input type="email" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" required="">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
                <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>
                </p> -->
            </form>
        </div>
    </div>
	<!-- <div class="container">
		<div class="col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>角色选择</h5>
				</div>
				<div class="ibox-content span7 text-center">
				</div>
			</div>
		</div>
	</div> -->
</body>
</html>