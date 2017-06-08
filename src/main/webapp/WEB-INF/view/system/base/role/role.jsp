<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/base/role/role.js"></script>
</head>
<body>
	 <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <!-- Example Card View -->
                        <div class="example-wrap">
                            <h4 class="example-title">角色信息</h4>
                            <div class="example">
						 <form id="searchForm" method="post" class="form-horizontal">
		                    <div class="form-group">
		                            <label class="col-sm-1 control-label">角色名称</label>
		                            <div class="col-sm-2">
		                                <input type="text" class="form-control" name="roleName" id="roleName" />
		                            </div>
		                            <label class="col-sm-1 control-label">角色描述</label>
		                            <div class="col-sm-2">
		                                <input type="text" class="form-control" name="roleDesc" id="roleDesc"/>
		                            </div>
									<june:btn type="search" onclick="searchRoleInfo()"></june:btn>
		                    </div>
						</form>
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<june:btn type="insert"></june:btn>
								<june:btn type="update"></june:btn>
								<june:btn type="delete"></june:btn>
								<june:btn type="custom" auth="btnAssignUser" onclick="initUser()" id="btn_assignUser" title="分配用户" icon="user" ></june:btn>
								<june:btn type="custom" auth="btnAssignMenu" onclick="initMenu()" id="btn_assignMenu" title="分配菜单" icon="list-alt" ></june:btn>
							</div>
							<table id="roleInfoTable">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemodal()">×</button>
					<p id="title"></p>
				</div>

				<form id="modalForm" class="form-horizontal">
				<!--设置该属性，用于判断是新建还是编辑0：编辑，1新建  -->
				<input type="hidden" name="isNew" class="form-control" id="isNew">
				<input type="hidden" name="roleId" class="form-control" id="roleId">
					<div class="modal-body">
                    <fieldset>
                       <div class="form-group">
                       <div class="group">
                          <label class="col-sm-2 control-label">角色名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="roleName" name="roleName" type="text" data-bv-group=".group"/>
                          </div>
                          </div>
                          <div class="group">
                          <label class="col-sm-2 control-label">角色描述</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="roleDesc" name="roleDesc" type="text" data-bv-group=".group"/>
                          </div>
                          </div>
                       </div>
                    </fieldset>     
                    
					</div>
					<div class="modal-footer">
						<june:btn type="cancle"></june:btn>
						<june:btn type="save"></june:btn>
					</div>
				</form>

			</div>
		</div>
	</div>
	
	<div class="modal fade bs-example-modal-sm" id="menuModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm" style="height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemenumodal()">×</button>
					分配角色权限
				</div>
				<div class="modal-body">
					<div class="row">
						<div>
							<div class="zTreeDemoBackground" style="width: 100%; height: 280px; overflow: auto;">
								<ul id="menuTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<june:btn type="cancle" onclick="closemenumodal()"></june:btn>
					<june:btn type="custom" auth="saveAssignMenu" id="saveAssignMenu" onclick="assignMenu()" css="btn btn-primary" icon="ok" title="确定"></june:btn>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade bs-example-modal-sm" id="userModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm" style="height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closeusermodal()">×</button>
					分配用户角色
				</div>

				<div class="modal-body">
					<div class="row">
						<div>
							<div class="zTreeDemoBackground" style="width: 100%; height: 280px; overflow: auto;">
								<ul id="userTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<june:btn type="cancle" onclick="closeusermodal()"></june:btn>
					<june:btn type="custom" auth="saveAssignUser" id="saveAssignUser" onclick="assignUser()" css="btn btn-primary" icon="ok" title="确定"></june:btn>
				</div>
			</div>
		</div>
	</div>
</body>
</html>