<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<title>left</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
欢迎您！&nbsp;

 <br>
 <br>
个人信息管理<br>
<a href="servlet/personalInfoServlet" target="main">&nbsp;&nbsp;&nbsp;—查询个人信息</a><br>
<a href="servlet/viewAndModifyPIServlet" target="main">&nbsp;&nbsp;&nbsp;—修改个人信息</a><br>
<a href="modifyPWD.jsp" target="main">&nbsp;&nbsp;&nbsp;—修改密码</a><br>
<br>
<%
String oidstr=(String)request.getSession(true).getAttribute("id"); 
int oid=Integer.parseInt(oidstr);
if(oid==1000){
out.print("<br>管理员管理<br><a href='servlet/listAdminsServlet' target='main'>&nbsp;&nbsp;&nbsp;—管理员列表</a><br><br>");
}
%>

<br>
属性映射关系管理<br>
<a href="servlet/listMappingServlet?p=1" target="main">&nbsp;&nbsp;&nbsp;—属性映射关系列表</a><br>
<a href="addMapping.jsp" target="main">&nbsp;&nbsp;&nbsp;—创建映射关系</a><br>
<a href="importMappings.jsp" target="main">&nbsp;&nbsp;&nbsp;—导入映射关系</a><br>
<a href="servlet/exportMappingsServlet" target="main">&nbsp;&nbsp;&nbsp;—导出映射关系</a><br>
<br>
<a href="logout.jsp" target="_parent">注销登录</a>
</body>
</html>