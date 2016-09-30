<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>result</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">

<% 
String s=(String)request.getAttribute("flag");
if(s.equalsIgnoreCase("true")){
	out.println("删除成功！");
}else{
	out.println("删除失败！");
}
%>
<br>
<br>

<a href="listMappingServlet?p=1">返回列表</a>

</body>
</html>