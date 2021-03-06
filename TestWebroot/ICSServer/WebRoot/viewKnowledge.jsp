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

<title>My JSP 'viewKnowledge.jsp' starting page</title>

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
	显示知识库.
	<br>
	<form action="ViewKnowledgeServlet" method="post">
	<% String ics_name = new String(request.getParameter("ics_name").getBytes("ISO8859-1"),"utf-8");%> 
		<input value=<%=ics_name%> name="ics_name">
		<input type="submit" value="显示知识库">
	</form>
	<table>
		<tr>
			<td>进程名</td>
			<td>hash值</td>
			<td>软件号</td>
			<td>操作系统号</td>
			<td>软件信息</td>
			<td>发布日期</td>
			<td>制造商</td>
			<td>工控软件</td>
			<td>知识库名</td>
		</tr>
		<c:forEach var="u" items="${result}">
			<tr>
				<td><c:out value="${u.process_name}"></c:out></td>
				<td><c:out value="${u.hash_value}"></c:out></td>
				<td><c:out value="${u.software_ver}"></c:out></td>
				<td><c:out value="${u.os_ver}"></c:out></td>
				<td><c:out value="${u.software_info}"></c:out></td>
				<td><c:out value="${u.release_date}"></c:out></td>
				<td><c:out value="${u.manufacturer}"></c:out></td>
				<td><c:out value="${u.ic_bool}"></c:out></td>
				<td><c:out value="${u.ics_name}"></c:out></td>
				<td><input type= "button" value="修改"
					onclick="window.location='modifyKnowledge.jsp?process_name=${u.process_name}&hash_value=${u.hash_value}&software_ver=${u.software_ver}&os_ver=${u.os_ver}&software_info=${u.software_info}&release_date=${u.release_date}&manufacturer=${u.manufacturer}&ic_bool=${u.ic_bool}&ics_name=${u.ics_name}';">
				</td>
			</tr>
		</c:forEach>
	</table>
	<br>
</body>
</html>
