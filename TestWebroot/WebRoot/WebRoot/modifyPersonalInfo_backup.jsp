<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"  import="cn.ac.sklois.imm.admin.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
</head>
<body bgcolor="#4780C3">

<%
AdminBean a=(AdminBean)request.getAttribute("a");
String name=a.getName();
String oid=a.getOperatorID();
String address=a.getAddress();
String phonenum=a.getPhoneNum();
String email=a.getEmail();
%>

<form action="modifyPersonalInfoServlet" method="post"><p align="center">
姓名：<input type="text" name="name" size="10" value="<%=name%>">&nbsp;&nbsp;&nbsp;&nbsp; 
ID号：<%out.print(oid); %><br>
　</p>
  <p align="center">
地址：<input type="text" name="address" size="98" value="<%=address%>"></p>
  <p align="center">
电话：<input type="text" name="phonenum" size="58" value="<%=phonenum%>"></p>
  <p align="center">
Email：<input type="text" name="email" size="59" value="<%=email%>"></p>
  <p align="center">
</p>
  <p align="center">
<br>
<input type="hidden" name="oid" value="<%out.print(oid);%>"> 
<input type="submit" value="提交"> <input type="reset" value="重置"> </p>
  <br>

</form>

  </body>
</html>