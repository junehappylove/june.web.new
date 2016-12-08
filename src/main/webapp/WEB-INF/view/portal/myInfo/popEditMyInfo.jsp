<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑资讯</title>
</head>
<body>
 <div class="formbody">
    <form id="releaseInfo">
       <div class="tabson">
       <input type="hidden" name="contentId" id="contentId" value="${contentId}"></input>
			<ul class="forminfo">
				<li><label>资讯模块<b>*</b></label>
	        	<select
					class="easyui-combobox" id="channel" name="channelId"
					style="width: 150px; height: 32px" data-options="editable:false">
						<c:forEach items="${channelList}" var="row" varStatus="status">
							<option value="${row.channelId}" <c:if test="${channelId==row.channelId}">selected="true"</c:if>>${row.channelName}</option>
						</c:forEach>
				</select>
				&nbsp;&nbsp;是否允许评论<input type="checkbox" name="commentState" id="commentState" value="true" <c:if test="${commentState=='0'}">checked="checked"</c:if> />
	       		 </li>
	       		 <li><label>资讯类别<b>*</b></label>
	        	<select:select name="contentType" id="contentType" type="easyui-combobox" style="width:150px;height:32px;" dataoptions="editable:false"/>
	       		</li>
	       		<li><label>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题<b>*</b></label><input id="title" name="title" value="${title}" type="text" class="easyui-textbox"  style="width:200px;height:32px"/></li>
	       		<li><label>摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要<b>*</b></label><textarea name="summary" class="easyui-textbox" data-options="multiline:true" id="summary" style="width:400px;height:60px">${summary}</textarea></li>
				<li></li>
				<FCK:editor instanceName="content_text" height="300px">
				<jsp:attribute name="value">${contentText}</jsp:attribute>
			</FCK:editor> 
			</ul> 
	 	</div>
	 	</form>
	 	
	</div>
	 
	 <div class="windowBtn">
	 		<input name="saveInfo" id="saveInfo" type="button"  class="btn" value="保存" onclick="saveInfo()"/>
        	<input name="submitInfo" id="submitInfo" type="button"  class="btn" value="提交" onclick="showConfirmbebeforeSubmit()"/>
        </div>
<script>
//保存修改资讯
function saveInfo()
{
	if(check())
	{
		var data = getFormJson("releaseInfo");
		var fckeditor = FCKeditorAPI.GetInstance('content_text');
		var str = fckeditor.GetHTML(); //这就是内容
		data.contentText= str;
		doAjax("post",contextPath + "/portal/MyInfo/saveContent",data,"savesuccess",null,false);
	}
}

//回调函数
function savesuccess(response)
{
	$("#mainFrame")[0].contentWindow.$("#content")[0].contentWindow.saveChangeSuccess();
}

//提交资讯前弹出确认消息
function showConfirmbebeforeSubmit()
{
	if(check())
	{
		showConfirm("submitInfo",getMessageFromList("SubmitContent",null),null,null,contextPath + "/portal/ReleaseInfo/submitContent",submitsuccess,false);
	}

}

//提交资讯
function submitInfo()
{
	var data = getFormJson("releaseInfo");
	var value = getFckContent('content_text');
	data.contentText= value;
	doAjax("post",contextPath + "/portal/MyInfo/submitContent",data,"submitsuccess",null,false);
}

function submitsuccess(response)
{
	$("#mainFrame")[0].contentWindow.$("#content")[0].contentWindow.submitChangeSuccess();
}

function check()
{
	checkMustInput("title","标题");
	checkMustInput("summary","摘要");
	checkFckEditor("content_text","资讯内容");
	maxLength("title",255,"标题","255");
	maxLength("summary",255,"摘要","255");
	return showMessage("error");
}
</script>
</body>
</html>