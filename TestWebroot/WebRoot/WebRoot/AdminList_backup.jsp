<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.admin.AdminBean"  %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>AdminList</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
<%
 ArrayList al=(ArrayList)request.getAttribute("c");
 if(al==null){out.println("twwwwssss");}
  %>
 <table width="100%" border="3" bgcolor="#ffffff"  height="71">
<tr>
<td>ID</td>
<td>����</td>
<td>�绰</td>
<td>Email</td>
<td>��ַ</td>
<td>����</td></tr>
<%
AdminBean a=null;
Iterator it = al.iterator();
while(it.hasNext()){
	Object obj=it.next();
	a=(AdminBean)obj;
	out.println("<tr><td>"+a.getOperatorID()+"</td>");
	out.println("<td>"+a.getName()+"</td>");
	out.println("<td>"+a.getPhoneNum()+"</td>");
	out.println("<td>"+a.getEmail()+"</td>");
	out.println("<td>"+a.getAddress()+"</td>");
	if(!a.getOperatorID().equals("1000")){
		out.println("<td><a href='deleteAdminServlet?oid="+a.getOperatorID()+"'>ɾ��</a></td>");
	}else out.println("<td>����ɾ��</td>");
	
	out.println("</tr>");
}
%>
</table>

</body>
</html>