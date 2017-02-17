<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/base/menu/qxsj_btn.js"></script>
</head>
<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<div class="example">
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<button id="btn_add" type="button"
									class="btn btn-outline btn-default" onclick="saveMenuBtn()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
								</button>
								<button id="btn_delete" type="button"
										class="btn btn-outline btn-default" onclick="deleteMenuBtn()">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
									</button>
								<button id="btn_add" type="button"
									class="btn btn-outline btn-default" onclick="useful()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>常用
								</button>
								
							</div>
							<table id="qxsjInfoTable"> </table>
							<input type="hidden" name="menu_id" value="${menu.menu_id}">
							<!-- appid作为必须查询条件 -->
							<input type="hidden" name="appid" value="${menu.menu_id}">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>