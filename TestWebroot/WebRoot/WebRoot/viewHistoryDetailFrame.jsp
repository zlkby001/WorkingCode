<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<title>Welcome</title>
</head>

<!-- 	String oid = request.getParameter("oid"); -->
<!-- 	String sdate = request.getParameter("sdate"); -->
<!-- 	String addr = "viewHistoryDetail.jsp?oid="+oid+"&sdate="+sdate; -->
<!-- 	//session.setAttribute("oid",oid); -->
<!-- 	//session.setAttribute("sdate",sdate); -->
<!-- 	System.out.println("--------01---  " + addr + "  ------------" ); -->
<iframe  border=0 frameSpacing=0  frameBorder=NO  style="width:100%;height:800px" src="viewHistoryDetail.jsp" >
</iframe>
  
  
</html>