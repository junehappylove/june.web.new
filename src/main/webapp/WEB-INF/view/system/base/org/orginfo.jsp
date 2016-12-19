<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../../../common/import.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/system/base/org/orgInfo.js"></script>
</head>
<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<h4 class="example-title">组织信息</h4>
					<div class="example">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">组织ID</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="orgId" id="orgId" />
								</div>
								<label class="col-sm-1 control-label">组织名</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="orgName"
										id="orgName" />
								</div>
								<c:if test="${search eq 'hasAuthority'}">
									<button id="btn_add" type="button" class="btn btn-primary"
										onclick="searchUserInfo()">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
									</button>
								</c:if>
							</div>
						</form>
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<c:if test="${btnAdd eq 'hasAuthority'}">
									<button id="btn_add" type="button" class="btn btn-outline btn-default" onclick="addNew()">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
									</button>
								</c:if>
								<c:if test="${btnEdit eq 'hasAuthority'}">
									<button id="btn_edit" type="button" class="btn btn-outline btn-default" onclick="editRow()">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										编辑
									</button>
								</c:if>
								<c:if test="${btnDelete eq 'hasAuthority'}">
									<button id="btn_delete" type="button"
										class="btn btn-outline btn-default" onclick="deleteRow()">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
									</button>
								</c:if>
							</div>
							<table id="orgInfoTable">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog" style="height: 1000px;">
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
							<div class="col-md-12">
								<div class="form-group">
									<label for="orgName">组织名</label> <input type="text"
										name="orgName" class="form-control" id="orgName">
								</div>
								<div class="form-group">
									<label for="parentOrgId">上级机构</label> <input type="text"
										name="parentOrgId" class="form-control" id="parentOrgId"
										onfocus="initOrg()"> <input type="hidden" name="orgId"
										class="form-control" id="orgId">
								</div>
								<div class="form-group">
									<label for="remark">地址</label> <input type="text" name="remark"
										class="form-control" id="remark">
								</div>
								<div class="form-group">
									<label for="orgLeader">领导</label> <input type="text"
										name="orgLeader" class="form-control" id="orgLeader">
								</div>
								<div class="form-group">
									<label for="delFlag">组织状态</label> <select
										class="selectpicker form-control" data-style="btn-success"
										id="delFlag" name="delFlag">
										<option value="0">正常</option>
										<option value="1">删除</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<c:if
							test="${btnAddSave eq 'hasAuthority' || btnEditSave eq 'hasAuthority'}">
							<button type="button" class="btn btn-primary"
								onclick="closemodal()">取消</button>
							<button type="submit" id="saveBtn" class="btn btn-primary">保存</button>
						</c:if>
					</div>
				</form>

			</div>
		</div>
	</div>
	<div class="modal fade bs-example-modal-sm" id="treeModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog modal-sm" style="height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closeTreemodal()">×</button>
					组织机构选择
				</div>

				<div class="modal-body">
					<div class="row">
						<div>
							<div class="zTreeDemoBackground" style="width: 100%; height: 280px; overflow: auto;">
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
</body>
</html>