<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<title>Welcome</title>
</head>


<FRAMESET border=0 frameSpacing=0 rows=15%,* frameBorder=NO>
<!-- 	String oid = request.getParameter("oid"); -->
<!-- 	String sdate = request.getParameter("sdate"); -->
<!-- 	String addr = "servlet/historyServlet?oid="+oid+"&sdate="+sdate;	 -->
<!-- 	System.out.println("--------02---  " + addr + "  ------------" ); -->
	<FRAME name=atttop src="./historyintoknowledgeSearchBar.jsp" noResize scrolling=yes>
	<FRAME name=attbottom src="servlet/historyintoknowledgeServlet" scrolling=yes>
</FRAMESET>

<noframes>
	<body>

		<p>此网页使用了框架，但您的浏览器不支持框架。</p>

	</body>
</noframes>

</html>