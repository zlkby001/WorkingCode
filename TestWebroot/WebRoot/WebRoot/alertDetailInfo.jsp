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
	margin-right: auto;">报警日志详细信息：</p>

<hr>
<TABLE class = "table" width="100%" height="150px" style="font-size: 12px;">
  <TR>
    <TD width="30%">用户名:</TD>
    <TD width="50%"><%=request.getParameter("username") %></TD>    
  </TR>
  
  <TR>
    <TD width="30%">IP地址:</TD>
    <TD width="50%"><%=request.getParameter("ip") %></TD>    
  </TR>
  
  <TR>
    <TD width="30%">文件名：</TD>
    <TD width="50%"><%
String s = URLDecoder.decode(request.getParameter("filename"), "UTF-8");
int len = s.length();
String m="";

if(len>30){
	int p = len/30;	
	for(int i=0;i<p+1;i++){
		m += "<br>";
		if(i<p)
		m += s.substring(30*i,30*i+29);
		else if(30*i<len)
		m += s.substring(30*i,len-1);		
	}
}
else if(len>0&&len<=30)
	m = s;
else 
	m ="";
out.println(m);
%></TD>    
  </TR>
  
  <TR>
    <TD width="30%">度量值：</TD>
    <TD width="50%"><%
    					String str = URLDecoder.decode(request.getParameter("digest"), "UTF-8");
    					//int len = str.length()/3;
    					//if(len>0){
		        		String msg = str.substring(0,29) + "<br>"+ str.substring(30,59) + "<br>"+ str.substring(60,str.length());
		        		out.println(msg);
     %></TD>    
  </TR>
  
  <TR>
    <TD width="30%">时间：</TD>
    <TD width="50%"><%=request.getParameter("issuedate") %></TD>
  </TR>
</TABLE>
<hr>

  </body>
</html>
