<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../common/import.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DEMO</title>

<script type="text/javascript" src="${ctx}/js/demo/demo.js"></script>
<body>
	<div id="content" class="col-lg-10 col-sm-10">
		<!-- content starts -->
		<div class="row">
			<div class="box col-md-12">
				<div class="box-inner">
					<div class="page-header">
						<h2>Ajax提交form表单</h2>
					</div>

					<form id="defaultForm" method="post" class="form-horizontal"
						action="${ctx}/demo/demo">
						<div class="form-group">
							<label class="col-lg-3 control-label">Username</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="username" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">Email address</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="email" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">Password</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="password" />
							</div>
						</div>

						<!---------------------- DateTime Picker -------------------->
						<div class="form-group">
							<label class="col-lg-3 control-label">DateTime</label>
							<div class="col-lg-3">
								<input type="text" class="form-control" id="meeting" name="meeting">
							</div>
						</div>

						<!---------------------- Start date -------------------->
						<div class="form-group">
							<label class="col-lg-3 control-label">StartDate</label>
							<div class="col-lg-3">
								<input type="text" class="form-control" id="startDate" name="startDate" placeholder="YYYY-MM-DD">
							</div>
						</div>
						
						<!---------------------- End date -------------------->
						<div class="form-group">
							<label class="col-lg-3 control-label">EndDate</label>
							<div class="col-lg-3">
								<input type="text" class="form-control" id="endDate" name="endDate" placeholder="YYYY-MM-DD">
							</div>
						</div>
						
						<!---------------------- date range -------------------->
						<div class="form-group">
							<label class="col-lg-3 control-label">日期范围</label>
							<div class="col-lg-4">
								<input class="form-control" type="text" name="daterange"
									id="daterange">
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">下拉框</label>
							<div class="col-lg-3">
								<select class="selectpicker form-control"
									data-style="btn-success">
									<!-- <option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option> -->
									<c:forEach items="${list}" var="row" varStatus="status">
										<option value="${row.code}"
											<c:if test="${title==row.code}">selected="true"</c:if>>${row.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">Gender</label>
							<div class="col-lg-5">
								<div class="radio">
									<label> <input type="radio" name="gender" value="male" />
										Male
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="gender"
										value="female" /> Female
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="gender" value="other" />
										Other
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">Languages</label>
							<div class="col-lg-5">
								<div class="checkbox">
									<label> <input type="checkbox" name="languages"
										value="english" /> English
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="languages"
										value="french" /> French
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="languages"
										value="german" /> German
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="languages"
										value="russian" /> Russian
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="languages"
										value="other" /> Other
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">Programming
								Languages</label>
							<div class="col-lg-5">
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="net" /> .Net
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="java" /> Java
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="c" /> C/C++
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="php" /> PHP
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="perl" /> Perl
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="ruby" /> Ruby
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="python" /> Python
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="programs"
										value="javascript" /> Javascript
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">级联1</label>
							<div class="col-lg-3">
								<select class="selectpicker form-control"
									data-style="btn-success"
									onchange="selectChange(this.options[this.options.selectedIndex].value)">
									<c:forEach items="${list}" var="row" varStatus="status">
										<option value="${row.code}"
											<c:if test="${title==row.code}">selected="true"</c:if>>${row.name}</option>
									</c:forEach>
								</select>
							</div>
							
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">级联2</label>
							<div class="col-lg-3">
								<select id="select" class="selectpicker form-control"
									data-style="btn-success" aria-expanded="false">
								</select>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-9 col-lg-offset-3">
								<button type="submit" class="btn btn-primary">Sign up</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="box col-md-12">
				<div class="box-inner">
					<div class="box-header well">
						<h2>
							<i class="glyphicon glyphicon-info-sign"></i> tree
						</h2>
					</div>
					<div class="center">
						<label>树(子节点非异步加载)</label>
						<div class="zTreeDemoBackground center" style="width: 150px;">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
					<label>树(子节点异步加载)</label>
					<div class="zTreeDemoBackground left">
						<ul id="ajaxtreeDemo" class="ztree"></ul>
					</div>
					<label>combo树(子节点非异步加载)</label>
					<div class="zTreeDemoBackground left">
						<ul id="combotreeDemo" class="ztree"></ul>
					</div>
					<label>combo树(子节点异步加载)</label>
					<div class="zTreeDemoBackground left">
						<ul id="ajaxcombotreeDemo" class="ztree"></ul>
					</div>
					<button type="button" class="btn btn-primary"
						onclick="getchecked()">获取选中的checkbox</button>
					<button type="button" class="btn btn-primary" onclick="expandAll()">折叠全部节点</button>
					<button type="button" class="btn btn-primary"
						onclick="collapseAll()">展开全部节点</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="box col-md-12">
				<div class="box-inner">
					<div class="box-header well">
						<h2>
							<i class="glyphicon glyphicon-info-sign"></i> uploadFile
						</h2>
					</div>
					<div>
					<form enctype="multipart/form-data"  id="uploadForm">
						<label>提交有file的form表单</label>
						<input id="uploadInput" name="myfiles" type="file" multiple class="file-loading">
					</form>
					</div>
					</br>
					<div>
					<form enctype="multipart/form-data"  id="uploadForm1">
						<label>单独提交file</label>
						<input id="uploadInput1" name="myfiles" type="file" multiple class="file-loading">
						<button type="submit" class="btn btn-primary">上传</button>
					</form>
					</div>
			</div>
		</div>
	</div>
	</div>
<!-- <script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
    </script> -->
</body>
</html>