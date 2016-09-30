<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
<%
 String address=(String)request.getAttribute("address");
 out.print("<br><a href='"+address+"'>点击保存导出的属性映射文件</a>");
%>
</body>
</html>