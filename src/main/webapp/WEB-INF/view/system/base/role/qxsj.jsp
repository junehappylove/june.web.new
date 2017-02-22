<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/base/role/qxsj.js"></script>
</head>
<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<h4 class="example-title">权限信息</h4>
					<div class="example">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">权限代码</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="qxsj_code" id="qxsj_code" />
								</div>
								<label class="col-sm-1 control-label">权限名</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="qxsj_name" id="qxsj_name" />
								</div>
								<label class="col-sm-1 control-label">权限类型</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="qxsj_type" id="qxsj_type" />
								</div>
								<june:btn type="search"></june:btn>
							</div>
						</form>
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<june:btn type="insert"></june:btn>
								<june:btn type="update"></june:btn>
								<june:btn type="delete"></june:btn>
							</div>
							<table id="qxsjInfoTable">
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
									<label for="qxsj_code">权限代码</label> 
									<input type="text" name="qxsj_code" class="form-control" id="qxsj_code">
								</div>
								<div class="form-group">
									<label for="qxsj_name">权限名称</label> 
									<input type="text" name="qxsj_name" class="form-control" id="qxsj_name"> 
								</div>
								<div class="form-group">
									<label for="qxsj_type">权限类型</label> 
									<select class="selectpicker form-control" data-style="btn-success" id="qxsj_type" name="qxsj_type">
										<option value="CUS">自定义</option>
										<option value="COM">通用</option>
									</select>
									<input type="hidden" name="qxsj_type_msk" class="form-control" id="qxsj_type_msk">
								</div>
								<div class="form-group">
									<label for="qxsj_menu">权限菜单</label> 
									<input type="text" name="qxsj_menu" class="form-control" id="qxsj_menu">
									<!-- TODO 这里应该能够根据菜单id展示菜单的名称 （树型菜单列表）-->
								</div>
								<div class="form-group">
									<label for="qxsj_sort">权限排序</label> 
									<input type="text" name="qxsj_sort" class="form-control" id="qxsj_sort">
								</div>
								<div class="form-group">
									<label for="qxsj_used">权限状态</label> 
									<select class="selectpicker form-control" data-style="btn-success" id="qxsj_used" name="qxsj_used">
										<option value="Y">正常</option>
										<option value="N">停用</option>
									</select>
								</div>
								<div class="form-group">
									<label for="qxsj_sort">权限描述</label> 
									<textarea name="qxsj_text" class="form-control" id="qxsj_text"></textarea>
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
</body>
</html>