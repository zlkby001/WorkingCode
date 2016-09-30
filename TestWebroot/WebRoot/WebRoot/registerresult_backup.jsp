<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>RegisterResult</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
<br>
<br>
<% 
String r=(String)request.getAttribute("result");

	out.println(r);

%>
<br>
<br>

<a href="../login.jsp">返回登录页面</a>

</body>
</html>