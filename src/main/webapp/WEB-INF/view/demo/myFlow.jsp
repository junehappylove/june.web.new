<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/demo/myflow.js"></script>
</head>
<body>
	 <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <!-- Example Card View -->
                        <div class="example-wrap">
                            <h4 class="example-title">我的流程</h4>
					<div class="box-content row">
						<div class="col-md-12">
							<table id="myFlow">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<div class="modal fade" id="processModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemodal()">×</button>
					<p id="title">流程审批</p>
				</div>

				<form id="modalForm" class="form-horizontal">
				   <input  type="hidden" name="taskId" id="taskId"></input>
       			   <input type="hidden" name="leaveid" id="leaveid"></input>
					<div class="modal-body">
                    <fieldset>
                    <div class="form-group">
                        <table id="history"></table>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假原因</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveReason" id="leaveReason" disabled/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假开始时间</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveStart" id="leaveStart" disabled/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假结束时间</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveEnd" id="leaveEnd" disabled/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">请假天数</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveDays" id="leaveDays" disabled/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">批注</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="leaveNote" id="leaveNote"/>
                        </div>
                    </div>
                    </fieldset>     
                    
					</div>
					<div class="modal-footer">
						<button type="button" id="disagree" class="btn btn-primary"
							onclick="doreject()"></button>
						<button type="submit" id="agree" class="btn btn-primary"></button>
					</div>
				</form>

			</div>
		</div>
	</div>	
	
	<div class="modal fade" id="imageModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closeviewmodal()">×</button>
					<p id="title">查看流程图</p>
				</div>

				<form id="modalForm" class="form-horizontal">
					<div class="modal-body">
					<div class="form-group">
						<img id="viewimage" src=""/> 
						</div>                   
					</div>
				</form>

			</div>
		</div>
	</div>	
</body>
</html>