<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String searchname=request.getParameter("searchname");
String searchdigest=request.getParameter("searchdigest");
String userid=request.getParameter("userid");
String indexid=request.getParameter("indexid");
String issuedate=request.getParameter("issuedate");
request.getSession(true).setAttribute("back", "1");
String url="wlsnapshot.jsp?indexid="+indexid+"&userid="+userid+"&issuedate="+issuedate+"&backsearchname="+searchname+"&backsearchdigest="+searchdigest;
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
  	 白名单快照<br>

	<br> 
    <form action="ModifyKnowledgeServlet" method="post">
    <table>
	<tr><td  style="width:150px">
		进程名</td><td><input readonly="true" size="40"  name="process_name" value=<%=request.getParameter("process_name")%> readonly=true></td></tr>
		<tr><td>
		Hash值</td><td><input  readonly="true" size="80"  name="hash_value" value=<%=request.getParameter("hash_value")%> readonly=true></td></tr>
		<tr><td>
		进程路径</td>
		<td>
			<% String process_path = new String(request.getParameter("process_path").getBytes("ISO8859-1"),"utf-8");
		if(process_path!=null)
			process_path = URLDecoder.decode(process_path,"UTF-8");
		%> 
		<input  readonly="true" size="80"  name="software_ver" value="<%=process_path%>" readonly=true>
		</td></tr>
		<tr><td>
		生成日期</td><td><input  readonly="true" size="40"  name="software_ver" value=<%=(String)request.getSession(true).getAttribute("issuedate")%> readonly=true>
		</td></tr>
		<tr><td>
		<% String ics_name = request.getParameter("ics_name");
			//if(ics_name!=null)
				//ics_name = URLEncoder.encode(ics_name,"UTF-8");
		%> 
		<!-- 知识库名称 -->
		<input type="hidden" name="ics_name" value=<%=ics_name%>>
		</td></tr>
		
		<tr><td>
		<input type="button" value="返回" onclick="window.location.href=document.referrer;"></td></tr>
	</table>
	</form>
	
	
  </body>
</html>
