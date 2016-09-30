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

            			<td width="10%">客户端IP</td>
            			<td width="20%">软件名称</td>
            			<td width="20%">文件名</td>
            			<td width="30%">完整性值</td>    
            			<td width="20%">信任状态</td>      			     			
            	</tr>

     	                             
		        <tr>
		          <td><br></td></tr>
		        <tr>
		          <td ></td>
		          <td></td>
		          <td align=right>

			</td></tr>

		       </tbody>
		     </table>
		     

          </td>
   </tr>
   
    <tbody>     
   </table>

          
          

</center>
</body></html>