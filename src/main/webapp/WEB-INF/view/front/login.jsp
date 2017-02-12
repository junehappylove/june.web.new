<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/import.jsp"%>
<title>login</title>
<script type="text/javascript" src="${ctx}/js/login/login.js"></script>
</head>
<body>
<form action="${ctx}/frontLogin/main" method="post">
用户名：<input type="text" name="username" value="admin" id="username"/> <br><br>
密 &nbsp;&nbsp;&nbsp;码：<input type="password" value="admin" name="password" id="password"/> <br><br>
<input type="submit" value="登陆" onclick = "return btnloginclick()"/>
</form>

</body>
<script>
var errormsg = '${error}';
 if(errormsg != "")
{
	 //$.messager.alert('Error',"errormsg",'error');
	showOnlyMessage("error",errormsg);
}
</script>
<script>
var logout = '${logout}';
 if(logout != "")
{
	showOnlyMessage("info",logout);
}
</script>
</html>