<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<%
String name=(String)request.getSession().getAttribute("name");
if(name==null)
	response.sendRedirect("login.jsp");
 %>
 
<frameset border=3 
frameSpacing=3 rows=86,40,*  cols=*">
  <frame name="top" scrolling="no" src="../top.html">
  <frame name="topFrame" src="../welcomebar.jsp" noResize scrolling=no>
  <frameset  rows=*  cols=18%,*,18%>
    <frame name="left"  src="../left.jsp">
    <frame name="main" src="../userframe.jsp">
    <frame name="right"  src="../left.jsp">
  </frameset>
  <noframes>
  <body>

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>123

  </body>
  </noframes>
</frameset>
</html>
