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
	out.print("����ɹ���<br>");
}else{
	out.print("δ�ɹ����������������ʾ����鿴�����Ƿ��Ѵ��ڣ�<br>");
	Iterator it = al.iterator();
	while(it.hasNext()){
		Object obj=it.next();
		a=(Attribute)obj;
		//out.print("<br>��������"+a.getAttributeName()+"&nbsp;"+a.getAttributeValue()+"<br>");
	}
}			
%>
<br>

<a href="listMappingServlet?p=1">����</a>
</body>
</html>