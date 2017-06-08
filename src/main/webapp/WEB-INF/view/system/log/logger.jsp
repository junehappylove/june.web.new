<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JUNE_WEB_NEW</title>
<%@include file="../../common/import.jsp"%>
<script type="text/javascript" src="${ctx}/js/system/log/logger.js"></script>
</head>
<body>
    <div class="ibox-content">
        <div class="row row-lg">
            <div class="col-sm-12">
                <div class="example-wrap">
                    <h4 class="example-title">日志信息</h4>
                    <div class="example">
                        <form id="searchForm" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-1 control-label">模块</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control"
                                        name="operateModule" id="operateModule" />
                                </div>
                                <june:btn type="search" />
                            </div>
                        </form>
                        <div class="col-md-12">
                            <div id="toolbar" class="btn-group">
                                <june:btn type="insert" />
                                <june:btn type="update" />
                                <june:btn type="delete" />
                            </div>
                            <div class="row">
                                <!-- 菜单列表 -->
                                <div class="col-md-12">
                                    <table id="loggerTable"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>