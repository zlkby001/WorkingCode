<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>修改密码</title>
     <link href="img/default.css" rel="stylesheet" type="text/css">   
  </head><body>  
 
<%String oid=(String)request.getSession(true).getAttribute("id"); 
  String name=(String)request.getSession(true).getAttribute("name");
%> 
 
<form  action="servlet/modifyPWDServlet" method="post">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">密码修改</font></strong>
		                </td>
		            </tr>
		            
		            
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">ID号:</td>
		        	<td style="padding-left: 2px;">
		        		<%out.print(oid); %>
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">用户名:</td>
		        	<td style="padding-left: 2px;"><%out.print(name); %></td>
		        </tr>
		        <!--end  -->
		        
		        
		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">旧密码:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="password" name="oldpwd" size="50" >  
		        	</td>
		        	
		        </tr>
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">新密码:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="password" name="pwd1" size="50" > 
		        		
		        		
		        	</td>  	
		        </tr>
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">确认密码:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="password" name="pwd2" size="50" >
		        		
		        		
		        	</td>  	
		        </tr>
		        
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%"></td>
		        	<td style="padding-left: 2px;">
		        		<font color=red>注意：请记住您的新密码，点击“提交修改”</font>
		        
		        		
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