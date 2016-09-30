<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*"  %>
<%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.upload.*"  %>
 
 
    
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
  
  var checkedAll = false;
   
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
 
 
 
function selectAll(formName,checkboxName){
	
//	var form = document.getElementsById(formName);
    var elements = document.getElementsByName(checkboxName);

	for (var i=0;i<elements.length;i++){
		var e = elements[i];
		if(checkedAll){
			e.checked = false;

		} else {
			e.checked = true;

		}
	}
	if(checkedAll){
//					form.alls.checked = false;
		checkedAll = false;
	} else {
	//				form.alls.checked = true;
		checkedAll = true;
	}
}  
   

function checkAll(formName,checkboxName){
var hasCheck = false;
var elements = document.getElementsByName(checkboxName);
for (var i=0;i<elements.length;i++){
var e = elements[i];
if(e.checked){
hasCheck = true;
}
}
return hasCheck;
}

/* 执行操作 */
function do_action(){
if (!checkAll("formName","YesNo")){
alert("没有元数据被选中，请至少选择一个！");
return false;
} else {
return true;
}
} 
     
</script>

</head><body leftmargin="4" topmargin="0" bgcolor="#ffffff" marginheight="0" marginwidth="0">
	<object   id="fileDialog"   width="0px"   height="0px"   classid="clsid:F9043C85-F6F2-101A-A3C9-08002B2F49FB"   codebase="http://activex.microsoft.com/controls/vb5/comdlg32.cab">   
  </object> 



<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>

  <tr>
    <td colspan="3"><img src="img/spacer.gif" width="1" height="1"></td>
  </tr>
  <tr><td align="middle" background="img/banner_bg.gif" width="200" height="29"><br></td>
  <td background="img/banner_bg1.gif"><br></td><td width="2"><br></td></tr><tr><td colspan="3"><br></td></tr>
  </tbody></table>
  
  

  <table style="border-collapse: collapse;" border="1" bordercolor="#77aa33" cellpadding="0" cellspacing="0" width="100%">
  
  <tr>
  	<td align="middle" valign="top" >
  	<form name="formName" action=servlet/verifySetTrustedServlet method=post target='_parent' onsubmit="return do_action();">
  		<table border="0" cellpadding="2" cellspacing="0" width="98%" height="90%">
  			<tbody><tr><td colspan="4" align="middle" height="30"></td></tr>
		        <tr align="center" bgcolor="#b6d3fc" height="20">

            			<td width="10%">全选<input type="checkbox" name="alls" onClick="selectAll('formName','YesNo')" title="全选/取消全选">
</td>
            			<td width="10%">客户端IP</td>
            			<td width="15%">软件名称</td>
            			<td width="15%">文件名</td>
            			<td width="30%">完整性值</td>
            			<td width="20%">信任状态</td>        			     			
            	</tr>
            	        	
         	<%
 ArrayList al=(ArrayList)request.getSession(true).getAttribute("VerifyFailListCollection"); 
  String currentpagestr=(String)request.getParameter("p");
  int currentpage=Integer.parseInt(currentpagestr);
  int totalpage=1;    
  	
//	FullMappingBean fmb=null;
   
  if(al!=null)
  {  	
  	if(al.size()==0){
  		totalpage=1;
  	}else if(al.size()%500==0){
  		totalpage=al.size()/500;
  	}else{
  		totalpage=al.size()/500+1;
  	}
	
	int i=0;
	while((i+(currentpage-1)*500)<al.size()&&i<500){
		
		
		
		Object obj=al.get(i+(currentpage-1)*500);
		VerifyDigestBean fmb=(VerifyDigestBean)obj;
		String filename = fmb.getName();
		int pos = filename.lastIndexOf("/");
		filename = filename.substring(pos+1);
		out.println("<tr style='background-color: rgb(254, 249, 226);' onmouseover='moveRow(this)' align='center' height='20'>");
		out.println("<td>");
		out.println("<input type='checkbox' name='YesNo' value='"+(i+1)+"' title='选择/不选择'>");
		out.print("<input type='hidden' name='aid"+(i+1)+"' value='"+fmb.getID()+"'>");
		out.print("<input type='hidden' name='trusted"+(i+1)+"' value='"+fmb.getTrusted()+"'>");
		out.println("</td>");
		out.println("<td>"+fmb.getIP()+"</td>");
		out.println("<td>"+fmb.getSoftware()+"</td>");
		out.println("<td>"+filename+"</td>");
		out.println("<td>"+fmb.getDigest()+"</td>");
		if(fmb.getTrusted()==0)
			out.println("<td>不可信</td>");
		else if(fmb.getTrusted()==2)
			out.println("<td>未登记</td>");					       			 
		out.println("</tr>");		
		i++;		
	}
	}
		%>
          	  
            	
            	
            	
            	
            		

     	                             
		        <tr>
		          <td><br></td></tr>
		        <tr>
		          <td></td>
		          <td></td>  
		          <td align=center>
<%
	if(al!=null)
 {
 	out.println("<a href='VerifyFailDataList.jsp?p=1'>首页</a>");
if(currentpage>1){
	out.println("<a href='VerifyFailDataList.jsp?p="+(currentpage-1)+"'>上一页</a>");
}
out.println("&nbsp;"+currentpage+"/"+totalpage+"&nbsp;");
if(currentpage<totalpage){
	out.println("<a href='VerifyFailDataList.jsp?p="+(currentpage+1)+"'>下一页</a>");
}
 	out.println("<a href='VerifyFailDataList.jsp?p="+totalpage+"'>尾页</a>");
}
	
	%>
          	  
     	                             
		        <tr>
		          <td><br></td></tr>
		        <tr>
		          <td ></td>
		          <td></td>
		          <td ></td>
		          <td align=left>
		          	<input type="hidden" name="filepath">
		          	<input   type="submit"   value="置为可信"   onclick="return do_action()"> 
			</td></tr>

       
   </table>
</form>	 
