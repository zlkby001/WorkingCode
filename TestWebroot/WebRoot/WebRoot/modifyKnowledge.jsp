<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="UTF-8"%>
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
  	 修改知识库 <br>

	<br> 
    <form action="ModifyKnowledgeServlet" method="post">
    <table>
	<tr><td  style="width:150px">
		进程名</td><td><input readonly="true" size="40"  name="process_name" value=<%=request.getParameter("process_name")%>></td></tr>
		<tr><td>
		Hash值</td><td><input  readonly="true" size="40"  name="hash_value" value=<%=request.getParameter("hash_value")%> readonly=true></td></tr>
		<tr><td>
		<% String software_ver = new String(request.getParameter("software_ver").getBytes("ISO8859-1"),"utf-8");
		if(software_ver!=null)
			software_ver = URLDecoder.decode(software_ver,"UTF-8");
		%> 
		软件版本号</td><td><input  readonly="true" size="40"  name="software_ver" value=<%=software_ver %>>
		</td></tr>
		<tr><td>
		<% String os_ver = new String(request.getParameter("os_ver").getBytes("ISO8859-1"),"utf-8");%> 
		操作系统号</td><td>
			<SELECT style="WIDTH: 290px" name="os_ver"  > 
        				<OPTION value="XP系统" >XP系统</OPTION>
        				<OPTION value="Windows7">Windows7</OPTION> 
        				<OPTION value="WinServer2003">WinServer2003</OPTION>
        				<OPTION value="WinServer2000">WinServer2000</OPTION>
        				<OPTION value="Linux">Linux</OPTION>
        	</SELECT>
	</td></tr>
		<tr><td>
		<% String software_info = new String(request.getParameter("software_info").getBytes("ISO8859-1"),"utf-8");
		if(software_info!=null)
			software_info = URLDecoder.decode(software_info,"UTF-8");
		%> 
		软件描述信息</td><td><input  name="software_info" size="40"  value="<%=software_info %>"></td></tr>
		<tr><td>
		发布日期</td><td><input  name="release_date" size="40" value=<%=request.getParameter("release_date")%>></td></tr>
		<tr><td>
		<% String manufacturer = new String(request.getParameter("manufacturer").getBytes("ISO8859-1"),"utf-8");%> 
		厂商信息</td><td><input  name="manufacturer" size="40" value=<%=URLDecoder.decode(manufacturer,"UTF-8")%>></td></tr>
		<tr><td>
		<% String ic_bool = new String(request.getParameter("ic_bool").getBytes("ISO8859-1"),"utf-8");%> 
		软件类型</td><td>
<!-- 		<input name="ic_bool" value=<=ic_bool%>> -->
		<SELECT style="WIDTH: 290px" name="ic_bool"  value="test"> 
        				<OPTION value="工控软件" >工控软件</OPTION>
        				<OPTION value="应用软件">应用软件</OPTION>
        				<OPTION value="操作系统">操作系统</OPTION> 
        				<OPTION value="办公软件">办公软件</OPTION>
        				<OPTION value="系统进程">系统进程</OPTION> 
        				<OPTION value="编程开发">编程开发</OPTION>
        				<OPTION value="杀毒安全">杀毒安全</OPTION> 
        				<OPTION value="系统工具">系统工具</OPTION>
        				<OPTION value="图形图像">图形图像</OPTION>
        				<OPTION value="多媒体">多媒体</OPTION>
        				<OPTION value="网络软件">网络软件</OPTION>
        				<OPTION value="游戏娱乐">游戏娱乐</OPTION>
<OPTION value="网络软件">网络软件</OPTION>
        			</SELECT>
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
		<% String id = request.getParameter("id");
			//if(ics_name!=null)
				//ics_name = URLEncoder.encode(ics_name,"UTF-8");
		%> 
		<!-- 知识库名称 -->
		<input type="hidden" name="id" value=<%=id%>>
		</td></tr>
		
		<tr><td>
		<input type="submit" value="提交">
		</td>
		<td><input type="button" value="返回" onclick="history.back();"></td></tr>
	</table>
	</form>
	
	
  </body>
</html>
