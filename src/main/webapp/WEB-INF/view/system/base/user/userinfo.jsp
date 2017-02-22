<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/base/user/userinfo.js"></script>
</head>
<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<h4 class="example-title">用户信息</h4>
					<div class="example">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">用户ID</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="userId"
										id="userId" />
								</div>
								<label class="col-sm-1 control-label">用户名</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="userName"
										id="userName" />
								</div>
								<june:btn type="search" onclick="searchUserInfo()"></june:btn>
							</div>
						</form>
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<june:btn type="insert"></june:btn>
								<june:btn type="update"></june:btn>
								<june:btn type="delete"></june:btn>
							</div>
							<table id="userInfoTable">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemodal()">×</button>
					<p id="title"></p>
				</div>

				<form id="modalForm">
				<!--设置该属性，用于判断是新建还是编辑0：编辑，1新建  -->
				<input type="hidden" name="isNew" class="form-control" id="isNew">
					<div class="modal-body">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="userId">用户ID</label> 
									<input type="text" name="userId" class="form-control" id="userId">
								</div>
								<div class="form-group">
									<label for="orgName">所属机构</label> 
									<input type="text" name="orgName" class="form-control" id="orgName" onfocus="initOrg()"> 
									<input type="hidden" name="orgId" class="form-control" id="orgId">
								</div>
								<div class="form-group">
									<label for="userAddress">地址</label> 
									<input type="text" name="userAddress" class="form-control" id="userAddress">
								</div>
								<div class="form-group">
									<label for="userTel">座机</label> 
									<input type="text" name="userTel" class="form-control" id="userTel">
								</div>
								<div class="form-group">
									<label for="userBirthday">用户生日</label> 
									<input type="text" name="userBirthday" class="form-control" id="userBirthday" data-date-format="yyyy-mm-dd" readonly placeholder="YYYY-MM-DD">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group" id="passWord">
									<label for="userPassword">密码</label> 
									<input type="password" name="userPassword" class="form-control" id="userPassword" onblur="EncryptPassword()">
								</div>
								<div class="form-group">
									<label for="userName">用户名</label> 
									<input type="text" name="userName" class="form-control" id="userName">
								</div>

								<div class="form-group">
									<label for="userEmail">邮箱</label> 
									<input type="text" name="userEmail" class="form-control" id="userEmail">
								</div>
								<div class="form-group">
									<label for="userMobile">手机</label> 
									<input type="text" name="userMobile" class="form-control" id="userMobile">
								</div>
								<div class="form-group">
									<label for="roleName">用户角色</label> <input type="text"
										name="roleName" class="form-control" id="roleName"
										onfocus="initRole()"> <input type="hidden"
										name="roleId" class="form-control" id="roleId">
								</div>
								<div class="form-group">
									<label for="userLocked">用户账号状态</label> <select
										class="selectpicker form-control" data-style="btn-success"
										id="userLocked" name="userLocked">
										<option value="0">正常</option>
										<option value="1">锁定</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<june:btn type="cancle"></june:btn>
						<june:btn type="save"></june:btn>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="modal_june_web_new"></div>
	<div class="modal fade bs-example-modal-sm" id="treeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm" style="height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closeTreemodal()">×</button>
					组织机构选择
				</div>

				<div class="modal-body">
					<div class="row">
						<div>
							<div class="zTreeDemoBackground" style="width: 100%; height: 260px; overflow: auto;">
								<ul id="orgTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="closeTreemodal()">取消</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade bs-example-modal-sm" id="roleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm" style="height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closerolemodal()">×</button>
					角色选择
				</div>

				<div class="modal-body">
					<div class="row">
						<div>
							<div class="zTreeDemoBackground" style="width: 100%; height: 260px; overflow: auto;">
								<ul id="roleTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="closerolemodal()">取消</button>
					<button type="button" class="btn btn-primary" onclick="roleSureClick()">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>