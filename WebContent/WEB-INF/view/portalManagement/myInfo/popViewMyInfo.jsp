<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预览资讯</title>
</head>
<body>
 <div class="formbody">
       <h1 style="text-align:center;font-size:18px;">${content.title}</h1>
    <p class="info" style="text-align:center;">
    <c:if test="${not empty content.checkUser}">
		发布者: ${content.checkUser}
	</c:if>
		作者: ${content.author}</#if>
		<c:if test="${not empty content.checkDate}">
		发布时间: ${content.checkDate}
		</c:if>		
	</p>
	<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
	<div>
	${content.contentText}
	</div>
	 	
</div>
	 
</body>
</html>