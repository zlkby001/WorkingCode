<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'showKnowledge.jsp' starting page</title>

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
	显示知识库名字.
	<br>
	<form action="RecountServlet" method="post">
		<input type="submit" value="重新统计">
	</form>
	<br>
	<form action="ShowKnowledgeServlet" method="post">
		<input type="submit" value="显示知识库名">
	</form>
	<table>
		<tr>
			<td>进程名</td>
		</tr>
		<c:forEach var="u" items="${name}">
			<tr>
				<td><c:out value="${u.ics_name}"></c:out></td>
				<td><input type="button" value="具体显示"
					onclick="window.location='viewKnowledge.jsp?ics_name=${u.ics_name}';">
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
