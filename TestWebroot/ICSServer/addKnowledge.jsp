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
		进程名<input name="process_name"><br>
		Hash值<input  name="hash_value"><br>
		软件版本号<input  name="software_ver"><br>
		操作系统号<input name="os_ver"><br>
		软件描述信息<input  name="software_info"><br>
		发布日期<input  name="release_date"><br>
		厂商信息<input  name="manufacturer"><br>
		是否工控软件<input name="ic_bool"><br>
		知识库名称<input  name="ics_name"><br>	
		<input type="submit" value="提交">
		<input type="button" value="取消" onclick="window.location='searchMetric.jsp';">
	</form>
</body>
</html>
