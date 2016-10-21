<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/demo/quartzList.js"></script>
</head>
<body>
	<div id="content" class="col-lg-10 col-sm-10">
		<!-- content starts -->
		<div>
			<ul class="breadcrumb">
				<li><a href="#">系统设置</a></li>
				<li><a href="#">基本设置</a></li>
				<li><a href="#">定时任务</a></li>
			</ul>
		</div>
		<div class="row">
			<div class="box col-md-12">
				<div class="box-inner">
					<div class="box-header well">
						<h2>
							<i class="glyphicon glyphicon-info-sign"></i> 定时任务
						</h2>
					</div>
					<div class="box-content row">
						<div class="col-md-12">
						<div id="toolbar" class="btn-group">
								<button id="btn_add" type="button" class="btn btn-default"
								onclick="addsimple()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加simpletrigger
								</button>
								<button id="btn_add" type="button" class="btn btn-default"
								onclick="addcron()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加crontrigger
								</button>
							</div>
							<table id="quartzList">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<div class="modal fade" id="simpletriggermodal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closemodal()">×</button>
					<p id="title">添加simpletrigger</p>
				</div>

				<form id="modalForm" class="form-horizontal">
					<div class="modal-body">
                    <fieldset>
                         <div class="form-group">
                        <label class="col-sm-4 control-label">trigger名称(必填)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="triggerName" />
                        </div>
                    </div>
					<div class="form-group">
							<label class="col-sm-4 control-label">job名称(必选)</label>
							<div class="col-sm-5">
								<select id="jobName" class="selectpicker form-control">
									
								</select>
							</div>
						</div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">执行开始时间(必填)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="startTime" id="startTime"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">执行结束时间(非必填)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="endTime" id="endTime"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">执行次数(不写执行一次)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="repeat" id="repeat"/>
                        </div>
                         <label class="col-sm-2 control-label" style="text-align:left;">次</label>
                    </div>
                     <div class="form-group">
                        <label class="col-sm-4 control-label">执行间隔</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="interval" id="interval"/>
                            <span class="help-block">表示trigger间隔多长时间执行一次，不填写前后两次执行没有时间间隔，直到任务结束</span>
                        </div>
                        <label class="col-sm-2 control-label" style="text-align:left;">秒</label>
                    </div>
                    </fieldset>     
                    
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="closemodal()">取消</button>
						<button type="submit" id="submitSimpleTrigger" class="btn btn-primary">确定</button>
					</div>
				</form>

			</div>
		</div>
	</div>
	
	<div class="modal fade" id="crontriggermodal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="closecronmodal()">×</button>
					<p id="title">添加crontrigger</p>
				</div>

				<form id="cronModalForm" class="form-horizontal">
					<div class="modal-body">
                    <fieldset>
                         <div class="form-group">
                        <label class="col-sm-4 control-label">trigger名称(必填)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="triggerName" />
                        </div>
                    </div>
					<div class="form-group">
							<label class="col-sm-4 control-label">job名称(必选)</label>
							<div class="col-sm-5">
								<select id="jobName" class="selectpicker form-control">
									
								</select>
							</div>
						</div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">cron表达式(必填)</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="cronExpress" id="cronExpress"/>
                            <span class="help-block">cron表达式，如("0/10 * * ? * * *",每十秒执行一次)，对使用者要求比较高，要求会写cron表达式，实际项目中不适用</span>
                        </div>
                    </div>
                    </fieldset>     
                    
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="closecronmodal()">取消</button>
						<button type="submit" id="submitSimpleTrigger" class="btn btn-primary">确定</button>
					</div>
				</form>

			</div>
		</div>
	</div>
</body>
</html>