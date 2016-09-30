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

<title>My JSP 'searchMetric.jsp' starting page</title>

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
	搜索条.
	<br>
	<form action="SearchMetricServlet" method="post">
		进程名<input value="" name="process_name"> 完整性值<input value=""
			name="hash_value"> 度量时间1<input value="" name="metric_time1">
		-度量时间2<input value="" name="metric_time2"> <input type="submit"
			value="提交查询"> 
		<input onclick="window.location='addKnowledge.jsp';" type="button" value="自定义添加">
	</form>
	<br>
</body>
</html>
