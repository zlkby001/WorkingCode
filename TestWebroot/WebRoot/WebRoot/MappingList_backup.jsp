<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="java.util.Collection,java.util.Iterator,cn.ac.sklois.imm.mappings.MappingBean"  %>
    
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ӳ���ϵ�б�</title>
</head>
<body bgcolor="#4780C3" link="#DE7108" >

<%
 Collection al=(Collection)request.getAttribute("ML");
 if(al==null){out.println("twwwwssss");}
  %>
 <table width="100%" border="3" bgcolor="#ffffff"  height="71">
<tr>
<td>������</td>
<td>����ֵ</td>
<td>Hashֵ��Ŀ</td>
<td>Hashֵ</td>
<td>����ʱ��</td>
<td>������ID</td>
<td>����</td>
</tr>
<%
MappingBean mb=null;
Iterator it = al.iterator();
while(it.hasNext()){
	Object obj=it.next();
	mb=(MappingBean)obj;
	
	
	out.println("<td>"+mb.getAtt().getAttributeValue()+"</td>");
	
	out.println("<td>"+mb.getMvs().getHashValues()+"</td>");
	out.println("<td>"+mb.getCreation().getOperationTime()+"</td>");
	out.println("<td>"+mb.getCreation().getOperatorID()+"</td>");
	
	out.println("<td><a href='revokeMappingServlet?attname="+mb.getAtt().getAttributeID()+"&attvalue="+mb.getAtt().getAttributeValue()+"'>ɾ��</a></td>");
	
	out.println("</tr>");
}
%>
</table>
<br>
<br>
<br>
<% 

int currentpage=((Integer)request.getAttribute("cp")).intValue();
int totalpage=((Integer)request.getAttribute("tp")).intValue();
if(currentpage>1){
	out.println("<a href='MappingListServlet?p="+(currentpage-1)+"'>��һҳ</a>");
}
out.println("&nbsp;"+currentpage+"/"+totalpage+"&nbsp;");
if(currentpage<totalpage){
	out.println("<a href='MappingListServlet?p="+(currentpage+1)+"'>��һҳ</a>");
}

%>


</body>
</html>