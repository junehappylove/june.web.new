<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/demo/leave.js"></script>
</head>
<body>
	 <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <!-- Example Card View -->
                        <div class="example-wrap">
                            <h4 class="example-title">休假信息</h4>
                            <div class="example">
						 <form id="searchForm" method="post" class="form-horizontal">
		                    <div class="form-group">
		                            <label class="col-sm-2 control-label" style="text-align:center;">请假开始时间</label>
		                            <div class="col-sm-2">
		                                <input type="text" class="form-control" name="leaveStart" id="leaveStartConditiron" />
		                            </div>
		                            <label class="col-sm-2 control-label" style="text-align:center;">请假结束时间</label>
		                            <div class="col-sm-2">
		                                <input type="text" class="form-control" name="leaveEnd" id="leaveEndConditiron"/>
		                            </div>
		                           <%--  <c:if test="${role_search eq 'hasAuthority'}"> --%>
		                             <button id="btn_add" type="button" class="btn btn-primary" onclick="searchLeaveInfo()">
										<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
									</button>
									<%-- </c:if> --%>
		                    </div>
						</form>
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
							<%-- <c:if test="${role_btnAdd eq 'hasAuthority'}"> --%>
								<button id="btn_add" type="button" class="btn btn-outline btn-default"
								onclick="applyLeave()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>申请休假
								</button>
								<%-- </c:if> --%>
								
							</div>
							<table id="leavegrid">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal fade" id="applyLeaveModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemodal()">×</button>
					<p id="title">申请休假</p>
				</div>

				<form id="modalForm" class="form-horizontal">
					<div class="modal-body">
                    <fieldset>
                         <div class="form-group">
                        <label class="col-sm-3 control-label">请假原因</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveReason" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假开始时间</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveStart" id="leaveStart"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假结束时间</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveEnd" id="leaveEnd"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假天数</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveDays" id="leaveDays" disabled/>
                        </div>
                    </div>
                    </fieldset>     
                    
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="closemodal()">取消</button>
						<button type="submit" id="submitLeave" class="btn btn-primary">确定</button>
					</div>
				</form>

			</div>
		</div>
	</div>
</body>
</html>