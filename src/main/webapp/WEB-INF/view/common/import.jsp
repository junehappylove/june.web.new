<%@page import="com.june.common.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="env" value="<%=Constants.ENVIRONMENT %>" />

<link href="${ctx}/jslib/bootstrap/css/bootstrap.min.css?v=3.3.5" type="text/css" rel="stylesheet">
<link href="${ctx}/jslib/bootstrap/css/font-awesome.min.css?v=4.4.0" type="text/css" rel="stylesheet">
<link href="${ctx}/jslib/bootstrap/css/animate.min.css" type="text/css" rel="stylesheet">
<link href="${ctx}/jslib/bootstrap/css/style.min.css?v=4.0.0" type="text/css" rel="stylesheet">
<link href="${ctx}/jslib/bootstrap-table/bootstrap-table.min.css" type="text/css" rel="stylesheet">
<link href="${ctx}/jslib/bootstrapValivator/dist/css/bootstrapValidator.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/jslib/bootstrap/bower_components/bootstrap-select/css/bootstrap-select.min.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/jslib/bootstrap/css/plugins/iCheck/custom.css" type="text/css" rel="stylesheet">
  
<link rel="stylesheet" href="${ctx}/jslib/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<!-- 日期时间选择器 -->
<link href="${ctx}/jslib/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/jslib/daterangepicker/daterangepicker.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/jslib/bootstrap-fileinput/css/fileinput.min.css" type="text/css" rel="stylesheet" />
<!-- 引入jQuery库 -->
<script src="${ctx}/jslib/jquery.min.js?v=1.11.3"></script>

<!-- external javascript -->
<script src="${ctx}/jslib/bootstrap/js/bootstrap.min.js?v=3.3.5"></script>
<script src="${ctx}/jslib/bootstrap/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/jslib/bootstrap/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/jslib/bootstrap/js/plugins/layer/layer.min.js"></script>
<script src="${ctx}/jslib/bootstrap/js/plugins/layer/laydate/laydate.js"></script>
<script src="${ctx}/jslib/bootstrap/js/hplus.min.js?v=4.0.0"></script>
<script src="${ctx}/jslib/bootstrap/js/contabs.min.js"></script>
<script src="${ctx}/jslib/bootstrap/js/plugins/pace/pace.min.js"></script>
<script src="${ctx}/jslib/bootstrap/js/content.min.js"></script>
<script src="${ctx}/jslib/bootstrap/js/plugins/iCheck/icheck.min.js"></script>
<!-- library for cookie management -->
<script src="${ctx}/jslib/bootstrap/js/jquery.cookie.js"></script>
<!-- data table plugin -->
<script src='${ctx}/jslib/bootstrap/js/jquery.dataTables.min.js'></script>

<!-- select or dropdown enhancer -->
<script src="${ctx}/jslib/bootstrap/bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="${ctx}/jslib/bootstrap/bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="${ctx}/jslib/bootstrap/js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="${ctx}/jslib/bootstrap/bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="${ctx}/jslib/bootstrap/bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="${ctx}/jslib/bootstrap/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="${ctx}/jslib/bootstrap/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="${ctx}/jslib/bootstrap/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script src="${ctx}/jslib/bootstrap/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script src="${ctx}/jslib/bootstrap/js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="${ctx}/jslib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/jslib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/jslib/bootstrapValivator/dist/js/bootstrapValidator.js"></script>
<!-- 日期时间选择器 -->
<script type="text/javascript" src="${ctx}/jslib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/jslib/bootstrap/bower_components/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="${ctx}/jslib/bootstrap-notify/dist/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx}/jslib/zTree/js/jquery.ztree.excheck.js"></script>
<!-- SmartMenus jQuery plugin -->
<script type="text/javascript" src="${ctx}/jslib/menu/jquery.smartmenus.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/daterangepicker/moment.js"></script>
<script type="text/javascript" src="${ctx}/jslib/daterangepicker/daterangepicker.js"></script>

<script type="text/javascript" src="${ctx}/jslib/md5.js"></script>
<script type="text/javascript" src="${ctx}/jslib/bootstrap-fileinput/js/fileinput.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>
<!-- 自定义插件 -->
<script src="${ctx}/jslib/bootbox/bootbox.min.js"></script>
<script src="${ctx}/jslib/common.js"></script>
<script src="${ctx}/jslib/check.js"></script>
<script src="${ctx}/jslib/message.js"></script>

<script src="${ctx}/jslib/ajaxfileupload.js"></script>
<script src="${ctx}/jslib/jquery.form.js"></script>
<script src="${ctx}/jslib/websocket/sockjs.min.js"></script>
<!-- 富文本编辑器 -->
<script src="${ctx}/jslib/ckeditor/ckeditor.js"></script>
<!-- ckfinder 整合 ckeditor -->
<%-- <script src="${ctx}/jslib/ckfinder/ckfinder.js"></script> --%>

<!-- 日期格式化控件 -->
<script type="text/javascript" src="${ctx }/jslib/date.format.js"></script>
<!-- 动态资源加载 -->
<script type="text/javascript" src="${ctx }/jslib/june_util.1.0.js"></script>
<script>
	var contextPath = '${ctx}';
	var ctx = contextPath;
</script>
<!-- 富头像上传编辑器 -->
<script src="${ctx}/jslib/avatar/swfobject.js"></script>
<script src="${ctx}/jslib/avatar/fullAvatarEditor.js"></script>
