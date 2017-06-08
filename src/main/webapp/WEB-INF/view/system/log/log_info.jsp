<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志修改</title>
<%@include file="../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/log/log_info.js"></script>
</head>
<body>
<!-- 添加或者修改菜单 -->
<form id="modalForm">
    <input type="hidden" name="isNew" class="form-control" id="isNew">
    <div class="modal-body">
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label for="loggerId">日志ID</label> 
                    <input type="text" name="loggerId" class="form-control" id="loggerId" value="${loggerId }">
                </div>
                <div class="form-group">
                    <label for="userId">用户</label> 
                    <input type="hidden" name="userId" class="form-control" id="userId">
                    <input type="text" name="userName" class="form-control" id="userName"> 
                </div>
                <div class="form-group">
                    <label for="operateType">类型</label> 
                    <input type="text" name="operateType" class="form-control" id="operateType">
                </div>
                <div class="form-group">
                    <label for="operateModule">模块</label> 
                    <input type="text" name="operateModule" class="form-control" id="operateModule">
                </div>
                <div class="form-group">
                    <label for="operateRemark">描述</label> 
                    <textarea name="operateRemark" class="form-control" id="operateRemark"></textarea>
                </div>
                <div class="form-group">
                    <label for="operateMethod">方法</label> 
                    <textarea name="operateMethod" class="form-control" id="operateMethod"></textarea>
                </div>
                <div class="form-group">
                    <label for="operateParams">参数</label> 
                    <textarea name="operateParams" class="form-control" id="operateParams"></textarea>
                </div>
                <div class="form-group">
                    <label for="operateTime">时间</label> 
                    <input type="text" name="operateTime" class="form-control" id="operateTime">
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>