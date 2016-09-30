<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>个人信息修改结果</title>
</head>
<body bgcolor="#4780C3">
<br>
<br>
<% String r=(String)request.getAttribute("result");
			
	out.print(r);			
%>
<br>

<a href="personalInfoServlet">返回</a>
</body>
</html>