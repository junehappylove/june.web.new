<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/import.jsp"%>
<title>index</title>
<script language="JavaScript">
var frontuserid = "<%=session.getAttribute("frontuserid")%>";
var frontLogin = "<%=session.getAttribute("frontLogin")%>";
jQuery(function(){ 
	//在元素内部追加内容 
	if(frontuserid != "null" && frontLogin == "success")
	{
		$("#head").append("欢迎您," + frontuserid);
		$("#head").append("<a id='href' href = '${ctx}/frontLogin/frontlogout'" + "></a>");
		$("#href").text("退出登录");
		
	}else{
		$("#head").append("<a id='href' href = '${ctx}/frontLogin/'" + "></a>");
		$("#href").text("请登录");
	}
	}) 
</script>
<body>
<div id="head">

</div>
<c:forEach items="${list}" var="article" >
		 <a href="${ctx}/checkDetail/detail/${article.contentId}" target="_blank">${article.title}</a><br>
</c:forEach>


</body>

</html>