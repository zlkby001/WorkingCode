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
String ip = request.getParameter("ip");
String username = request.getParameter("username");
String pubkey = request.getParameter("pubkey");
String verifydate = request.getParameter("verifydate");
String wldate = request.getParameter("wldate");

String addr = "userTree2.jsp?ip="+ip+"&username="+username+"&pubkey="+pubkey+"&verifydate="+verifydate+"&wldate="+wldate;

%>

<iframe border=0 frameSpacing=0  frameBorder=NO style="width:100%;height:800px" src="<%=addr %>">
</iframe>
  
  
</html>