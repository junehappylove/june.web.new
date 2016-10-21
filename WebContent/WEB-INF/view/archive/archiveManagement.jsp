<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/archive/archive.js"></script>
</head>
<body>
	 <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <!-- Example Card View -->
                        <div class="example-wrap">
                            <h4 class="example-title">档案信息</h4>
                            <div class="example">
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<button id="btn_add" type="button" class="btn btn-outline btn-default"
								onclick="addNew()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
								</button>
							<c:if test="${userinfo_btnEdit eq 'hasAuthority'}">
								<button id="btn_edit" type="button" class="btn btn-outline btn-default"
									onclick="editRow()">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
							</c:if>
							<c:if test="${userinfo_btnDelete eq 'hasAuthority'}">
								<button id="btn_delete" type="button" class="btn btn-outline btn-default"
									onclick="deleteRow()">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
								</button>
							</c:if>
							</div>
							<table id="archiveTable">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemodal()">×</button>
					<p id="title">添加档案</p>
				</div>
				<form enctype="multipart/form-data"  id="uploadForm" class="form-horizontal m-t">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">档案名称：</label>
                                <div class="col-sm-8">
                                    <input id="archiveName" name="archiveName" minlength="2" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                              <label class="col-sm-3 control-label">添加图片：</label>
                              <div class="col-sm-8">
								<input id="uploadInput" name="myfiles" type="file" multiple class="file-loading">
							</div>
							</div>
                        
                    <div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="closemodal()">取消</button>
						<button type="submit" id="saveBtn" class="btn btn-primary">保存</button>
					</div>
					</form>
						<!-- <form enctype="multipart/form-data"  id="uploadForm">

					<label>提交有file的form表单</label>
						<input id="uploadInput" name="myfiles" type="file" multiple class="file-loading">
						<div class="col-lg-3">
								<input type="text" class="form-control" id="startDate" name="startDate" placeholder="YYYY-MM-DD">
						</div>
						    <div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="closemodal()">取消</button>
						<button type="submit" id="saveBtn" class="btn btn-primary">保存</button>
					</div>
					</form> -->
			</div>
		</div>
	</div>
</body>
</html>