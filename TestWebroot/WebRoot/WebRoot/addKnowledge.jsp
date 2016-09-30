<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>My JSP 'addKnowledge.jsp' starting page</title>

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
	自定义添加知识库描述.
	<br>
	<form action="AddKnowledgeServlet" method="post">
	<table>
	<tr><td  style="width:150px">
		进程名</td><td style="width:200px"><input name="process_name"></td></tr>
		<tr><td>
		Hash值</td><td><input  name="hash_value"></td></tr>
		<tr><td>
		软件版本号</td><td><input  name="software_ver"></td></tr>
		<tr><td>
		操作系统号</td><td><input name="os_ver"></td></tr>
		<tr><td>
		软件描述信息</td><td><input  name="software_info" size="30"></td></tr>
		<tr><td>
		发布日期</td><td><input  name="release_date" size="30"></td></tr>
		<tr><td>
		厂商信息</td><td><input  name="manufacturer"></td></tr>
<tr><td>
		是否工控软件</td><td><input name="ic_bool"></td></tr>
<tr><td>
		知识库名称</td><td><input  name="ics_name"></td></tr>
<tr><td>
		<input type="submit" value="提交"  onclick="window.history.back();"></td><td>
		<input type="button" value="取消" onclick="window.history.back();"></td></tr>
	</table>
	</form>
</body>
</html>
