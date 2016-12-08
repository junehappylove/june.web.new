<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../common/import.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>无标题文档</title>

<script type="text/javascript"
	src="${ctx}/js/portalManagement/checkInfo/checkInfo.js"></script>
</head>

<body>
	<div class="formbody">

		<div class="tabson">

			<div id="search">
				<form id="searchInfo">
				<ul class="seachform">
					<li><label>资讯模块</label>
	        	<select
					class="easyui-combobox" id="channel" name="channelId"
					style="width: 150px; height: 32px" data-options="editable:false">
						<option value="">--请选择--</option>
						<c:forEach items="${channelList}" var="row" varStatus="status">
							<option value="${row.channelId}">${row.channelName}</option>
						</c:forEach>
				</select>
	       		 </li>
	       		 <li><label>资讯类别</label>
	        	<select:select name="contentType" id="contentType" type="easyui-combobox" style="width:150px;height:32px;" dataoptions="editable:false" defaultValue="true"/>
	       		</li>
					<li><label>标题</label> <input id="title" name="title" class="easyui-textbox"
						style="width: 100px; height: 32px"></li>
					<li><label>摘要</label> <input id="summary" name="summary"
						class="easyui-textbox" style="width: 100px; height: 32px"></li>
					<li><label>&nbsp;</label><input id="search" type="button"
						class="scbtn" value="查询" onclick="searchCheckInfo()" /></li>
				</ul>
				<ul class="toolbar_r">
					<li class="click" ><span title="批量通过审核"><img
							src="${ctx}/images/batchpass.png"
							onclick="batchCheckPass()" /></span></li>
					<li class="click" ><span title="批量驳回资讯"><img
							src="${ctx}/images/batchback.png"
							onclick="batchCheckBack()" /></span></li>
				</ul>
				</form>
			</div>

			<div class='myGrid'>
				<div id="CheckInfoGrid"></div>
			</div>
		</div>
	</div>

		<script>
			$('.myGrid').css('height', $(document).height() - 100);
		</script>
</body>
</html>