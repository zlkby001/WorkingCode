<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK" import="cn.ac.sklois.imm.admin.*"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>查看个人信息</title>
</head>
<body bgcolor="#4780C3">
<%
AdminBean a=(AdminBean)request.getAttribute("a");
out.println("姓名："+a.getName());
out.println("<br>");
out.println("ID号："+a.getOperatorID());
out.println("<br>");
out.println("地址："+a.getAddress());
out.println("<br>");
out.println("电话号码："+a.getPhoneNum());
out.println("<br>");
out.println("Email："+a.getEmail());
 %>

</body>
</html>