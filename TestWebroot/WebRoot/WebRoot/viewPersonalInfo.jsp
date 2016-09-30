<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cn.ac.sklois.imm.admin.*"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>查看个人信息</title>
     <link href="../img/default.css" rel="stylesheet" type="text/css">   
  </head><body>  
  
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">个人信息</font></strong>
		                </td>
		            </tr>
		            
		            <%AdminBean a=(AdminBean)request.getAttribute("a");%>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">ID号:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getOperatorID()); %>
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">用户名:</td>
		        	<td style="padding-left: 2px;"><% out.println(a.getName()); %></td>
		        </tr>
		        <!--end  -->
		        
		        
		        <tr> 
                    <td style="padding-left: 10px;" width="20%">性别：</td>
           		    <td style="padding-left: 2px;">           		    	
           		    		<%
           		    		String tmp=a.getGender();
           		    		if(tmp.equalsIgnoreCase("M")){
           		    			out.println("男");
           		    		}else{
           		    			out.println("女");
           		    		}
           		    		 %>     
                    </td>
                </tr>
                
                <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">联系地址:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getAddress()); %>
		        	</td>
		        	
		        </tr>
                
                <tr>
   			    
		        	<td style="padding-left: 10px;" width="20%">电话号码:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getPhoneNum()); %>
		        	</td>
		        	
		        </tr>
                
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">EMail地址:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getEmail()); %>
		        	</td>

		        </tr>
		        
   			    
		        
		        
                
		        

		        
		        <tr>
		        
		        	<td style="padding-left: 10px;" width="20%">证件类型:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getCertclass()); %>
		        	</td>
		        	
		        </tr>
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">证件号码:</td>
		        	<td style="padding-left: 2px;">
		        		<% out.println(a.getCertnumber()); %>
		        	</td>  	
		        </tr>
		        
		        <tr>		        
		        	<td style="padding-left: 10px;" width="20%">当前状态:</td>
		        	<td style="padding-left: 2px;">
		        		<% 
		        		String t=a.getPass();
		        		if(t.equalsIgnoreCase("Y")){
		        			out.println("已经审核通过"); 
		        		}else{
		        			out.println("待审核"); 
		        		}
		        		%>
		        	</td>

		        	
		        </tr>
		        
		        
		        
		        
                
                
                
 				               
            	

                
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="30%"></td>
   					   			
   					   			<td>

   					   			<table bgcolor="#9AADCD">
   					   			<tbody><tr >
   					   			<td><br></td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>
              				
                
                </tbody>
                </table>					       
		        
   
  
  </body></html>