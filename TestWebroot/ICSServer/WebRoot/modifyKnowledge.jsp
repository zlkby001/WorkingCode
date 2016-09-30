<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'modifyKnowledge.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	  更改知识库界面. <br>

	<br> 
    <form action="ModifyKnowledgeServlet" method="post">
    
		进程名<input name="process_name" value=<%=request.getParameter("process_name")%>><br>
		Hash值<input  name="hash_value" value=<%=request.getParameter("hash_value")%> readonly=true><br>
		软件版本号<input  name="software_ver" value=<%=request.getParameter("software_ver")%>><br>
		<% String os_ver = new String(request.getParameter("os_ver").getBytes("ISO8859-1"),"utf-8");%> 
		操作系统号<input name="os_ver" value=<%=os_ver%>><br>
		<% String software_info = new String(request.getParameter("software_info").getBytes("ISO8859-1"),"utf-8");%> 
		软件描述信息<input  name="software_info" value=<%=software_info%> ><br>
		发布日期<input  name="release_date" value=<%=request.getParameter("release_date")%>><br>
		<% String manufacturer = new String(request.getParameter("manufacturer").getBytes("ISO8859-1"),"utf-8");%> 
		厂商信息<input  name="manufacturer" value=<%=manufacturer%>><br>
		<% String ic_bool = new String(request.getParameter("ic_bool").getBytes("ISO8859-1"),"utf-8");%> 
		是否工控软件<input name="ic_bool" value=<%=ic_bool%>><br>
		<% String ics_name = new String(request.getParameter("ics_name").getBytes("ISO8859-1"),"utf-8");%> 
		知识库名称<input  name="ics_name" value=<%=ics_name%>><br>	
		<input type="hidden" name="ics_name1" value=<%=ics_name%>><br>
		<input type="submit" value="提交">
		<input type="button" value="取消" onclick="history.go(-1)">
	</form>
  </body>
</html>
