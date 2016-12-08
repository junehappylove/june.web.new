<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../common/import.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资讯信息</title>
<script type="text/javascript" src="${ctx}/js/portal/releaseInfo/releaseInfo.js"></script>
</head>
<body>

    <div class="formbody">
    <form id="releaseInfo">
       <div class="tabson">
       <input type="hidden" name="contentId" id="contentId"></input>
			<ul class="forminfo">
				<li><label>资讯模块<b>*</b></label>
	        	<select
					class="easyui-combobox" id="channel" name="channelId"
					style="width: 150px; height: 32px" data-options="editable:false">
						<c:forEach items="${channelList}" var="row" varStatus="status">
							<option value="${row.channelId}">${row.channelName}</option>
						</c:forEach>
				</select>
				&nbsp;&nbsp;是否允许评论<input type="checkbox" name="commentState" id="commentState" value="true" checked="checked"/>
	       		 </li>
	       		<li><label>资讯类别<b>*</b></label>
	        	<select:select name="contentType" id="contentType" type="easyui-combobox" style="width:150px;height:32px;" dataoptions="editable:false"/>
	       		</li>
	       		<li><label>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题<b>*</b></label><input id="title" name="title" type="text" class="easyui-textbox"  style="width:200px;height:32px"/></li>
	       		<li><label>摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要<b>*</b></label><textarea name="summary" class="easyui-textbox" data-options="multiline:true" id="summary" style="width:400px;height:60px"></textarea></li>
				<li>
				</li>
				
			</ul> 
	 	</div>
	 	<div>
				<textarea name="content"></textarea>
				<script type="text/javascript">
				var editor = CKEDITOR.replace("content");
				CKFinder.setupCKEditor( editor, '${ctx}/ckfinder/' );
				</script>
		</div>
	 	</form>
	 	
	</div>
	 
	 <div class="windowBtn">
	 		<input name="saveInfo" id="saveInfo" type="button"  class="btn" value="保存" onclick="saveInfo()"/>
        	<input name="submitInfo" id="submitInfo" type="button"  class="btn" value="提交" onclick="showConfirmbebeforeSubmit()"/>
     </div>
</body>
</html>