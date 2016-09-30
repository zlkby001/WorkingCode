<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*"  %>
 
 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">



<link href="img/flower.css" type="text/css" rel="StyleSheet">
<style type="text/css">
<!--
td{
word-break: break-all; word-wrap:break-word;
}
--></style>

<script language="JavaScript">  
    
  var   selectRow=null;//记录上一次click过的行  
  var   pointRow=null;//记录上次mouseover过的行  
   
  function   moveRow(src){  
        if(pointRow!=null)  
              pointRow.style.backgroundColor="#FFFFFF";  
        if(src!=selectRow){  
              pointRow=src;  
              pointRow.style.backgroundColor="#FEF9E2";  
        }  
  } 
  function   moveRow2(src){  
        if(pointRow!=null)  
              pointRow.style.backgroundColor="#FFFFFF";  
        if(src!=selectRow){  
              pointRow=src;  
              pointRow.style.backgroundColor="#DEEBFE";  
        }  
  }
  function showquesTable(id) {
	if (id.style.display=="none"){
		id.style.display = "";

		}
	else{
		id.style.display = "none";
		
		}
} 
   
function delete1(){
 	if(confirm("确定要删除此映射关系吗！")){
		return true;
	}else{
		return false;
	}
 }
     
</script>

</head><body leftmargin="4" topmargin="0" bgcolor="#ffffff" marginheight="0" marginwidth="0">
<center>



<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>

  <tr>
    <td colspan="3"><img src="img/spacer.gif" width="1" height="1"></td>
  </tr>
  <tr><td align="middle" background="img/banner_bg.gif" width="200" height="29"><br></td>
  <td background="img/banner_bg1.gif"><br></td><td width="2"><br></td></tr><tr><td colspan="3"><br></td></tr></tbody></table>
  <table style="border-collapse: collapse;" border="1" bordercolor="#77aa33" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
  <tr>
  	<td align="middle" valign="top" >
  		<table border="0" cellpadding="2" cellspacing="0" width="98%" height="90%">
  			<tbody><tr><td colspan="4" align="middle" height="30"></td></tr>
		        <tr align="center" bgcolor="#b6d3fc" height="20">

            			<td width="15%">软件名称</td>
            			<td width="15%">文件名</td>
            			<td width="8%">软件版本</td>    
            			<td width="20%">软件类型</td>

            			<td width="10%">信任状态</td>
            			<td width="10%">添加人员</td>
            			<td>操 作</td>            			     			
            	</tr>
            	        	
         	<%
 ArrayList al=(ArrayList)request.getSession(true).getAttribute("MappingCollection"); 
  String currentpagestr=(String)request.getParameter("p");
  int currentpage=Integer.parseInt(currentpagestr);
  int totalpage=1;

  String message   =   "Warning   Info   ... " ;
  int size = al.size();
   
  if(al!=null)
  {
  	
  	if(al.size()==0){
  		totalpage=1;
  	}else if(al.size()%20==0){
  		totalpage=al.size()/20;
  	}else{
  		totalpage=al.size()/20+1;
  	}
  	
	FullInfoBean fmb=null;
	
	
	int i=0;
	while((i+(currentpage-1)*20)<al.size()&&i<20){
		
		
		
		Object obj=al.get(i+(currentpage-1)*20);
		fmb=(FullInfoBean)obj;
		String filename = fmb.getFileName();
		String Edition;
		            if(fmb.getEdition()==null)
		            	Edition="";
		            else
		            	Edition=fmb.getEdition();
		int pos = filename.lastIndexOf("/");
		filename = filename.substring(pos+1); 
		out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");
		
		out.println("<td>"+fmb.getSoftwareName()+"</td>");
		out.println("<td>"+filename+"</td>");
		out.println("<td>"+Edition+"</td>");
		out.println("<td>"+fmb.getClassName()+"</td>");

		
		
		if(fmb.getTrusted()==1)
			out.println("<td>可信</td>");
		else
			out.println("<td>不可信</td>");
		
		out.println("<td>"+fmb.getCreateName()+"</td>");
		out.println("<td>"); 
		
		//应该还需要AValue和Hash才能定位Mapping。
		out.println("[<a href='servlet/viewMappingServlet?aid="+fmb.getID()+"' target='_parent'>详细</a>]");
		out.println("[<a href='servlet/viewAndModifyMappingServlet?aid="+fmb.getID()+"' target='_parent'>修改</a>]");
		out.println("[<a href='servlet/revokeMappingServlet?aid="+fmb.getID()+"' target='_parent' onclick='return delete1();'>删除</a>]");         			 
            			 
	
	
		out.println("</td></tr>");
		
		i++;
		
	}
	
	}
	%>
          	  
            	
            	
            	
            	
            		

     	                             
		        <tr>
		          <td><br></td></tr>
		        <tr>
		          <td ></td>
		          <td></td>
		          <td></td>  
		          <td align=center>
<%
if(al!=null)
 {
 	out.println("<a href='MappingList.jsp?p=1'>首页</a>");
if(currentpage>1){
	out.println("<a href='MappingList.jsp?p="+(currentpage-1)+"'>上一页</a>");
}
out.println("&nbsp;"+currentpage+"/"+totalpage+"&nbsp;");
if(currentpage<totalpage){
	out.println("<a href='MappingList.jsp?p="+(currentpage+1)+"'>下一页</a>");
}
 	out.println("<a href='MappingList.jsp?p="+totalpage+"'>尾页</a>");
}
%>
			</td></tr>

		       </tbody>
		     </table>
		     

          </td>
   </tr>
   
    <tbody>     
   </table>

          
          

</center>
</body></html>