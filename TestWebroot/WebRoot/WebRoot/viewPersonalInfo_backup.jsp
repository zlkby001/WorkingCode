<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK" import="cn.ac.sklois.imm.admin.*"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�鿴������Ϣ</title>
</head>
<body bgcolor="#4780C3">
<%
AdminBean a=(AdminBean)request.getAttribute("a");
out.println("������"+a.getName());
out.println("<br>");
out.println("ID�ţ�"+a.getOperatorID());
out.println("<br>");
out.println("��ַ��"+a.getAddress());
out.println("<br>");
out.println("�绰���룺"+a.getPhoneNum());
out.println("<br>");
out.println("Email��"+a.getEmail());
 %>

</body>
</html>