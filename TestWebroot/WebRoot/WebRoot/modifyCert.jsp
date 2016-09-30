<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
 import="cn.ac.sklois.imm.admin.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>修改证件信息</title>
     <link href="../img/default.css" rel="stylesheet" type="text/css">   
  </head><body>  
  <%
AdminBean a=(AdminBean)request.getAttribute("a");
String name=a.getName();
String oid=a.getOperatorID();
String address=a.getAddress();
String phonenum=a.getPhoneNum();
String email=a.getEmail();

String gender=a.getGender();
String certclass=a.getCertclass();
String certnumber=a.getCertnumber();

%>
<form action="modifyCertServlet" method="post">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">证件信息</font></strong>
		                </td>
		            </tr>
		            
		            
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">ID号:</td>
		        	<td style="padding-left: 2px;">
		        		<%out.print(oid); %><input type="hidden" name="oid" value="<%out.print(oid);%>"> 
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">用户名:</td>
		        	<td style="padding-left: 2px;"><%out.print(name); %></td>
		        </tr>
		        <!--end  -->
		        
		        
		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">证件类型:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="certclass" size="20" value="<%=certclass%>"> 
		        	</td>
		        	
		        </tr>
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">证件号码:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="certnumber" size="50" value="<%=certnumber%>"> 
		        		
		        		
		        	</td>  	
		        </tr>
		        
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%"></td>
		        	<td style="padding-left: 2px;">
		        		<font color=red>注意：证件信息的修改将导致帐户处于未审核状态！<br>需要系统管理员重新审核！确认请点击“提交修改”！</font>
		        
		        		
		        	</td>  	
		        </tr>
		        
		        
		        
		        
                
                
                
 				               
            	

                
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="30%"></td>
   					   			<td>

   					   			<table>
   					   			<tbody><tr>
   					   			<td style="padding-left: 0px;" align="center" width="33%"><input value="   提交修改   "  type="submit"></td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>

              				
                
                </tbody>
                </table>					       
		   </form>     
   
  
  </body></html>
