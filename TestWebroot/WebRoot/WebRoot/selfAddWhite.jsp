<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,cn.ac.sklois.imm.mappings.*"   %>
    
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>修改映射关系</title>
     
     <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
     
     
     <link href="img/default.css" rel="stylesheet" type="text/css">   
  </head><body> 
  <script language="JavaScript">
  function checkForm(form){
	//if(form.sname.value==""){
		//confirm("请输入所属软件包名！");
		//return false;	
	//}  
	if(form.shash.value == ""){
		confirm("请输入hash值");
		return false;
	}
	if(form.filename.value==""){
		confirm("请输入文件名！");
		return false;	
	} 
	//if(form.sissuetime.value==""){
		//confirm("请输入发布时间！");
		//return false;	
	//}  
	return true;
 } 
 
 function doSubmit() {
    a123.action = "servlet/selfAddWhiteServlet";
    a123.submit();
}

function doBack() {
   a123.action = "Javascript:window.history.go(-1)";
    a123.submit();
}
 
 </script>
 <form name="a123" method="post" onsubmit="return checkForm(this);"> 
<!-- <form action="servlet/ModifyMappingServlet" method="post" onsubmit="return checkForm(this);"> -->
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="100%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">修改</font></strong>
		                </td>
		            </tr>
		            
		         
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">文件名:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="filename" size="15" >
		        	<font color="#cc0000">*</font> </td>

		        </tr>
		        		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">软件完整性值:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="shash" size="50" >         		    	
           	 	
		        	<font color="#cc0000">*</font> </td>  	
		        </tr>
		        
		      	
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="20%">
   					   			
   					   			</td>
   					   			
   					   			<td>

   					   			<table bgcolor="#9AADCD">
   					   			<tbody><tr >
   					   			<td><center>
   					   			
   					   					
   					   					<!--  <input value="   提交修改   "  type="submit"> -->
   					   					<input value="提交修改"  type=button onclick="doSubmit()">
										<input value="返回"  type=button onclick="doBack()">
   					   				
   					   			
   					   			
   					   			
   					   			
   					   			
   					   			
   					   			</center><br>
   					   			
   					   			
   					   			
   					   			</td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>
              				
                
                </tbody>
                </table>					       
		 </form>       
   
  
  </body></html>