<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<title>left</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" vlink="yellow">
��ӭ����&nbsp;

 <br>
 <br>
������Ϣ����<br>
<a href="servlet/personalInfoServlet" target="main">&nbsp;&nbsp;&nbsp;����ѯ������Ϣ</a><br>
<a href="servlet/viewAndModifyPIServlet" target="main">&nbsp;&nbsp;&nbsp;���޸ĸ�����Ϣ</a><br>
<a href="modifyPWD.jsp" target="main">&nbsp;&nbsp;&nbsp;���޸�����</a><br>
<br>
<%
String oidstr=(String)request.getSession(true).getAttribute("id"); 
int oid=Integer.parseInt(oidstr);
if(oid==1000){
out.print("<br>����Ա����<br><a href='servlet/listAdminsServlet' target='main'>&nbsp;&nbsp;&nbsp;������Ա�б�</a><br><br>");
}
%>

<br>
����ӳ���ϵ����<br>
<a href="servlet/listMappingServlet?p=1" target="main">&nbsp;&nbsp;&nbsp;������ӳ���ϵ�б�</a><br>
<a href="addMapping.jsp" target="main">&nbsp;&nbsp;&nbsp;������ӳ���ϵ</a><br>
<a href="importMappings.jsp" target="main">&nbsp;&nbsp;&nbsp;������ӳ���ϵ</a><br>
<a href="servlet/exportMappingsServlet" target="main">&nbsp;&nbsp;&nbsp;������ӳ���ϵ</a><br>
<br>
<a href="logout.jsp" target="_parent">ע����¼</a>
</body>
</html>