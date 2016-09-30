<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.ac.sklois.imm.admin.*"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logout</title>
</head>
<body>


<%
String id = (String) request.getSession(true).getAttribute("id");

int id1 = 0;
System.out.println("id-----"+id);
//String name = (String) request.getSession(true).getAttribute("name");
String name =(String)session.getAttribute("name");
//String name=request.getParameter("username");
System.out.println("name-----"+name);
	//String usertype = request.getParameter("usertype");
String usertype = (String) request.getSession(true).getAttribute("usertype");
//String usertype =(String)session.getAttribute("usertype");
System.out.println("usertype1-----"+usertype);

  audit.log_record(id1, usertype, name, "登陆与退出", "退出");
session.removeAttribute("name");
	session.removeAttribute("id");
	if(session.getAttribute("AttCollection")!=null)
		session.removeAttribute("AttCollection");
	if(session.getAttribute("MappingCollection")!=null)
		session.removeAttribute("MappingCollection");	
	
	response.sendRedirect("login.jsp");
	
 %>
</body>
</html>