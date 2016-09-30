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
<div align="left">USB度量信息详情 <br>
  <br>
</div>
<form action="ModifyKnowledgeServlet" method="post">
  <div align="left">
    <table style="border-collapse:collapse;text-align:center;"  borderColor="#111111" cellSpacing="0" cellPadding="0" border="1" >
      <tr>
        <td  style="width:100px"> 制造商</td>
        <td style="width:300px"> 
			<%=request.getParameter("manufacture")%> 
        </td>
      </tr>
      
       <tr>
        <td  style="width:100px"> 序列号</td>
        <td style="width:300px"> 
			<%=request.getParameter("sn")%> 
        </td>
      </tr>
      
       <tr>
        <td  style="width:100px"> 版本号</td>
        <td style="width:300px"> 
			<%=request.getParameter("version")%> 
        </td>
      </tr>
      
       <tr>
        <td  style="width:100px"> 生产商</td>
        <td style="width:300px"> 
			<%=request.getParameter("producer")%> 
        </td>
      </tr>
      
       <tr>
        <td  style="width:100px"> 终端</td>
        <td style="width:300px"> 
			<%=request.getParameter("terminal")%> 
        </td>
      </tr>
      
       <tr>
        <td  style="width:100px"> 日期</td>
        <td style="width:300px"> 
			<%=request.getParameter("date")%> 
        </td>
      </tr>
      
    </table>
    <br>
     <input type="button" value="返回" onClick="history.back();"></td>
  </div>
</form>
</body>
</html>
