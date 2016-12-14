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
		                            <c:if test="${search eq 'hasAuthority'}">
		                             <button id="btn_add" type="button" class="btn btn-primary" onclick="searchRoleInfo()">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
									</button>
									</c:if>
		                    </div>
						</form>
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
							<c:if test="${btnAdd eq 'hasAuthority'}">
								<button id="btn_add" type="button" class="btn btn-outline btn-default"
								onclick="addNew()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
								</button>
								</c:if>
								<c:if test="${btnEdit eq 'hasAuthority'}">
								<button id="btn_edit" type="button" class="btn btn-outline btn-default"
									onclick="editRow()">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
								</c:if>
								<c:if test="${btnDelete eq 'hasAuthority'}">
								<button id="btn_delete" type="button" class="btn btn-outline btn-default"
									onclick="deleteRow()">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
								</button>
								</c:if>
								<c:if test="${btnAssignUser eq 'hasAuthority'}">
								<button id="btn_assignUser" type="button" class="btn btn-outline btn-default"
									onclick="initUser()">
									<span class="glyphicon glyphicon-user" aria-hidden="true"></span>分配用户
								</button>
								</c:if>
								<c:if test="${btnAssignMenu eq 'hasAuthority'}">
								<button id="btn_assignMenu" type="button" class="btn btn-outline btn-default" onclick="initMenu()">
									<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>分配菜单
								</button>
								</c:if>
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
					<c:if test="${saveAssignUser eq 'hasAuthority' || saveAssignMenu eq 'hasAuthority'}">
						<button type="button" class="btn btn-primary"
							onclick="closemodal()">取消</button>
						<button type="submit" id="saveBtn" class="btn btn-primary">保存</button>
						</c:if>
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
				<c:if test="${saveAssignMenu eq 'hasAuthority'}">
					<button type="button" class="btn btn-primary"
						onclick="closemenumodal()">取消</button>
					<button type="button" id="saveAssignMenu" class="btn btn-primary" onclick="assignMenu()">确定</button>
				</c:if>
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
				<c:if test="${saveAssignUser eq 'hasAuthority'}">
					<button type="button" class="btn btn-primary"
						onclick="closeusermodal()">取消</button>
					<button type="button" id="saveAssignUser" class="btn btn-primary" onclick="assignUser()">确定</button>
				</c:if>
				</div>

			</div>
		</div>
	</div>
</body>
</html>