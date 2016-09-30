<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.net.URLDecoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>title</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META content="MSHTML 6.00.6000.16788" name=GENERATOR>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
.body {
	font-size: 14px;
	background-color: #DBEBF7;
	font-family: "Times New Roman", Times, serif;
}
.table {
	font-size: 12px;
	background-color: #DBEBF7;	
}
.title{
	font-weight: bold;
	font-size:14px;
	position:relative;
	width:150px;	
	margin-left: auto;
	margin-right: auto;
	
}
</style>
</head>
  
<body class="body">

<p class="title" style="
	font-weight: bold;
	font-size:14px;
	position:relative;
	width:150px;	
	margin-left: auto;
	margin-right: auto;">审计日志详细信息：</p>

<hr>
<TABLE class = "table" width="100%" height="150px" style="font-size: 12px;">
  <TR>
    <TD width="30%">用户种类:</TD>
    <TD width="50%"><%=request.getParameter("role") %></TD>    
  </TR>
  <TR>
    <TD width="30%">用户名:</TD>
    <TD width="50%"><%=request.getParameter("user") %></TD>    
  </TR>
  
  <TR>
    <TD width="30%">操作种类:</TD>
    <TD width="50%"><%=request.getParameter("type") %></TD>    
  </TR>
  
  <TR>
    <TD width="30%">操作描述：</TD>
    <TD width="50%"><%=request.getParameter("action")%></TD>    
  </TR>
  
  <TR>
    <TD width="30%">时间：</TD>
    <TD width="50%"><%=request.getParameter("time") %></TD>
  </TR>
</TABLE>
<hr>

  </body>
</html>
