<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
<% 
ArrayList al=(ArrayList)request.getAttribute("result");
Attribute a=null;
if(al.size()==0){
	out.print("导入成功！<br>");
}else{
	out.print("未成功导入的属性如下所示，请查看它们是否已存在！<br>");
	Iterator it = al.iterator();
	while(it.hasNext()){
		Object obj=it.next();
		a=(Attribute)obj;
		//out.print("<br>属性名："+a.getAttributeName()+"&nbsp;"+a.getAttributeValue()+"<br>");
	}
}			
%>
<br>

<a href="listMappingServlet?p=1">返回</a>
</body>
</html>